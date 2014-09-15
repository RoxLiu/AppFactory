package com.ocyd.appfactory.controller.core;

import com.ocyd.appfactory.manager.ClientManager;
import com.ocyd.appfactory.pojo.*;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UserService;
import com.ocyd.jeecgframework.core.common.controller.BaseController;
import com.ocyd.jeecgframework.core.common.model.json.AjaxJson;
import com.ocyd.jeecgframework.core.constant.Globals;
import com.ocyd.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import com.ocyd.jeecgframework.core.extend.datasource.DataSourceType;
import com.ocyd.jeecgframework.core.util.ContextHolderUtils;
import com.ocyd.jeecgframework.core.util.IpUtil;
import com.ocyd.jeecgframework.core.util.ResourceUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登陆初始化控制器
 * @author 张代浩
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController{
	private Logger log = Logger.getLogger(LoginController.class);
	private SystemService systemService;
	private UserService userService;

    @Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TUser user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
		AjaxJson j = new AjaxJson();
        String randCode = req.getParameter("randCode");
        if (StringUtils.isEmpty(randCode)) {
            j.setMsg("请输入验证码");
            j.setSuccess(false);
        } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
            j.setMsg("验证码错误！");
            j.setSuccess(false);
        } else {
//            System.out.println("....name..." + user.getAccountName()+"...password..."+user.getPassword());
            TUser u = userService.checkUserExits(user);
            if(u == null) {
                j.setMsg("用户名或密码错误!");
                j.setSuccess(false);
                return j;
            }
            TUser u2 = userService.getEntity(TUser.class, u.getId());

            if (u2.getStatus() != 0) {
                // if (user.getUserKey().equals(u.getUserKey())) {
                String message = "用户[" + user.getAccountName() + "]" + "登录成功";
                Client client = new Client();
                client.setIp(IpUtil.getIpAddr(req));
                client.setLogindatetime(new Date());
                client.setUser(u);
                ClientManager.getInstance().addClient(session.getId(), client);
                // 添加登陆日志
                systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
            } else {
                j.setMsg("用户名或密码错误!");
                j.setSuccess(false);
            }
        }
		return j;
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "login")
	public String login(ModelMap modelMap,HttpServletRequest request) {
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_jeecg);
		TUser user = ResourceUtil.getCurrentSessionUser();
//		String roles = "";
		if (user != null) {
            if(user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR || user.getType() == TUser.TYPE_APP_ADMINISTRATOR) {
                modelMap.put("user", user);

                TShopAccount shop = systemService.findUniqueByProperty(TShopAccount.class, "id", user.getShopId());
                if(shop != null) {
                    modelMap.put("shopName", shop.getShopName());
                }

                request.getSession().setAttribute("CKFinder_UserRole", "admin");
                //设置动态菜单
                request.setAttribute("menuList", getFunctionList(user));
                return "main/shortcut_main";
            }
		}

		return "login/login";
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		TUser user = ResourceUtil.getCurrentSessionUser();
        if(user != null) {
            systemService.addLog("用户" + user.getAccountName() + "已退出", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
            ClientManager.getInstance().removeClient(session.getId());
        }

        return new ModelAndView(new RedirectView("loginController.do?login"));
	}

    /**
     * 首页跳转
     *
     * @return
     */
    @RequestMapping(params = "home")
    public ModelAndView home(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();

        if(user != null) {
            if(user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
                return new ModelAndView("main/home");
            } else if(user.getType() == TUser.TYPE_APP_ADMINISTRATOR) {
                TShopAccount shopAccount = userService.findUniqueByProperty(TShopAccount.class, "id", user.getShopId());
                return new ModelAndView("shop/shopAccount", new ModelMap("shopAccount", shopAccount));
            }
        }
        return new ModelAndView("main/home");
    }
    /**
     * 无权限页面提示跳转
     *
     * @return
     */
    @RequestMapping(params = "noAuth")
    public ModelAndView noAuth(HttpServletRequest request) {
        return new ModelAndView("common/noAuth");
    }


    /**
     * 获取权限的map
     *
     * @param user
     * @return
     */
    private List<TSFunction> getFunctionList(TUser user) {
        List<TSFunction> list = new ArrayList<TSFunction>(5);
        if(user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
            TSFunction function = new TSFunction();
            function.setFunctionName("账号管理");
            function.setFunctionUrl("userController.do?userList");
            list.add(function);

            //
//            function = new TSFunction();
//            function.setFunctionName("模块管理");
//            function.setFunctionUrl("moduleController.do?moduleList");
//            list.add(function);
        } else if(user.getType() == TUser.TYPE_APP_ADMINISTRATOR) {
            List<TShopModule> modules = systemService.findByProperty(TShopModule.class, "shopId", user.getShopId());

            for(TShopModule module : modules) {
                TSFunction function = new TSFunction();
                switch (module.getModuleId()) {
                    case TModule.TYPE_NEWS:
                        function.setFunctionName(module.getModuleName());
                        function.setFunctionUrl("articleController.do?articleList&connectId=" + module.getId());
                        list.add(function);
                        break;
                    case TModule.TYPE_SHOP:
                        function.setFunctionName(module.getModuleName());
                        function.setFunctionUrl("shopController.do?shopList");
                        list.add(function);
                }
            }

            //
            TSFunction function = new TSFunction();
            function.setFunctionName("账号管理");
            function.setFunctionUrl("userController.do?userList");
//            function.setTSIcon();
            list.add(function);

            function = new TSFunction();
            function.setFunctionName("订单管理");
            function.setFunctionUrl("orderController.do?orderList");
            list.add(function);
        } else {
            log.info("No Function Menu for User Type. id = " + user.getId() + ", type=" + user.getType());
        }

        return list;
    }

    @RequestMapping(params = "goPwdInit")
    public String goPwdInit() {
        return "login/pwd_init";
    }

    /**
     * admin账户密码初始化
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "pwdInit")
    public ModelAndView pwdInit(HttpServletRequest request) {
        ModelAndView modelAndView = null;
        TUser user = new TUser();
        user.setAccountName("admin");
        String newPwd = "123456";
        userService.pwdInit(user, newPwd);
        modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
        return modelAndView;
    }
}
