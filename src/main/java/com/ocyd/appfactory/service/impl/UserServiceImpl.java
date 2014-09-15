package com.ocyd.appfactory.service.impl;

import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.ocyd.appfactory.service.UserService;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author  张代浩
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public TUser checkUserExits(TUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	public void pwdInit(TUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
//		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
//		criteria.add(Restrictions.eq("TSRole.id", id));
//		int allCounts = ((Long) criteria.setProjection(
//				Projections.rowCount()).uniqueResult()).intValue();
//		return allCounts;
        return 0;
	}
	
}
