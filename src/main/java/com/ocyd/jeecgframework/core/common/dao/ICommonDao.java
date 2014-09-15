package com.ocyd.jeecgframework.core.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ocyd.appfactory.pojo.TUser;
import com.ocyd.jeecgframework.core.common.model.json.ComboTree;
import com.ocyd.jeecgframework.tag.vo.easyui.ComboTreeModel;

import com.ocyd.jeecgframework.core.common.model.common.UploadFile;
import com.ocyd.jeecgframework.core.common.model.json.ImportFile;
import com.ocyd.jeecgframework.core.common.model.json.TreeGrid;
import com.ocyd.jeecgframework.core.extend.template.Template;
import com.ocyd.jeecgframework.tag.vo.easyui.TreeGridModel;

public interface ICommonDao extends IGenericBaseCommonDao{
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	public void pwdInit(TUser user,String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	public TUser getUserByUserIdAndUserNameExits(TUser user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	/**
	 * 根据模型生成JSON
	 * @param all 全部对象
	 * @param in  已拥有的对象
	 * @param comboBox 模型
	 * @return
	 */
	public  List<ComboTree> ComboTree(List all,ComboTreeModel comboTreeModel,List in);
}

