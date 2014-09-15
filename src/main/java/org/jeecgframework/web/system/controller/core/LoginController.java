package org.jeecgframework.web.system.controller.core;

import com.ocyd.appfactory.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.datasource.DataSourceContextHolder;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import weixin.guanjia.account.service.WeixinAccountServiceI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
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
     * @Title: top
     * @author gaofeng
     * @Description: shortcut头部菜单请求
     * @param request
     * @return ModelAndView
     * @throws
     */
    @RequestMapping(params = "shortcut_top")
    public ModelAndView shortcut_top(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        HttpSession session = ContextHolderUtils.getSession();
        // 登陆者的权限
        if (user == null || user.getId() == 0) {
            session.removeAttribute(Globals.USER_SESSION);
            return new ModelAndView(new RedirectView("loginController.do?login"));
        }

        request.setAttribute("menuList", getFunctionList(user));
        List<TSConfig> configs = userService.loadAll(TSConfig.class);
        for (TSConfig tsConfig : configs) {
            request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
        }
        return new ModelAndView("main/shortcut_top");
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

/*####################################################################################################################*/
    /**
     * 菜单跳转
     *
     * @return
     */
    @RequestMapping(params = "left")
    public ModelAndView left(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        HttpSession session = ContextHolderUtils.getSession();
        ModelAndView modelAndView = new ModelAndView();
        // 登陆者的权限
        if (user.getId() == 0) {
            session.removeAttribute(Globals.USER_SESSION);
            modelAndView.setView(new RedirectView("loginController.do?login"));
        }else{
            List<TSConfig> configs = userService.loadAll(TSConfig.class);
            for (TSConfig tsConfig : configs) {
                request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
            }
            modelAndView.setViewName("main/left");
            request.setAttribute("menuMap", getFunctionMap(user));
        }
        return modelAndView;
    }


    /**
	 * 获取权限的map
	 * 
	 * @param user
	 * @return
	 */
	private Map<Integer, List<TSFunction>> getFunctionMap(TUser user) {
        Map<Integer, List<TSFunction>> functionMap = new HashMap<Integer, List<TSFunction>>();
        if(user.getId() == 1 || user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
            List<TSFunction> list = new ArrayList<TSFunction>(5);

            TSFunction function = new TSFunction();
            function.setFunctionName("账号管理");
            function.setFunctionUrl("userController.do?user");
            list.add(function);
            functionMap.put(0, list);
        } else if(user.getType() == TUser.TYPE_APP_ADMINISTRATOR) {
            List<TSFunction> list = new ArrayList<TSFunction>(5);

            TSFunction function = new TSFunction();
            function.setFunctionName("账号管理");
            function.setFunctionUrl("userController.do?user");
//            function.setTSIcon();
            list.add(function);

            function = new TSFunction();
            function.setFunctionName("商家管理");
            function.setFunctionUrl("shopController.do?shopList");
            list.add(function);

            function = new TSFunction();
            function.setFunctionName("订单管理");
            function.setFunctionUrl("iconController.do?icon");
            list.add(function);

            functionMap.put(0, list);
        } else {
            log.info("No Function Menu for User Type. id = " + user.getId() + ", type=" + user.getType());
        }

        return functionMap;
	}

	/**
	 * 获取用户菜单列表
	 * 
	 * @param user
	 * @return
	 */
	private Map<String, TSFunction> getUserFunction(TUser user) {
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client.getFunctions() == null || client.getFunctions().size() == 0) {
			Map<String, TSFunction> loginActionlist = new HashMap<String, TSFunction>();
			List<TSRoleUser> rUsers = systemService.findByProperty(
					TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				List<TSRoleFunction> roleFunctionList = systemService
						.findByProperty(TSRoleFunction.class, "TSRole.id",
								role.getId());
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = roleFunction.getTSFunction();
					loginActionlist.put(function.getId(), function);
				}
			}
			client.setFunctions(loginActionlist);
		}
		return client.getFunctions();
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
        TSUser user = new TSUser();
        user.setUserName("admin");
        String newPwd = "123456";
        userService.pwdInit(user, newPwd);
        modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
        return modelAndView;
    }

	/**
	 * @Title: top
	 * @Description: bootstrap头部菜单请求
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(params = "top")
	public ModelAndView top(HttpServletRequest request) {
		TUser user = ResourceUtil.getCurrentSessionUser();
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		if (user.getId() == 0) {
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}
		request.setAttribute("menuMap", getFunctionMap(user));
		List<TSConfig> configs = userService.loadAll(TSConfig.class);
		for (TSConfig tsConfig : configs) {
			request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
		}
		return new ModelAndView("main/bootstrap_top");
	}

	/**
	 * @Title: top
	 * @author:gaofeng
	 * @Description: shortcut头部菜单一级菜单列表，并将其用ajax传到页面，实现动态控制一级菜单列表
	 * @return AjaxJson
	 * @throws
	 */
    @RequestMapping(params = "primaryMenu")
    @ResponseBody
	public String getPrimaryMenu() {
		List<TSFunction> primaryMenu = getFunctionMap(ResourceUtil.getCurrentSessionUser()).get(0);
        String floor = "";
        for (TSFunction function : primaryMenu) {
            if(function.getFunctionLevel() == 0){

                if("Online 开发".equals(function.getFunctionName())){

                    floor += " <li><img class='imag1' src='plug-in/login/images/online.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/online_up.png' style='display: none;' />" + " </li> ";
                }else if("统计查询".equals(function.getFunctionName())){

                    floor += " <li><img class='imag1' src='plug-in/login/images/guanli.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/guanli_up.png' style='display: none;' />" + " </li> ";
                }else if("系统管理".equals(function.getFunctionName())){

                    floor += " <li><img class='imag1' src='plug-in/login/images/xtgl.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/xtgl_up.png' style='display: none;' />" + " </li> ";
                }else if("常用示例".equals(function.getFunctionName())){

                    floor += " <li><img class='imag1' src='plug-in/login/images/cysl.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/cysl_up.png' style='display: none;' />" + " </li> ";
                }else if("系统监控".equals(function.getFunctionName())){

                    floor += " <li><img class='imag1' src='plug-in/login/images/xtjk.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/xtjk_up.png' style='display: none;' />" + " </li> ";
                }else{
                    //其他的为默认通用的图片模式
                    String s = "";
                    if(function.getFunctionName().length()>=5 && function.getFunctionName().length()<7){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ function.getFunctionName() +"</span></div>";
                    }else if(function.getFunctionName().length()<5){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'>"+ function.getFunctionName() +"</div>";
                    }else if(function.getFunctionName().length()>=7){
                        s = "<div style='width:67px;position: absolute;top:40px;text-align:center;color:#909090;font-size:12px;'><span style='letter-spacing:-1px;'>"+ function.getFunctionName().substring(0, 6) +"</span></div>";
                    }
                    floor += " <li style='position: relative;'><img class='imag1' src='plug-in/login/images/default.png' /> "
                            + " <img class='imag2' src='plug-in/login/images/default_up.png' style='display: none;' />"
                            + s +"</li> ";
                }
            }
        }
		
		return floor;
	}
	

	/**
	 * 返回数据
	 */
	@RequestMapping(params = "getPrimaryMenuForWebos")
	@ResponseBody
	public AjaxJson getPrimaryMenuForWebos() {
		AjaxJson j = new AjaxJson();
		//将菜单加载到Session，用户只在登录的时候加载一次
		Object getPrimaryMenuForWebos =  ContextHolderUtils.getSession().getAttribute("getPrimaryMenuForWebos");
		if(ConvertUtils.isNotEmpty(getPrimaryMenuForWebos)){
			j.setMsg(getPrimaryMenuForWebos.toString());
		}else{
			String PMenu = ListtoMenu.getWebosMenu(getFunctionMap(ResourceUtil.getCurrentSessionUser()));
			ContextHolderUtils.getSession().setAttribute("getPrimaryMenuForWebos", PMenu);
			j.setMsg(PMenu);
		}
		return j;
	}
}
