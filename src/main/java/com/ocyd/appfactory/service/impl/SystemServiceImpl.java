package com.ocyd.appfactory.service.impl;

import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.appfactory.service.SystemService;
import com.ocyd.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.ocyd.jeecgframework.core.util.BrowserUtils;
import com.ocyd.jeecgframework.core.util.ContextHolderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {

	public TUser checkUserExits(TUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}


	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
        //TODO: write log.
//		HttpServletRequest request = ContextHolderUtils.getRequest();
//		String broswer = BrowserUtils.checkBrowse(request);
//		TSLog log = new TSLog();
//		log.setLogcontent(logcontent);
//		log.setLoglevel(loglevel);
//		log.setOperatetype(operatetype);
//		log.setNote(ConvertUtils.getIp());
//		log.setBroswer(broswer);
//		log.setOperatetime(DataUtils.gettimestamp());
//		log.setTSUser(ResourceUtil.getSessionUserName());
//		commonDao.save(log);
	}
}
