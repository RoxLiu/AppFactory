package com.ocyd.appfactory.controller;

import com.ocyd.appfactory.pojo.TAdvert;
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
@RequestMapping("/advertController")
public class AdvertController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AdvertController.class);

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
    @RequestMapping(params = "advertList")
    public String advertList(HttpServletRequest request) {
        return "advert/advertList";
    }


    /**
     * 当前商家用户对应的模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletResponse response, DataGrid dataGrid) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        CriteriaQuery cq = new CriteriaQuery(TAdvert.class, dataGrid);
        cq.eq("shopId", user.getShopId());
        cq.eq("status", TAdvert.STATUS_NORMAL);
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
    public ModelAndView addOrUpdate(HttpServletRequest request, TAdvert advert) {
        if (advert.getId() > 0) {
            advert = systemService.getEntity(TAdvert.class, advert.getId());
        }

        request.setAttribute("advert", advert);
        return new ModelAndView("advert/advert");
    }


    /**
     * 对商家进行保存
     */
    @RequestMapping(params = "saveAdvert")
    @ResponseBody
    public AjaxJson saveAdvert(HttpServletRequest request, TAdvert article) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        AjaxJson j = new AjaxJson();
        if (article.getId() > 0) {
            TAdvert found = systemService.getEntity(TAdvert.class, article.getId());
            //删除旧的文件
            if(found.getIcon() != null && ! found.getIcon().equals(article.getIcon())) {
                fileService.deleteFile(found.getIcon());
            }

            found.setName(article.getName());
            found.setIcon(article.getIcon());
            found.setWebLink(article.getWebLink());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            found.setLastUpdate(date);
            systemService.updateEntity(found);
            message = "广告[" + found.getName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            article.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
            article.setStatus(1); // 正常。

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            article.setLastUpdate(date);
            systemService.save(article);
            message = "广告[" + article.getName() + "]添加成功.";

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
    public AjaxJson delete(HttpServletRequest req, TAdvert advert) {
        AjaxJson j = new AjaxJson();
        TAdvert found = systemService.getEntity(TAdvert.class, advert.getId());

        if(found.getId() > 0) {
            //先删除对应的图标文件
            fileService.deleteFile(found.getIcon());

            found.setStatus(TAdvert.STATUS_DELETED);
            systemService.save(found);
        }

        message = "广告[" + found.getName() + "]删除成功.";

        j.setMsg(message);
        return j;
    }
}