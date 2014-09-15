<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="shopList" title="" actionUrl="shopController.do?datagrid&shopId=${user.shopId}" fit="true" fitColumns="true" idField="id" onClick="showProductList">
	<t:dgCol title="编号" field="id" hidden="false"/>
	<t:dgCol title="商家ID" sortable="false" field="accountId" query="false"/>
	<t:dgCol title="联系电话" field="phone" query="false"/>
	<t:dgCol title="状态" sortable="false" field="status" replace="正常_1,禁用_0,删除_-1" />
	<t:dgCol title="描述" field="description" query="false"/>
	<t:dgCol title="商家图标" field="icon" query="false"/>
	<t:dgCol title="经营范围" field="product" query="false"/>
	<t:dgCol title="地址" field="address" query="false"/>
	<t:dgCol title="经度" field="lon" query="false"/>
	<t:dgCol title="纬度" field="lat" query="false"/>
	<t:dgCol title="相册图片" field="photoList" query="false"/>
	<t:dgCol title="创建时间" field="createTime" query="false"/>
	<t:dgCol title="网址" field="webLink" query="false"/>
	<t:dgCol title="最后更新时间" field="lastUpdate" query="false"/>
	<t:dgCol title="操作" field="opt"/>
	<t:dgDelOpt title="删除" url="shopController.do?del&id={id}&accountId={accountId}" />
    <t:dgToolBar title="添加" icon="icon-add" url="shopController.do?addorupdate" funname="add"/>
    <t:dgToolBar title="修改" icon="icon-edit" url="shopController.do?addorupdate" funname="update"/>
</t:datagrid>
</div>
</div>
<div region="east" style="width: 500px;" split="true">
<div class="easyui-panel" title="商品管理" style="padding: 0px;" fit="true" border="false" id="product-panel"></div>
</div>


<script type="text/javascript">

function showProductList(rowIndex,rowData) {
    //alert("rowData.accountId" + rowData.accountId);
	$("#product-panel").panel(
		{
			title :"商品管理 - [" + rowData.accountId + "]",
			href:"productController.do?productListOfShop&shopId=" + rowData.id
		}
	);
	$('#product-panel').panel("refresh");
//    addTab('商品管理2','productController.do?productListOfShop&shopId=1','pictures');
}
</script>