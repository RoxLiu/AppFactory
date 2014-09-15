<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<html>
<body style="overflow-y: hidden" scroll="no">

<t:datagrid name="productList" title="" actionUrl="productController.do?datagrid&connectId=${connectId}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="编号" field="id" hidden="false"/>
	<t:dgCol title="商品名称" sortable="false" field="name" query="false"/>
	<t:dgCol title="简介" field="brief" query="false"/>
    <t:dgCol title="链接"  field="webLink" query="false"/>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="productController.do?del&id={id}" />
    <t:dgToolBar title="添加" icon="icon-add" url="productController.do?addorupdatepersonproduct&connectId=${connectId}" funname="add"/>
    <t:dgToolBar title="修改" icon="icon-edit" url="productController.do?addorupdatepersonproduct" funname="update"/>
</t:datagrid>
</body>
</html>