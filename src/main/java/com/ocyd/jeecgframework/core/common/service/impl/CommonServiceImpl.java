package com.ocyd.jeecgframework.core.common.service.impl;

import com.ocyd.jeecgframework.core.common.dao.ICommonDao;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import com.ocyd.jeecgframework.core.common.hibernate.qbc.PageList;
import com.ocyd.jeecgframework.core.common.model.common.DBTable;
import com.ocyd.jeecgframework.core.common.model.common.UploadFile;
import com.ocyd.jeecgframework.core.common.model.json.ComboTree;
import com.ocyd.jeecgframework.core.common.model.json.DataGridReturn;
import com.ocyd.jeecgframework.core.common.model.json.ImportFile;
import com.ocyd.jeecgframework.core.common.service.CommonService;
import com.ocyd.jeecgframework.tag.vo.datatable.DataTableReturn;
import com.ocyd.jeecgframework.tag.vo.easyui.AutoComplete;
import com.ocyd.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	public ICommonDao commonDao = null;

	/**
	 * 获取所有数据库表
	 * 
	 * @return
	 */
	public List<DBTable> getAllDbTableName() {
		return commonDao.getAllDbTableName();
	}

	public Integer getAllDbTableSize() {
		return commonDao.getAllDbTableSize();
	}

	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	
	public <T> Serializable save(T entity) {
		return commonDao.save(entity);
	}

	
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);

	}

	
	public <T> void delete(T entity) {
		commonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntities(Collection<T> entities) {
		commonDao.deleteAllEntities(entities);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T get(Class<T> class1, Serializable id) {
		return commonDao.get(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @param hql
	 * @param size
	 * @return
	 */
	public <T> List<T> getList(Class clas) {
		return commonDao.loadAll(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T getEntity(Class entityName, Serializable id) {
		return commonDao.getEntity(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {

		return commonDao.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.loadAll(entityClass);
	}

	public <T> T singleResult(String hql) {
		return commonDao.singleResult(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntity(T pojo) {
		commonDao.updateEntity(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findByQueryString(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @param query
	 * @return
	 */
	public int updateBySqlString(String sql) {
		return commonDao.updateBySqlString(sql);
	}

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findListBySql(String query) {
		return commonDao.findListBySql(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	public <T> List<T> findByPropertiesOrder(Class<T> entityClass,
                                             String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertiesOrder(entityClass, propertyName,
                value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getPageList(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return commonDao.getDataGridReturn(cq, isOffset);
	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final HqlQuery hqlQuery,
			final boolean needParameter) {
		return commonDao.getPageList(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageListBySql(final HqlQuery hqlQuery,
			final boolean isToEntity) {
		return commonDao.getPageListBySql(hqlQuery, isToEntity);
	}

	public Session getSession()

	{
		return commonDao.getSession();
	}

	public List findByExample(final String entityName,
			final Object exampleEntity) {
		return commonDao.findByExample(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,
			Boolean ispage) {
		return commonDao.getListByCriteriaQuery(cq, ispage);
	}

	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return commonDao.viewOrDownloadFile(uploadFile);
	}

	/**
	 * 生成XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 * @return
	 */
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	/**
	 * 根据模型生成JSON
	 * 
	 * @param all
	 *            全部对象
	 * @param in
	 *            已拥有的对象
	 * @param comboBox
	 *            模型
	 * @return
	 */
	public List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel,
			List in) {
		return commonDao.ComboTree(all, comboTreeModel, in);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getAutoList(AutoComplete autoComplete) {
		StringBuffer sb = new StringBuffer("");
		for (String searchField : autoComplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"
					+ autoComplete.getTrem() + "%' ");
		}
		String hql = "from " + autoComplete.getEntityName() + " where 1!=1 "
				+ sb.toString();
		return commonDao.getSession().createQuery(hql)
				.setFirstResult(autoComplete.getCurPage() - 1)
				.setMaxResults(autoComplete.getMaxRows()).list();
	}

	
	public Integer executeSql(String sql, List<Object> param) {
		return commonDao.executeSql(sql, param);
	}

	
	public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}

	
	public Integer executeSql(String sql, Map<String, Object> param) {
		return commonDao.executeSql(sql, param);
	}
	
	public Object executeSqlReturnKey(String sql, Map<String, Object> param) {
		return commonDao.executeSqlReturnKey(sql, param);
	}
	
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return commonDao.findForJdbc(sql, page, rows);
	}

	
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findForJdbc(sql, objs);
	}

	
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		return commonDao.findForJdbcParam(sql, page, rows, objs);
	}

	
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		return commonDao.findObjForJdbc(sql, page, rows, clazz);
	}

	
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.findOneForJdbc(sql, objs);
	}

	
	public Long getCountForJdbc(String sql) {
		return commonDao.getCountForJdbc(sql);
	}

	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return commonDao.getCountForJdbcParam(sql,objs);
	}

	
	public <T> void batchSave(List<T> entities) {
		this.commonDao.batchSave(entities);
	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param) {
		return this.commonDao.findHql(hql, param);
	}

	public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
			int maxResult) {
		return this.commonDao.pageList(dc, firstResult, maxResult);
	}

	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return this.commonDao.findByDetached(dc);
	}

}
