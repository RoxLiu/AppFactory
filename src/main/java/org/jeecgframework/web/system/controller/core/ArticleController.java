package org.jeecgframework.web.system.controller.core;

import com.ocyd.appfactory.pojo.TArticle;
import com.ocyd.appfactory.pojo.TUser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


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

/*###################################################################################################################*/



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
			j.setSuccess(false);
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
			user.setPassword(PasswordUtil.encrypt(user.getAccountName(), password, PasswordUtil.getStaticSalt()));
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


	public void delRoleUser(TSUser user) {
		// 同步删除用户角色关联表
		List<TSRoleUser> roleUserList = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (TSRoleUser tRoleUser : roleUserList) {
				systemService.delete(tRoleUser);
			}
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
		// 得到用户的角色
//		String roleid = oConvertUtils.getString(req.getParameter("roleid"));
		
		String password = ConvertUtils.getString(req.getParameter("password"));
		if (user.getId() > 0) {
			TUser found = systemService.getEntity(TUser.class, user.getId());
            found.setPhone(user.getPhone());
            found.setEmail(user.getEmail());
			systemService.updateEntity(found);
//			List<TSRoleUser> ru = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
//			systemService.deleteAllEntities(ru);
			message = "用户: " + found.getAccountName() + "更新成功";
//			if (StringUtil.isNotEmpty(roleid)) {
//				saveRoleUser(found, roleid);
//			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			TUser users = systemService.findUniqueByProperty(TUser.class, "accountName",user.getAccountName());
			if (users != null) {
				message = "用户: " + users.getAccountName() + "已经存在";
			} else {
				
//				String shopSymbol = req.getParameter("shopSymbol");
//				if(shopSymbol!=null&&"shop".equals(shopSymbol)){
//					TSRole tsRole =this.systemService.findUniqueByProperty(TSRole.class,"roleCode", "manager");
//					roleid = tsRole.getId();
//				}
				
				user.setPassword(PasswordUtil.encrypt(user.getAccountName(), password, PasswordUtil.getStaticSalt()));
		
//				if (user.getTSDepart().equals("")) {
//					user.setTSDepart(null);
//				}
				user.setStatus(1); // 正常。
                user.setType(2); //设为普通用户

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                user.setCreateTime(formatter.format(new Date()));
				systemService.save(user);
				message = "用户: " + user.getAccountName() + "添加成功";
//				if (StringUtil.isNotEmpty(roleid)) {
//					saveRoleUser(user, roleid);
//				}
				
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}

		}
		j.setMsg(message);

		return j;
	}

	protected void saveRoleUser(TSUser user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleUser rUser = new TSRoleUser();
			TSRole role = systemService.getEntity(TSRole.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			systemService.save(rUser);

		}
	}

	/**
	 * 用户选择角色跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "roles")
	public String roles() {
		return "system/user/modules";
	}

	/**
	 * 角色显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridRole")
	public void datagridRole(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
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