package com.ocyd.appfactory.controller;

import javax.servlet.http.HttpServletRequest;

import com.ocyd.appfactory.pojo.DuplicateCheckPage;
import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.jeecgframework.core.common.model.json.AjaxJson;
import com.ocyd.jeecgframework.core.util.ResourceUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.ocyd.jeecgframework.core.common.controller.BaseController;
import com.ocyd.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**   
 * @Title: Action
 * @Description: 校验工具Action
 * @author 张代浩
 * @date 2013-09-12 22:27:30
 * @version V1.0   
 */
@Controller
@RequestMapping("/duplicateCheckAction")
public class DuplicateCheckAction extends BaseController {

	private static final Logger logger = Logger.getLogger(DuplicateCheckAction.class);

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	
	/**
	 * 校验数据是否在系统中是否存在
	 * @return
	 */
	@RequestMapping(params = "doDuplicateCheck")
	@ResponseBody
	public AjaxJson doDuplicateCheck(DuplicateCheckPage duplicateCheckPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Long num = null;

        //为了兼容id为数字型的情况，如果传入的id值为"0"，也认为对应的object不存在。（如果真的有id为"0"的object，会出现不能正常工作的情况).
		if(StringUtils.isNotBlank(duplicateCheckPage.getRowObid()) && !"0".equals(duplicateCheckPage.getRowObid())){
			//[2].编辑页面校验
			String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName() +" WHERE "+duplicateCheckPage.getFieldName() +" =? and id != ?";
			num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue(),duplicateCheckPage.getRowObid()});
		}else{
			//[1].添加页面校验
            if("user_account".equals(duplicateCheckPage.getTableName())) {
                TUser user = ResourceUtil.getCurrentSessionUser();
                //当前用户为App管理员，增加的用户为普通用户，只要求在同一个shop下唯一。
                if (user.getType() == TUser.TYPE_APP_ADMINISTRATOR) {
                    //检查shopId为空（系统管理员）的情况下，该帐号是否已经使用。
                    String sql = "SELECT count(*) FROM user_account WHERE "+duplicateCheckPage.getFieldName() +" =? and user_shopid=''";
                    num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});

                    //检查shopId为0(系统管理员)的情况下，该帐号是否已经使用。
                    if(num==null||num==0){
                        sql = "SELECT count(*) FROM user_account WHERE "+duplicateCheckPage.getFieldName() +" =? and user_shopid='0'";
                        num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});
                    }

                    if(num==null||num==0){
                        sql = "SELECT count(*) FROM user_account WHERE "+duplicateCheckPage.getFieldName() +" =? and user_shopid=?";
                        num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue(), user.getShopId()});
                    }
                } else {
                    String sql = "SELECT count(*) FROM user_account WHERE "+duplicateCheckPage.getFieldName() +" =?";
                    num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});
                }
            } else {
                String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName() +" WHERE "+duplicateCheckPage.getFieldName() +" =?";
                num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});
            }
		}
		
		if(num==null||num==0){
			//该值可用
			j.setSuccess(true);
			j.setMsg("该值可用！");
		}else{
			//该值不可用
			j.setSuccess(false);
			j.setMsg("该值不可用，系统中已存在！");
		}
		return j;
	}
}
