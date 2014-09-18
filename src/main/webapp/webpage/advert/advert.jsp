<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/webpage/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <c:if test="${article.id == 0 }">
        <title>添加</title>
    </c:if>
    <c:if test="${article.id != 0 }">
        <title>修改</title>
    </c:if>
    <t:base type="jquery,easyui,tools"></t:base>
    <link rel="stylesheet" href="plug-in/kindeditor/themes/default/default.css"/>
    <script src="plug-in/kindeditor/kindeditor.js"></script>
    <script src="plug-in/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" >
        KindEditor.ready(function(K) {
            var uploadbutton = K.uploadbutton({
                button : K('#uploadButton')[0],
                fieldName : 'imgFile',
                url : 'fileUploadController.do?saveFiles',
                afterUpload : function(data) {
                    if (data.error === 0) {
                        //var url = K.formatUrl(data.url, 'absolute');
                        K('#url').val(data.url);
                    } else {
                        alert(data.message);
                    }
                },
                afterError : function(str) {
                    alert('错误信息: ' + str);
                }
            });
            uploadbutton.fileBox.change(function(e) {
                uploadbutton.submit();
            });
        });
    </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="advertController.do?saveAdvert">
    <input id="id" name="id" type="hidden" value="${advert.id}">
    <table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 广告名: </label></td>
            <td class="value">
                <input class="inputxt" name="name" value="${advert.name}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 图标: </label></td>
            <td class="value">
                <input class="ke-input-text" type="text" id="url" name="icon" value="${advert.icon}" readonly="readonly" /> <input type="button" id="uploadButton" value="Upload" />
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> URL: </label></td>
            <td class="value">
                <input class="inputxt" name="webLink" value="${advert.webLink}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>