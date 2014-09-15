package com.ocyd.appfactory.controller.core;

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
import java.io.*;
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
        cq.eq("shopId", user.getShopId());
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
                String savePath = request.getSession().getServletContext().getRealPath(relative);
                article.setContent(readHtmlFile(savePath));
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
            String relative = found.getContent();
            if(relative == null) {
                relative = composeHtmlFileRelativePath(request.getContextPath());
            }
            String filePath = request.getSession().getServletContext().getRealPath(relative);
            saveHtmlFile(filePath, article.getContent());

            found.setContent(relative);
            found.setIcon(article.getIcon());
            found.setTitle(article.getTitle());
            found.setDescription(article.getDescription());
            found.setKeyWords(article.getKeyWords());
            found.setWebLink(article.getWebLink());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            found.setLastUpdate(date);
            found.setPublishUserId(user.getId());
            systemService.updateEntity(found);
            message = "[" + found.getTitle() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            //将提交上来的content保存为文件，然后将路径存放到content字段。
            if(article.getContent() != null) {
                String saveUrl = composeHtmlFileRelativePath(request.getContextPath());
                String savePath = request.getSession().getServletContext().getRealPath(saveUrl);

                saveHtmlFile(savePath, article.getContent());

                article.setContent(saveUrl);
            }

            article.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            article.setStatus(1); // 正常。

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            article.setCreateTime(date);
            article.setLastUpdate(date);
            article.setCreateUserId(user.getId());
            article.setPublishUserId(user.getId());
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
            //先删除对应的图标文件
            fileService.deleteFile(found.getIcon());

            found.setStatus(TArticle.STATUS_DELETED);
            systemService.save(found);
        }

        message = "[" + found.getTitle() + "]删除成功.";

        j.setMsg(message);
        return j;
    }

    private String composeHtmlFileRelativePath(String contextRoot) {
        StringBuilder sb = new StringBuilder(contextRoot);
        sb.append("/upload/html/");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        sb.append(ymd).append("/");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
        sb.append(df.format(new Date())).append(".html");

        return sb.toString();
    }

    /**
     * 从指定的文件中读取出所有的内容。
     */
    private String readHtmlFile(String path) {
        File file = new File(path);
        StringBuilder sb = new StringBuilder(1024);
        char[] buffer = new char[512];

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int len = 0;
            while((len = reader.read(buffer)) > 0) {
                sb.append(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 将内容保存为html文件。
     */
    private void saveHtmlFile(String filePath, String content) {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }

        BufferedWriter writer = null;
        try {
            if(!file.exists()) {
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("<html><head><meta charset=\"gbk\"></head><body>");
            writer.write(content);
            writer.write("</body></html>");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}