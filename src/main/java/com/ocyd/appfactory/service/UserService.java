package com.ocyd.appfactory.service;

import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.jeecgframework.core.common.service.CommonService;

/**
 * 
 * @author  张代浩
 *
 */
public interface UserService extends CommonService {

	public TUser checkUserExits(TUser user);
	public void pwdInit(TUser user, String newPwd);
	/**
	 * 判断这个角色是不是还有用户使用
	 *@Author JueYue
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);
}
