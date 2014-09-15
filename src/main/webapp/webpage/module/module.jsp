<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <c:if test="${module.id == 0 }">
        <title>添加</title>
    </c:if>
    <c:if test="${module.id != 0 }">
        <title>修改</title>
    </c:if>
    <t:base type="jquery,easyui,tools"/>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="moduleController.do?saveModule">
    <input id="id" name="id" type="hidden" value="${module.id}">
    <table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right" width="15%" nowrap><label class="Validform_label"> 模块名称: </label></td>
            <td class="value" width="85%">
                <input id="accountName" class="inputxt" name="name" value="${module.name }" datatype="s2-10"/>
                <span class="Validform_checktip">模块名称长度范围在2~10位字符</span>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 详细描述: </label></td>
            <td class="value">
                <input class="inputxt" name="description" value="${module.description}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>