<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<t:base type="jquery,easyui,tools"/>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid name="moduleList" title="选择模块" actionUrl="userController.do?moduledatagrid" idField="id" checkbox="true" showRefresh="false">
	<t:dgCol title="编号" field="id" hidden="false"/>
	<t:dgCol title="模块名称" field="name" width="50"/>
    <t:dgCol title="模块描述" field="description" width="50"/>
</t:datagrid>
</body>
</html>
