<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="shopList" title="" actionUrl="shopController.do?datagrid&connectId=${connectId}" fit="true" fitColumns="true" idField="id" onClick="showProductList">
	<t:dgCol title="编号" field="id" hidden="false"/>
	<t:dgCol title="名字" sortable="false" field="name" query="false"/>
    <t:dgCol title="简介" field="brief" query="false"/>
    <t:dgCol title="电话号码" field="phone" query="false"/>
	<t:dgCol title="描述" field="description" query="false"/>
	<t:dgCol title="图标" field="icon" image="true" imageSize="24,24" query="false"/>
    <t:dgCol title="地址" field="address" query="false"/>
	<t:dgCol title="最后更新时间" field="lastUpdate" query="false"/>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="shopController.do?del&id={id}" />
    <t:dgToolBar title="添加" icon="icon-add" url="shopController.do?addorupdate&connectId=${connectId}" funname="add"/>
    <t:dgToolBar title="修改" icon="icon-edit" url="shopController.do?addorupdate" funname="update"/>
</t:datagrid>
</div>
</div>
<div region="east" style="width: 500px;" split="true">
<div class="easyui-panel" title="产品管理" style="padding: 0px;" fit="true" border="false" id="product-panel"></div>
</div>

<%--
<t:dgCol title="经营范围" field="product" query="false"/>
<t:dgCol title="经度" field="lon" query="false"/>
<t:dgCol title="纬度" field="lat" query="false"/>
<t:dgCol title="相册图片" field="photoList" query="false"/>
<t:dgCol title="创建时间" field="createTime" query="false"/>
<t:dgCol title="网址" field="webLink" query="false"/>
--%>

<script type="text/javascript">

function showProductList(rowIndex,rowData) {
    //alert("rowData.accountId" + rowData.accountId);
	$("#product-panel").panel(
		{
			title :"产品管理 - [" + rowData.name + "]",
			href:"productController.do?productListOfShop&connectId=" + rowData.id
		}
	);
	$('#product-panel').panel("refresh");
}
</script>