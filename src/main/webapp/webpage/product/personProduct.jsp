<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/webpage/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>商品信息</title>
    <t:base type="jquery,easyui,tools"/>
    <link rel="stylesheet" href="plug-in/kindeditor/themes/default/default.css"/>
    <script src="plug-in/kindeditor/kindeditor.js"></script>
    <script src="plug-in/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" >
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="htmlcontent"]', {
                width : '300px',
                allowPreviewEmoticons : false,
                allowImageUpload : true,
                allowFileManager: false,
                allowImageRemote: false,
                uploadJson: 'fileUploadController.do?saveFiles',
                fileManagerJson: 'fileUploadController.do?saveFiles',
                afterUploadafterUpload : function(url) {
                    alert(url);
                },
                items : [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', '|', 'emoticons', 'image', 'link']
            });
        });

        function uploadHTML() {
            document.getElementById("webLink").value = editor.html();
        }
    </script>

</head>
<body style="overflow-y: hidden">
<t:formvalid formid="formobj" dialog="true" layout="table" action="productController.do?savePersonProduct"  beforeSubmit="uploadHTML">
    <input id="id" name="id" type="hidden" value="${product.id}">
    <input id="connectId" name="connectId" type="hidden" value="${product.connectId}">
    <input id="webLink" name="webLink"  type="hidden"/>
    <table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 商品名称: </label></td>
            <td class="value">
                <input class="inputxt" name="name" value="${product.name}" datatype="s1-25">
                <span class="Validform_checktip">用户名范围在1~25位字符</span>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 简介: </label></td>
            <td class="value">
                <input class="inputxt" name="brief" value="${product.brief}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 链接: </label></td>
            <td class="value">
                <textarea id="htmltextarea" name="htmlcontent" style="width:100px;height:200px;visibility:hidden;">${product.webLink}</textarea>
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>

    </table>
</t:formvalid>
</body>
</html>
