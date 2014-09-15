package com.ocyd.appfactory.aop;

import java.io.Serializable;
import java.util.Date;


import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.jeecgframework.core.util.ResourceUtil;
import com.ocyd.jeecgframework.core.util.ConvertUtils;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

/**
 * Hiberate拦截器：实现创建人，创建时间，创建人名称自动注入;
 *                修改人,修改时间,修改人名自动注入;
 * @author  张代浩
 */
@Component
public class HibernateAspect extends EmptyInterceptor {
	private static final Logger logger = Logger.getLogger(HibernateAspect.class);
	private static final long serialVersionUID = 1L;



public boolean onSave(Object entity, Serializable id, Object[] state,
		String[] propertyNames, Type[] types) {
	TUser currentUser = null;
	try {
		currentUser = ResourceUtil.getCurrentSessionUser();
	} catch (RuntimeException e) {
		logger.warn("当前session为空,无法获取用户");
	}
	if(currentUser==null){
		return true;
	}
	try {
		//添加数据
		 for (int index=0;index<propertyNames.length;index++)
		 {
		     /*找到名为"创建时间"的属性*/
		     if ("createDate".equals(propertyNames[index])
		    		 ||"createDatetime".equals(propertyNames[index]))
		     {
		         /*使用拦截器将对象的"创建时间"属性赋上值*/
		    	 if(ConvertUtils.isEmpty(state[index])){
		    		 state[index] = new Date();
		    	 }
		     }
		     /*找到名为"创建人"的属性*/
		     else if ("createBy".equals(propertyNames[index]))
		     {
		         /*使用拦截器将对象的"创建人"属性赋上值*/
		    	 if(ConvertUtils.isEmpty(state[index])){
		    		  state[index] = currentUser.getId();
		    	 }
             }
		     /*找到名为"创建人Key"的属性*/
		     else if ("createKey".equals(propertyNames[index]))
		     {
		         /*使用拦截器将对象的"创建人Key"属性赋上值*/
		    	 if(ConvertUtils.isEmpty(state[index])){
		    		  state[index] = currentUser.getAccountName();
		    	 }
             }
		     /*找到名为"创建人名称"的属性*/
		     else if ("createName".equals(propertyNames[index]))
		     {
		         /*使用拦截器将对象的"创建人名称"属性赋上值*/
		    	 if(ConvertUtils.isEmpty(state[index])){
		    		 state[index] = currentUser.getAccountName();
		    	 }
             }
		 }
	} catch (RuntimeException e) {
		e.printStackTrace();
	}
	 return true;
}


public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
	TUser currentUser = null;
	try {
		currentUser = ResourceUtil.getCurrentSessionUser();
	} catch (RuntimeException e1) {
		logger.warn("当前session为空,无法获取用户");
	}
	if(currentUser==null){
		return true;
	}
	//添加数据
     for (int index=0;index<propertyNames.length;index++)
     {
         /*找到名为"修改时间"的属性*/
         if ("updateDate".equals(propertyNames[index])
        		 ||"updateDatetime".equals(propertyNames[index]))
         {
             /*使用拦截器将对象的"修改时间"属性赋上值*/
        	 currentState[index] = new Date();
         }
         /*找到名为"修改人"的属性*/
         else if ("updateBy".equals(propertyNames[index]))
         {
             /*使用拦截器将对象的"修改人"属性赋上值*/
        	 currentState[index] = currentUser.getId();
         }
         /*找到名为"修改人"的属性*/
         else if ("updateName".equals(propertyNames[index]))
         {
             /*使用拦截器将对象的"修改人"属性赋上值*/
        	 currentState[index] = currentUser.getAccountName();
         }
         /*找到名为"修改人名称"的属性*/
         else if ("updateRealname".equals(propertyNames[index]))
         {
             /*使用拦截器将对象的"修改人名称"属性赋上值*/
        	 currentState[index] = currentUser.getAccountName();
         }
     }
	 return true;
}
}
