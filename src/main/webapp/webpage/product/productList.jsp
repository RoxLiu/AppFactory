<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="productList" title="商品管理" actionUrl="productionController.do?datagrid&shopId=${user.shopId}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
	<t:dgCol title="商品名称" sortable="false" field="name" query="false"></t:dgCol>
	<t:dgCol title="品牌" field="brandId" query="false"></t:dgCol>
	<t:dgCol title="颜色" field="color" query="false"></t:dgCol>
	<t:dgCol title="尺寸" sortable="size" field="status"></t:dgCol>
	<t:dgCol title="简介" field="brief" query="false"></t:dgCol>
    <t:dgCol title="价格" field="nowPrice" query="false"></t:dgCol>

    <!--
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgDelOpt title="删除" url="moduleController.do?del&id={id}&userName={userName}" />
    -->
    <!--
	<t:dgToolBar title="修改" icon="icon-edit" url="moduleController.do?addorupdate" funname="update"></t:dgToolBar>
    -->
</t:datagrid>
