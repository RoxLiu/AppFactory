<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/webpage/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="orderList" title="" actionUrl="orderController.do?datagrid&shopId=${user.shopId}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="编号" field="id" hidden="false"/>
	<t:dgCol title="商家ID" sortable="false" field="shopId" query="false"/>
	<t:dgCol title="商品" field="productionId" query="false"/>
	<t:dgCol title="类型" field="type" replace="订单_1,预约_2,收藏_4" />
	<t:dgCol title="订单用户" field="userId" query="false"/>
	<t:dgCol title="状态" field="status" replace="正常_1,删除_2,完成_3" query="false"/>
	<t:dgCol title="数量" field="productionAmount" query="false"/>
	<t:dgCol title="下单时间" field="orderTime" query="false"/>
	<t:dgCol title="完成时间" field="completeTime" query="false"/>
	<t:dgCol title="最后更新时间" field="lastUpdateTime" query="false"/>
	<t:dgCol title="单价" field="priceEach" query="false"/>
	<t:dgCol title="总价" field="totalPrice" query="false"/>
	<t:dgCol title="折扣" field="discount" query="false"/>
    <t:dgCol title="备注" field="note" query="false"/>
</t:datagrid>
</div>
</div>