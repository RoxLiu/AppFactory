<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="appUserList" title="用户管理" actionUrl="userController.do?datagrid" fit="true" fitColumns="true" idField="id" onClick="showModuleList">
    <t:dgCol title="编号" field="id" hidden="false"/>
    <t:dgCol title="用户名" sortable="false" field="accountName" query="true"/>
    <t:dgCol title="商铺ID" field="shopId" query="false"/>
    <t:dgCol title="电话" field="phone" query="true"/>
    <t:dgCol title="EMAIL" field="email" query="true"/>
    <t:dgCol title="推荐码" field="sendCode" query="false"/>
    <t:dgCol title="状态" sortable="false" field="status" replace="正常_1,锁定_-2,删除_-1" />
    <t:dgCol title="类型" field="type" replace="超级管理员_0,管理用户_1,普通用户_2"/>
    <t:dgCol title="创建时间" field="createTime" query="false"/>
    <t:dgCol title="是否在线" field="online" replace="否_0,是_1"/>
    <t:dgCol title="操作" field="opt"/>
    <t:dgDelOpt title="删除" url="userController.do?del&id={id}&userName={userName}" />
    <t:dgToolBar title="添加用户" icon="icon-add" url="userController.do?addorupdate" funname="add"/>
    <t:dgToolBar title="修改用户" icon="icon-edit" url="userController.do?addorupdate" funname="update"/>
    <t:dgToolBar title="重置密码" icon="icon-edit" url="userController.do?changepasswordforuser" funname="update"/>
    <t:dgToolBar title="锁定账户" icon="icon-edit" url="userController.do?lock" funname="lockObj"/>
    <t:dgToolBar title="解锁账户" icon="icon-edit" url="userController.do?unlock" funname="unlockObj"/>
</t:datagrid>
</div>
</div>
<div region="east" style="width: 500px;" split="true">
<div class="easyui-panel" title="模块管理" style="padding: 0px;" fit="true" border="false" id="module-panel"/>
</div>


<script type="text/javascript">
function showModuleList(rowIndex,rowData) {
	$("#module-panel").panel(
		{
			title :"模块管理 - [" + rowData.shopId + "]",
			href:"shopController.do?shopModuleList&shopId=" + rowData.shopId
		}
	);
	$('#module-panel').panel("refresh");
}

function lockObj(title,url, id) {
    gridname=id;
    var rowsData = $('#'+id).datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择要锁定的用户.');
        return;
    }
    url += '&id='+rowsData[0].id;

    $.dialog.confirm('请确认是否要锁定该用户[' + rowsData[0].accountName + "]？", function(){
        lockuploadify(url, '&id');
    }, function(){
    });
}

function unlockObj(title,url, id) {
    gridname=id;
    var rowsData = $('#'+id).datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择要解锁的用户.');
        return;
    }
    url += '&id='+rowsData[0].id;

    $.dialog.confirm('请确认是否要解锁该用户[' + rowsData[0].accountName + "]？", function(){
        lockuploadify(url, '&id');
    }, function(){
    });
}

function lockuploadify(url, id) {
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            var d = $.parseJSON(data);
            if (d.success) {
                var msg = d.msg;
                tip(msg);
                reloadTable();
            }

        }
    });
}
</script>