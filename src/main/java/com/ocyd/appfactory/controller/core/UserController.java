package com.ocyd.appfactory.controller.core;

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
import com.ocyd.jeecgframework.core.common.model.common.UploadFile;
import com.ocyd.jeecgframework.core.common.model.json.AjaxJson;
import com.ocyd.jeecgframework.core.common.model.json.DataGrid;
import com.ocyd.jeecgframework.core.common.model.json.ValidForm;
import com.ocyd.jeecgframework.core.constant.Globals;
import com.ocyd.jeecgframework.core.util.*;
import com.ocyd.jeecgframework.tag.core.easyui.TagUtil;
import com.ocyd.appfactory.manager.ClientManager;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.appfactory.service.UserService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import com.ocyd.jeecgframework.core.common.model.json.ComboBox;
import com.ocyd.jeecgframework.tag.vo.datatable.DataTableReturn;
import com.ocyd.jeecgframework.tag.vo.datatable.DataTables;
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
        List<TUser> roles=systemService.findByProperty(TUser.class, "userName", userName);
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
		TUser user = ResourceUtil.getCurrentSessionUser();
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
		TUser user = ResourceUtil.getCurrentSessionUser();
		String password = ConvertUtils.getString(request.getParameter("password"));
		String newpassword = ConvertUtils.getString(request.getParameter("newpassword"));
		if (!password.equals(user.getPassword())) {
			j.setMsg("原密码不正确");
		} else {
			try {
				user.setPassword(newpassword);
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
}