package com.ocyd.appfactory.controller;

import com.ocyd.appfactory.pojo.TArticle;
import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UploadFileService;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import com.ocyd.jeecgframework.core.common.model.json.AjaxJson;
import com.ocyd.jeecgframework.core.common.model.json.DataGrid;
import com.ocyd.jeecgframework.core.constant.Globals;
import com.ocyd.jeecgframework.core.util.ResourceUtil;
import com.ocyd.jeecgframework.tag.core.easyui.TagUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 */
@Scope("prototype")
@Controller
@RequestMapping("/articleController")
public class ArticleController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ArticleController.class);

	private SystemService systemService;
    private UploadFileService fileService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setFileService(UploadFileService fileService) {
		this.fileService = fileService;
	}


    /**
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "articleList")
    public String articleList(HttpServletRequest request, int connectId) {
        request.setAttribute("connectId", connectId);
        return "article/articleList";
    }


    /**
     * 当前商家用户对应的模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(int connectId, HttpServletResponse response, DataGrid dataGrid) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        CriteriaQuery cq = new CriteriaQuery(TArticle.class, dataGrid);
//        cq.eq("shopId", user.getShopId());
        cq.eq("connectId", connectId);
        cq.eq("status", TArticle.STATUS_NORMAL);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyuiAJAX请求数据： 用户选择角色列表
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(HttpServletRequest request, TArticle article) {
        if (article.getId() > 0) {
            article = systemService.getEntity(TArticle.class, article.getId());
            String relative = article.getContent();

            if(relative != null) {
                article.setContent(fileService.readHtmlFile(relative));
                article.setWebLink(article.getContent());
            }
        }

        request.setAttribute("article", article);
//        req.setAttribute("user", ResourceUtil.getCurrentSessionUser());
        return new ModelAndView("article/article");
    }


    /**
     * 对商家进行保存
     */
    @RequestMapping(params = "saveArticle")
    @ResponseBody
    public AjaxJson saveArticle(HttpServletRequest request, TArticle article) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        AjaxJson j = new AjaxJson();
        if (article.getId() > 0) {
            TArticle found = systemService.getEntity(TArticle.class, article.getId());
            //删除旧的文件
            if(found.getIcon() != null && ! found.getIcon().equals(article.getIcon())) {
                fileService.deleteFile(found.getIcon());
            }

            //内容保存为html，然后将相对路径保存到数据库。
            String relative = fileService.saveHtmlFile(found.getContent(), article.getContent());
            found.setContent(relative);
            found.setWebLink(found.getContent());
            found.setIcon(article.getIcon());
            found.setTitle(article.getTitle());
            found.setDescription(article.getDescription());
            found.setKeyWords(article.getKeyWords());

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
            found.setLastUpdate(date);
            found.setPublishUserId("" + user.getId());
            systemService.updateEntity(found);
            message = "[" + found.getTitle() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            //将提交上来的content保存为文件，然后将路径存放到content字段。
            if(article.getContent() != null) {
                String relative = fileService.saveHtmlFile(null, article.getContent());

                article.setContent(relative);
                article.setWebLink(article.getContent());
            }

            article.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            article.setStatus(1); // 正常。

            String date = String.valueOf((int)(System.currentTimeMillis()/1000));
            article.setCreateTime(date);
            article.setPublishTime(date);
            article.setLastUpdate(date);
            article.setCreateUserId("" + user.getId());
            article.setPublishUserId("" + user.getId());
            systemService.save(article);
            message = "[" + article.getTitle() + "]添加成功.";

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }
        j.setMsg(message);

        return j;
    }


    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson delete(HttpServletRequest req, TArticle article) {
        AjaxJson j = new AjaxJson();
        TArticle found = systemService.getEntity(TArticle.class, article.getId());

        if(found.getId() > 0) {
//            //先删除对应的图标文件
//            fileService.deleteFile(found.getIcon());
            found.setStatus(TArticle.STATUS_DELETED);
            systemService.save(found);
        }

        message = "[" + found.getTitle() + "]删除成功.";

        j.setMsg(message);
        return j;
    }
}