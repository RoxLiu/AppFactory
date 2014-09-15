<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link type="text/css" rel="stylesheet" href="/css/style.css">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true">
    <fieldset class="step">
        <legend> 商家信息</legend>
        <div class="form"><label class="form"> 名称: </label> ${shopAccount.shopName}</div>
        <div class="form"><label class="form"> 帐号: </label> ${shopAccount.accountName}</div>
        <div class="form"><label class="form"> 联系电话: </label> ${shopAccount.phone}</div>
        <div class="form"><label class="form"> 邮箱: </label> ${shopAccount.email}</div>
        <div class="form"><label class="form"> 状态: </label> ${shopAccount.status}</div>
        <div class="form"><label class="form"> 创建时间: </label> ${shopAccount.createTime}</div>
        <div class="form"><label class="form"> 详细描述: </label> ${shopAccount.description}</div>
    </fieldset>

</t:formvalid>
</body>
