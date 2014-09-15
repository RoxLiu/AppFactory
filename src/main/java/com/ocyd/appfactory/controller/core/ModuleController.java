package com.ocyd.appfactory.controller.core;

import com.ocyd.appfactory.pojo.TModule;
import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UserService;
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
@RequestMapping("/moduleController")
public class ModuleController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ModuleController.class);

	private UserService userService;
	private SystemService systemService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


    /**
     * 商家对应的模块列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "moduleList")
    public String user(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        request.setAttribute("user", user);
        return "module/moduleList";
    }


    /**
     * 系统模块列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TUser user, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TModule.class, dataGrid);
        cq.eq("status", TModule.STATUS_NORMAL);
        cq.add();

        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * easyuiAJAX请求数据： 增加或者修改模块
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(HttpServletRequest req, TModule module) {
        if (module.getId() > 0) {
            module = systemService.getEntity(TModule.class, module.getId());
        }

        req.setAttribute("module", module);
        return new ModelAndView("module/module");
    }


    /**
     * 对商家进行保存
     */
    @RequestMapping(params = "saveModule")
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest req, TModule module) {
        AjaxJson j = new AjaxJson();
        if (module.getId() > 0) {
            TModule found = systemService.getEntity(TModule.class, module.getId());
            found.setName(module.getName());
            found.setDescription(module.getDescription());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            found.setLastUpdate(date);

            systemService.updateEntity(found);
            message = "模块[ " + found.getName() + "]更新成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            module.setStatus(1); // 正常。

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = formatter.format(new Date());
            module.setCreateTime(date);
            module.setLastUpdate(date);
            systemService.save(module);
            message = "模块[ " + module.getName() + "]添加成功.";

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }

        j.setMsg(message);

        return j;
    }


    /**
     * 用户信息录入和更新
     *
     * @param user
     * @param req
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(HttpServletRequest req, TModule module) {
        AjaxJson j = new AjaxJson();
        TModule found = systemService.getEntity(TModule.class, module.getId());

        if(found.getId() > 0) {
            found.setStatus(-1); //将状态置为-1表示删除，不直接从数据库中删除记录。
            userService.save(found);
        }
        message = "模块[" + found.getName() + "]删除成功";
        j.setSuccess(true);

        j.setMsg(message);
        return j;
    }
}