
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="articleList" title="" actionUrl="articleController.do?datagrid&connectId=${connectId}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="编号" field="id" hidden="false"/>
    <t:dgCol title="图标" field="icon" image="true" imageSize="24,24" query="false"/>
    <t:dgCol title="标题" field="title" query="false"/>
    <t:dgCol title="描述" field="description" query="false"/>
    <t:dgCol title="URL" field="content" query="false"/>
    <t:dgCol title="关键字" field="keyWords" query="false"/>
    <%--<t:dgCol title="状态" field="status" sortable="false" replace="正常_1,禁用_0,删除_-1" />--%>
    <t:dgCol title="创建者" field="createUserId" query="false"/>
    <t:dgCol title="发布者" field="publishUserId" query="false"/>
<%--
    <t:dgCol title="创建时间" field="createTime" query="false"/>
    <t:dgCol title="发布时间" field="publishTime" query="false"/>
    <t:dgCol title="最后更新时间" field="lastUpdate" query="false"/>
--%>
    <t:dgCol title="网址" field="webLink" query="false"/>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="articleController.do?del&id={id}" />
    <t:dgToolBar title="添加" icon="icon-add" url="articleController.do?addorupdate&connectId=${connectId}" funname="add"/>
    <t:dgToolBar title="修改" icon="icon-edit" url="articleController.do?addorupdate" funname="update"/>
</t:datagrid>
</div>
</div>
