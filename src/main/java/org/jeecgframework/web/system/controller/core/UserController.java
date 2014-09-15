package org.jeecgframework.web.system.controller.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocyd.appfactory.pojo.TModule;
import com.ocyd.appfactory.pojo.TShopAccount;
import com.ocyd.appfactory.pojo.TShopModule;
import com.ocyd.appfactory.pojo.TUser;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSFunction;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleFunction;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ClassName: UserController
 * @Description: TODO(用户管理处理类)
 * @author 张代浩
 */
@Scope("prototype")
@Controller
@RequestMapping("/userController")
public class UserController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserController.class);

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
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "userList")
	public String user(HttpServletRequest request) {
        TUser user = ResourceUtil.getCurrentSessionUser();
        request.setAttribute("user", user);

        if(user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
            return "system/user/appUserList";
        } else {
            return "system/user/userList";
        }
	}


    /**
     * easyuiAJAX用户列表请求数据
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TUser.class, dataGrid);
        //查询条件组装器
        TUser user = ResourceUtil.getCurrentSessionUser();
//		HqlGenerateUtil.installHql(cq, user);
        if(user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
            cq.eq("type", TUser.TYPE_APP_ADMINISTRATOR);
            cq.in("status", new Integer[] { TUser.STATUS_LOCKED, TUser.STATUS_NORMAL});
        } else {
            cq.eq("shopId", user.getShopId());
            cq.in("status", new Integer[] { TUser.STATUS_LOCKED, TUser.STATUS_NORMAL});
        }
        cq.add();

        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * easyuiAJAX请求数据： 增加修改用户
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(HttpServletRequest req, TUser user) {
        if (user.getId() > 0) {
            user = systemService.getEntity(TUser.class, user.getId());
            req.setAttribute("user", user);
        } else {
            req.setAttribute("user", new TUser());
        }

        TUser current = ResourceUtil.getCurrentSessionUser();

        if(current.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
            if(user.getId() > 0) {
                List<TShopModule> moduleList = systemService.findByProperty(TShopModule.class, "shopId", user.getShopId());

                StringBuilder idBuilder = new StringBuilder();
                StringBuilder nameBuilder = new StringBuilder();
                for (TShopModule module : moduleList) {
                    idBuilder.append(module.getId()).append(",");
                    nameBuilder.append(module.getModuleName()).append(",");
                }

                if(idBuilder.length() > 0) {
                    req.setAttribute("id", idBuilder.substring(0, idBuilder.length() - 1));
                }

                if(nameBuilder.length() > 0) {
                    req.setAttribute("name", nameBuilder.substring(0, nameBuilder.length() - 1));
                }
            }
            return new ModelAndView("system/user/appUser");
        } else {
            return new ModelAndView("system/user/user");
        }
    }

    /**
     * 检查用户名
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "checkUser")
    @ResponseBody
    public ValidForm checkUser(HttpServletRequest request) {
        ValidForm v = new ValidForm();
        String userName= ConvertUtils.getString(request.getParameter("param"));
        String code= ConvertUtils.getString(request.getParameter("code"));
        List<TSUser> roles=systemService.findByProperty(TSUser.class, "userName", userName);
        if(roles.size()>0&&!code.equals(userName))
        {
            v.setInfo("用户名已存在");
            v.setStatus("n");
        }
        return v;
    }

    /**
     * 用户录入
     *
     * @param user
     * @param req
     * @return
     */

    @RequestMapping(params = "saveUser")
    @ResponseBody
    public AjaxJson saveUser(HttpServletRequest req, TUser user) {
        AjaxJson j = new AjaxJson();
        String password = ConvertUtils.getString(req.getParameter("password"));
        if (user.getId() > 0) {
            TUser found = systemService.getEntity(TUser.class, user.getId());
            found.setPhone(user.getPhone());
            found.setEmail(user.getEmail());
            systemService.updateEntity(found);
            message = "用户: " + found.getAccountName() + "更新成功";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            TUser users = systemService.findUniqueByProperty(TUser.class, "accountName",user.getAccountName());
            if (users != null) {
                message = "用户: " + users.getAccountName() + "已经存在";
            } else {
                user.setPassword(PasswordUtil.encrypt(user.getAccountName(), password, PasswordUtil.getStaticSalt()));

                user.setStatus(TUser.STATUS_NORMAL); // 正常。
                user.setType(TUser.TYPE_TERMINAL_USER); //设为普通用户
                user.setShopId(ResourceUtil.getCurrentSessionUser().getShopId());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                user.setCreateTime(formatter.format(new Date()));
                systemService.save(user);
                message = "用户: " + user.getAccountName() + "添加成功";

                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }

        }
        j.setMsg(message);

        return j;
    }


    /**
     * 模块选择跳转页面
     *
     * @return
     */
    @RequestMapping(params = "modules")
    public String modules() {
        return "system/user/modules";
    }

    /**
     * 模块显示列表
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "moduledatagrid")
    public void moduleDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TModule.class, dataGrid);
        cq.eq("status", TModule.STATUS_NORMAL);
        cq.add();

        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * APP用户录入：增加shop_account、user_account、shop_module
     *
     * @param user
     * @param req
     * @return
     */

    @RequestMapping(params = "saveAppUser")
    @ResponseBody
    public AjaxJson saveAppUser(HttpServletRequest req, TUser user) {
        AjaxJson j = new AjaxJson();
        String moduleId = req.getParameter("moduleId");

        String password = ConvertUtils.getString(req.getParameter("password"));
        if (user.getId() > 0) {
            TUser found = systemService.getEntity(TUser.class, user.getId());
            found.setPhone(user.getPhone());
            found.setEmail(user.getEmail());
            systemService.updateEntity(found);

            if(moduleId != null) {
                saveShopModule(found, moduleId);
            }

            message = "用户: " + found.getAccountName() + "更新成功";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            //1. 创建shop_account
            //2. 创建user_account
            //3. 创建shop_module
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(new Date());

            TShopAccount shop = new TShopAccount();
            shop.setAccountName(user.getAccountName());
            shop.setPassword(password);
            shop.setPhone(user.getPhone());
            shop.setEmail(user.getEmail());
            shop.setStatus(TShopAccount.STATUS_NORMAL);
            shop.setType(1);
            shop.setCreateTime(date);
            int shopId = (Integer)systemService.save(shop);

//            user.setPassword(PasswordUtil.encrypt(user.getAccountName(), password, PasswordUtil.getStaticSalt()));
            user.setPassword(password);
            user.setStatus(TUser.STATUS_NORMAL); // 正常。
            user.setType(TUser.TYPE_APP_ADMINISTRATOR); //设为普通用户
            user.setShopId(shopId);
            user.setCreateTime(date);
            systemService.save(user);

            if(moduleId != null) {
                saveShopModule(user, moduleId);
            }

            message = "用户: " + user.getAccountName() + "添加成功";
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }

        j.setMsg(message);

        return j;
    }

    private void saveShopModule(TUser user, String modules) {
        // 同步删除用户角色关联表
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(new Date());
        List<TShopModule> moduleList = systemService.findByProperty(TShopModule.class, "shopId", user.getShopId());

        //1.先将所有的数据设为删除。
        for (TShopModule module : moduleList) {
            module.setStatus(TShopModule.STATUS_DELETED);
            module.setLastUpdate(date);
        }

        String[] array = modules.split(",");
        for (int i = 0; i < array.length; i++) {
            int moduleId = Integer.parseInt(array[i]);
            TShopModule shopModule = findInList(moduleList, moduleId);
            //2. 如果数据库中没有，增加。
            if(shopModule == null) {
                shopModule = new TShopModule();
                TModule module = systemService.getEntity(TModule.class, moduleId);

                shopModule.setShopId(user.getShopId());
                shopModule.setModuleId(moduleId);
                shopModule.setModuleName(module.getName());
                shopModule.setStatus(TShopModule.STATUS_NORMAL);

                shopModule.setCreateTime(date);
                shopModule.setLastUpdate(date);

                systemService.save(shopModule);
            } else {
                //3.数据库中存在，用户也没有删除，将moduleList中的数据恢复为正常
                shopModule.setStatus(TShopModule.STATUS_NORMAL);
            }
        }

        //4. 将数据库中存在，但用户没有再选择的数据保存为删除。
        for (TShopModule module : moduleList) {
            if(module.getStatus() == TShopModule.STATUS_DELETED) {
                systemService.save(module);
            }
        }
    }

    private TShopModule findInList(List<TShopModule> moduleList, int moduleId) {
        for(TShopModule shopModule : moduleList) {
            if(shopModule.getModuleId() == moduleId) {
                return shopModule;
            }
        }

        return null;
    }

    /**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "changepassword")
	public String changepassword(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		return "system/user/changepassword";
	}
	
	

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "savenewpwd")
	@ResponseBody
	public AjaxJson savenewpwd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		String password = ConvertUtils.getString(request.getParameter("password"));
		String newpassword = ConvertUtils.getString(request.getParameter("newpassword"));
		String pString = PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt());
		if (!pString.equals(user.getPassword())) {
			j.setMsg("原密码不正确");
		} else {
			try {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), newpassword, PasswordUtil.getStaticSalt()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemService.updateEntity(user);
			j.setMsg("修改成功");

		}
		return j;
	}

	/**
	 * 
	 * 修改用户密码
	 * @author Chj
	 */
	
	@RequestMapping(params = "changepasswordforuser")
	public ModelAndView changepasswordforuser(TUser user, HttpServletRequest req) {
		if (user.getId() > 0) {
			user = systemService.getEntity(TUser.class, user.getId());
			req.setAttribute("user", user);
//			idandname(req, user);
			LogUtil.info(user.getPassword() + "-----" + user.getAccountName());
		}
		return new ModelAndView("system/user/adminchangepwd");
	}

	
	@RequestMapping(params = "savenewpwdforuser")
	@ResponseBody
	public AjaxJson savenewpwdforuser(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
        int id = Integer.parseInt(req.getParameter("id"));
		String password = ConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(id)) {
			TUser user = systemService.getEntity(TUser.class,id);
			LogUtil.info("重置用户[" + user.getAccountName() + "]密码.");
//			user.setPassword(PasswordUtil.encrypt(user.getAccountName(), password, PasswordUtil.getStaticSalt()));
            user.setPassword(password);
//			user.setStatus(Globals.User_Normal);
//			user.setActivitiSync(user.getActivitiSync());
			systemService.updateEntity(user);
			message = "用户[" + user.getAccountName() + "]密码重置成功.";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} 
		
		j.setMsg(message);

		return j;
	}

	/**
	 * 锁定账户
	 */
	@RequestMapping(params = "lock")
	@ResponseBody
	public AjaxJson lock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		TUser user = systemService.getEntity(TUser.class, Integer.parseInt(id));
        if(user == null) {
            message = "该用户不存在.";
            j.setMsg(message);
            return j;
        }

		if("admin".equals(user.getAccountName())){
			message = "超级管理员[admin]不可锁定.";
			j.setMsg(message);
			return j;
		}

		if(user.getStatus() == TUser.STATUS_DELETED){
            message = "锁定账户失败,该用户已经删除.";
		} else {
            user.setStatus(TUser.STATUS_LOCKED);
            userService.updateEntity(user);
            message = "用户[" + user.getAccountName() + "]锁定成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }

		j.setMsg(message);
		return j;
	}


    /**
     * 锁定账户
     */
    @RequestMapping(params = "unlock")
    @ResponseBody
    public AjaxJson unlock(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();

        TUser user = systemService.getEntity(TUser.class, Integer.parseInt(id));
        if(user == null) {
            message = "该用户不存在.";
            j.setMsg(message);
            return j;
        }

        if(user.getStatus() == TUser.STATUS_DELETED){
            message = "解锁账户失败,该用户已经删除.";
        } else {
            user.setStatus(TUser.STATUS_NORMAL);
            userService.updateEntity(user);
            message = "用户[" + user.getAccountName() + "]解锁成功.";
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
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
    public AjaxJson del(TUser user, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        if("admin".equals(user.getAccountName())){
            message = "超级管理员[admin]不可删除";
            j.setMsg(message);
            return j;
        }
        user = systemService.getEntity(TUser.class, user.getId());
        //非管理员用户
        if (user.getType() == TUser.TYPE_SUPPER_ADMINISTRATOR) {
            message = "超级管理员不可删除.";
        } else {
            user.setStatus(TUser.STATUS_DELETED); //将状态置为-1表示删除，不直接从数据库中删除记录。
            userService.save(user);
            message = "用户[" + user.getAccountName() + "]删除成功";

            //
            List<TUser> userList = systemService.findByProperty(TUser.class, "shopId", user.getShopId());

            //shop下已经不存在可用的user，删除相关的shop、shop_module信息。
            if(!hasNormalUser(userList)) {
                TShopAccount account = systemService.findUniqueByProperty(TShopAccount.class, "id", user.getShopId());
                account.setStatus(TShopAccount.STATUS_DELETED);

                systemService.save(account);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = formatter.format(new Date());

                List<TShopModule> moduleList = systemService.findByProperty(TShopModule.class, "shopId", user.getShopId());
                for (TShopModule module : moduleList) {
                    module.setStatus(TShopModule.STATUS_DELETED);
                    module.setLastUpdate(date);
                }

                systemService.batchSave(moduleList);
            }
        }

        j.setMsg(message);
        return j;
    }

    private boolean hasNormalUser(List<TUser> userList) {
        for(TUser user : userList) {
            if(user.getType() == TUser.TYPE_APP_ADMINISTRATOR && user.getStatus() == TUser.STATUS_NORMAL) {
                return true;
            }
        }

        return false;
    }




	/*###############################################################################################################*/


    /**
     * 菜单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "menu")
    public void menu(HttpServletRequest request, HttpServletResponse response) {
        SetListSort sort = new SetListSort();
        TSUser u = ResourceUtil.getSessionUserName();
        // 登陆者的权限
        Set<TSFunction> loginActionlist = new HashSet();// 已有权限菜单
        List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", u.getId());
        for (TSRoleUser ru : rUsers) {
            TSRole role = ru.getTSRole();
            List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
            if (roleFunctionList.size() > 0) {
                for (TSRoleFunction roleFunction : roleFunctionList) {
                    TSFunction function = (TSFunction) roleFunction.getTSFunction();
                    loginActionlist.add(function);
                }
            }
        }
        List<TSFunction> bigActionlist = new ArrayList();// 一级权限菜单
        List<TSFunction> smailActionlist = new ArrayList();// 二级权限菜单
        if (loginActionlist.size() > 0) {
            for (TSFunction function : loginActionlist) {
                if (function.getFunctionLevel() == 0) {
                    bigActionlist.add(function);
                } else if (function.getFunctionLevel() == 1) {
                    smailActionlist.add(function);
                }
            }
        }
        // 菜单栏排序
        Collections.sort(bigActionlist, sort);
        Collections.sort(smailActionlist, sort);
        String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
        // request.setAttribute("loginMenu",logString);
        try {
            response.getWriter().write(logString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户列表页面跳转[跳转到标签和手工结合的html页面]
     *
     * @return
     */
    @RequestMapping(params = "userDemo")
    public String userDemo(HttpServletRequest request) {
        // 给部门查询条件中的下拉框准备数据
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
        return "system/user/userList2";
    }

    /**
     * 用户信息
     *
     * @return
     */
    @RequestMapping(params = "userinfo")
    public String userinfo(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("user", user);
        return "system/user/userinfo";
    }

	/**
	 * 得到角色列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	@ResponseBody
	public List<ComboBox> role(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSRole> roles = new ArrayList();
		if (StringUtil.isNotEmpty(id)) {
			List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", id);
			if (roleUser.size() > 0) {
				for (TSRoleUser ru : roleUser) {
					roles.add(ru.getTSRole());
				}
			}
		}
		List<TSRole> roleList = systemService.getList(TSRole.class);
		comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);
		return comboBoxs;
	}

	/**
	 * 得到部门列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	@ResponseBody
	public List<ComboBox> depart(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSDepart> departs = new ArrayList();
		if (StringUtil.isNotEmpty(id)) {
			TSUser user = systemService.get(TSUser.class, id);
			if (user.getTSDepart() != null) {
				TSDepart depart = systemService.get(TSDepart.class, user.getTSDepart().getId());
				departs.add(depart);
			}
		}
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
		return comboBoxs;
	}


	public void idandname(HttpServletRequest req, TSUser user) {
		List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		String roleId = "";
		String roleName = "";
		if (roleUsers.size() > 0) {
			for (TSRoleUser tRoleUser : roleUsers) {
				roleId += tRoleUser.getTSRole().getId() + ",";
				roleName += tRoleUser.getTSRole().getRoleName() + ",";
			}
		}
		req.setAttribute("id", roleId);
		req.setAttribute("roleName", roleName);
	}

	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "choose")
	public String choose(HttpServletRequest request) {
		List<TSRole> roles = systemService.loadAll(TSRole.class);
		request.setAttribute("roleList", roles);
		return "system/membership/checkuser";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseUser")
	public String chooseUser(HttpServletRequest request) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		request.setAttribute("roleid", roleid);
		request.setAttribute("departid", departid);
		return "system/membership/userlist";
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridUser")
	public void datagridUser(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		if (departid.length() > 0) {
			cq.eq("TDepart.departid", ConvertUtils.getInt(departid, 0));
			cq.add();
		}
		String userid = "";
		if (roleid.length() > 0) {
			List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TRole.roleid", ConvertUtils.getInt(roleid, 0));
			if (roleUsers.size() > 0) {
				for (TSRoleUser tRoleUser : roleUsers) {
					userid += tRoleUser.getTSUser().getId() + ",";
				}
			}
			cq.in("userid", ConvertUtils.getInts(userid.split(",")));
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "roleDepart")
	public String roleDepart(HttpServletRequest request) {
		List<TSRole> roles = systemService.loadAll(TSRole.class);
		request.setAttribute("roleList", roles);
		return "system/membership/roledepart";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseDepart")
	public ModelAndView chooseDepart(HttpServletRequest request) {
		String nodeid = request.getParameter("nodeid");
		ModelAndView modelAndView = null;
		if (nodeid.equals("role")) {
			modelAndView = new ModelAndView("system/membership/users");
		} else {
			modelAndView = new ModelAndView("system/membership/departList");
		}
		return modelAndView;
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridDepart")
	public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 测试
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		String jString = request.getParameter("_dt_json");
		DataTables dataTables = new DataTables(request);
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
		String username = request.getParameter("userName");
		if (username != null) {
			cq.like("userName", username);
			cq.add();
		}
		DataTableReturn dataTableReturn = systemService.getDataTableReturn(cq, true);
		TagUtil.datatable(response, dataTableReturn, "id,userName,mobilePhone,TSDepart_departname");
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "index")
	public String index() {
		return "bootstrap/main";
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "main")
	public String main() {
		return "bootstrap/test";
	}

	/**
	 * 测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "testpage")
	public String testpage(HttpServletRequest request) {
		return "test/test";
	}

	/**
	 * 设置签名跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addsign")
	public ModelAndView addsign(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView("system/user/usersign");
	}

	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "savesign", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savesign(HttpServletRequest req) {
		UploadFile uploadFile = new UploadFile(req);
		String id = uploadFile.get("id");
		TSUser user = systemService.getEntity(TSUser.class, id);
		uploadFile.setRealPath("signatureFile");
		uploadFile.setCusPath("signature");
		uploadFile.setByteField("signature");
		uploadFile.setBasePath("resources");
		uploadFile.setRename(false);
		uploadFile.setObject(user);
		AjaxJson j = new AjaxJson();
		message = user.getUserName() + "设置签名成功";
		systemService.uploadFile(uploadFile);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		j.setMsg(message);

		return j;
	}
	/**
	 * 测试组合查询功能
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "testSearch")
	public void testSearch(TSUser user, HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		if(user.getUserName()!=null){
			cq.like("userName", user.getUserName());
		}
		if(user.getRealName()!=null){
			cq.like("realName", user.getRealName());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "changestyle")
	public String changeStyle(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(user==null){
			return "login/login";
		}
		String indexStyle = "shortcut";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie==null || StringUtils.isEmpty(cookie.getName())){
				continue;
			}
			if(cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")){
				indexStyle = cookie.getValue();
			}
		}
		request.setAttribute("indexStyle", indexStyle);
		return "system/user/changestyle";
	}
	/**
	* @Title: saveStyle
	* @Description: 修改首页样式
	* @param request
	* @return AjaxJson    
	* @throws
	 */
	@RequestMapping(params = "savestyle")
	@ResponseBody
	public AjaxJson saveStyle(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(Boolean.FALSE);
		TSUser user = ResourceUtil.getSessionUserName();
		if(user!=null){
			String indexStyle = request.getParameter("indexStyle");
			if(StringUtils.isNotEmpty(indexStyle)){
				Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);
				//设置cookie有效期为一个月
				cookie.setMaxAge(3600*24*30);
				response.addCookie(cookie);
				j.setSuccess(Boolean.TRUE);
				j.setMsg("样式修改成功，请刷新页面");
			}
            ClientManager.getInstance().getClient().getFunctions().clear();
		}else{
			j.setMsg("请登录后再操作");
		}
		return j;
	}
}