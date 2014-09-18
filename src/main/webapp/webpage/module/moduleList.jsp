<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<body style="overflow-y: hidden" scroll="no">

<t:datagrid name="moduleList" title="" actionUrl="moduleController.do?datagrid" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="编号" field="id" hidden="false"/>
	<t:dgCol title="模块名称" field="name" sortable="false" query="false"/>
    <t:dgCol title="描述" field="description" query="false"/>
    <t:dgCol title="状态" field="status" sortable="false" replace="正常_1,禁用_0,删除_-1" />
<%--
    <t:dgCol title="创建时间" field="createTime" query="false"/>
    <t:dgCol title="最后更新时间" field="createTime" query="false"/>
--%>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="moduleController.do?del&id={id}" />
    <t:dgToolBar title="添加" icon="icon-add" url="moduleController.do?addorupdate" funname="add"/>
	<t:dgToolBar title="修改" icon="icon-edit" url="moduleController.do?addorupdate" funname="update"/>
</t:datagrid>
</body>
</html>