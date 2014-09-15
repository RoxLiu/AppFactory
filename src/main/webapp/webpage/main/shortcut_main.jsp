<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>AppFactory管理系统</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"/>
<script type="text/javascript" src="plug-in/accordion/js/left_shortcut_menu.js"></script><style type="text/css">
a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
}
/*update-start--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色*/
.tree-node-selected{
    background: #eaf2ff;
}
/*update-end--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色*/
</style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<!-- 顶部-->
<div region="north" border="false" title="" style="BACKGROUND: #A8D7E9; height: 30px; padding: 1px; overflow: hidden;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" style="vertical-align: text-bottom">
        <!--
            <img src="plug-in/weixin/logo/logo_weixin2.png"/> 
		    <img src="plug-in/login/images/toplogo.png" width="550" height="52" alt="">
        -->
		</td>
		<td align="right" nowrap>
		<table border="0" cellpadding="0" cellspacing="0">
			<tr style="height: 25px;" align="right">
				<td style="" colspan="2">
				<div style="background: url('plug-in/login/images/top_bg.jpg') no-repeat right center; float: right;">

                <!-- 系统管理员 -->
                <c:if test="${user.type == 0}">
                    <div style="float: left; line-height: 25px; margin-left: 70px;"><span style="color: #386780">当前用户:</span> <span style="color: #FFFFFF">${user.accountName}</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
                            style="color: #386780">角色:</span> <span style="color: #FFFFFF">系统管理员</span>
                    </div>
                </c:if>
                <!-- APP管理员 -->
                <c:if test="${user.type == 1}">
                    <div style="float: left; line-height: 25px; margin-left: 70px;"><span style="color: #386780">当前用户:</span> <span style="color: #FFFFFF">${user.accountName}</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
                            style="color: #386780">角色:</span> <span style="color: #FFFFFF">APP管理员</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
                            style="color: #386780">商家:</span> <span style="color: #FFFFFF">${shopName}</span>
                    </div>
                </c:if>
				<div style="float: left; margin-left: 18px;">
				<div style="right: 0px; bottom: 0px;">
                    <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-comturn" style="color: #FFFFFF">控制面板</a>&nbsp;&nbsp;
                    <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu" iconCls="icon-exit" style="color: #FFFFFF">注销</a></div>
				<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
					<%--<div onclick="openwindow('用户信息','userController.do?userinfo')">个人信息</div>--%>
					<div class="menu-sep"></div>
					<div onclick="add('修改密码','userController.do?changepassword')">修改密码</div>
                    <!--
					<div class="menu-sep"></div>	
					<div onclick="add('修改首页风格','userController.do?changestyle')">首页风格</div>
                    -->
				</div>
				<div id="layout_north_zxMenu" style="width: 100px; display: none;">
					<div onclick="exit('loginController.do?logout','确定退出该系统吗 ?',1);">退出系统</div>
				</div>
				</div>
                <!--
                <div style="float: left; margin-left: 8px;margin-right: 5px; margin-top: 5px;">
                    <img src="plug-in/easyui/themes/default/images/layout_button_up.gif" style="cursor:pointer" onclick="panelCollapase()" />
                </div>
                -->
                </div>
                </td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- 左侧-->
<div region="west" split="true" title="系统功能" style="width: 200px; padding: 1px;">
    <div id="nav" style="display:block;" class="easyui-accordion" fit="true" border="false">
        <ul class="easyui-tree tree-lines"  fit="false" border="false">
            <c:forEach var="fuction" items="${menuList}">
                <li iconCls="pictures"> <a onclick="addTab('${fuction.functionName}','${fuction.functionUrl}','pictures')"  title='${fuction.functionName}' url='${fuction.functionUrl}' href="#" ><span class="nav" >${fuction.functionName}</span></a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<!--
<div region="west" split="true" title="系统功能" style="width: 200px; padding: 1px;">
<div id="nav" style="display:block;" class="easyui-accordion" fit="true" border="false">
<ul class="easyui-tree tree-lines"  fit="false" border="false">
<li iconCls="pictures"> <a onclick="addTab('账号管理','userController.do?user','pictures')"  title="账号管理" url="userController.do?user" href="#" ><span class="nav" >账号管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('商家管理','shopController.do?shopList','pictures')"  title="商家管理" url="shopController.do?shopList" href="#" ><span class="nav" >商家管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('订单管理','iconController.do?icon','pictures')"  title="订单管理" url="iconController.do?icon" href="#" ><span class="nav" >订单管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('用户管理','userController.do?user','pictures')"  title="用户管理" url="userController.do?user" href="#" ><span class="nav" >用户管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('角色管理','roleController.do?role','pictures')"  title="角色管理" url="roleController.do?role" href="#" ><span class="nav" >角色管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('数据字典','systemController.do?typeGroupList','pictures')"  title="数据字典" url="systemController.do?typeGroupList" href="#" ><span class="nav" >数据字典</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('菜单管理','functionController.do?function','pictures')"  title="菜单管理" url="functionController.do?function" href="#" ><span class="nav" >菜单管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('图标管理','iconController.do?icon','pictures')"  title="图标管理" url="iconController.do?icon" href="#" ><span class="nav" >图标管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('商品管理','productionController.do?productionList','pictures')"  title="商品管理" url="productionController.do?productionList" href="#" ><span class="nav" >商品管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('模块管理','moduleController.do?shopModuleList','pictures')"  title="模块管理" url="moduleController.do?shopModuleList" href="#" ><span class="nav" >模块管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('文章管理','iconController.do?icon&clickFunctionId=4028d881436d514601436d5215220021','pictures')"  title="文章管理" url="iconController.do?icon" href="#" ><span class="nav" >文章管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('用户管理','userController.do?user&clickFunctionId=4028d881436d514601436d521513001d','pictures')"  title="用户管理" url="userController.do?user" href="#" ><span class="nav" >用户管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('角色管理','roleController.do?role&clickFunctionId=4028d881436d514601436d521516001e','pictures')"  title="角色管理" url="roleController.do?role" href="#" ><span class="nav" >角色管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('数据字典','systemController.do?typeGroupList&clickFunctionId=4028d881436d514601436d52151f0020','pictures')"  title="数据字典" url="systemController.do?typeGroupList" href="#" ><span class="nav" >数据字典</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('菜单管理','functionController.do?function&clickFunctionId=4028d881436d514601436d52151a001f','pictures')"  title="菜单管理" url="functionController.do?function" href="#" ><span class="nav" >菜单管理</span></a></li>
<li iconCls="pictures"> <a onclick="addTab('图标管理','iconController.do?icon&clickFunctionId=4028d881436d514601436d5215220021','pictures')"  title="图标管理" url="iconController.do?icon" href="#" ><span class="nav" >图标管理</span></a></li>
</ul>
</div>
</div>
-->
<!-- 中间-->
<div id="mainPanle" region="center" style="overflow: hidden;">
<div id="maintabs" class="easyui-tabs" fit="true" border="false">
    <c:if test="user.type == 1">
        <div class="easyui-tab" title="首页" href="loginController.do?home" style="padding: 2px; overflow: hidden;"/>
    </c:if>
</div>
</div>
<!-- 底部 -->
<div region="south" border="false" style="height: 25px; overflow: hidden;">
<div align="center" style="color: #CC99FF; padding-top: 2px"><span>&copy; 版权所有(推荐谷歌浏览器，获得更快响应速度)</span></div>
</div>
<div id="mm" class="easyui-menu" style="width: 150px;">
<div id="mm-tabupdate">刷新</div>
<div id="mm-tabclose">关闭</div>
<div id="mm-tabcloseall">全部关闭</div>
<div id="mm-tabcloseother">除此之外全部关闭</div>
<div class="menu-sep"></div>
<div id="mm-tabcloseright">当前页右侧全部关闭</div>
<div id="mm-tabcloseleft">当前页左侧全部关闭</div>

</div>
</body>
</html>