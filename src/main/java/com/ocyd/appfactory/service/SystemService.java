package com.ocyd.appfactory.service;

import java.util.List;
import java.util.Set;

import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.jeecgframework.core.common.service.CommonService;

/**
 * 
 * @author  张代浩
 *
 */
public interface SystemService extends CommonService {

	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public TUser checkUserExits(TUser user) throws Exception;
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 * @param TUser 操作人
	 */
	public void addLog(String LogContent, Short loglevel,Short operatetype);
}
