
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="articleList" title="" actionUrl="advertController.do?datagrid" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="编号" field="id" hidden="false"/>
    <t:dgCol title="广告名" field="name" query="false"/>
    <t:dgCol title="图标" field="icon" image="true" imageSize="24,24" query="false"/>
    <t:dgCol title="URL" field="webLink" query="false"/>
    <%--<t:dgCol title="最后更新时间" field="lastUpdate" query="false"/>--%>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="advertController.do?del&id={id}" />
    <t:dgToolBar title="添加" icon="icon-add" url="advertController.do?addorupdate" funname="add"/>
    <t:dgToolBar title="修改" icon="icon-edit" url="advertController.do?addorupdate" funname="update"/>
</t:datagrid>
</div>
</div>
