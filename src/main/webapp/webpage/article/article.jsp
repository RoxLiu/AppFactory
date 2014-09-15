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
    <link rel="stylesheet" href="/plug-in/kindeditor/themes/default/default.css"/>
    <script src="/plug-in/kindeditor/kindeditor.js"></script>
    <script src="/plug-in/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" >
        KindEditor.ready(function(K) {
            var uploadbutton = K.uploadbutton({
                button : K('#uploadButton')[0],
                fieldName : 'imgFile',
                url : 'fileUploadController.do?saveFiles',
                afterUpload : function(data) {
                    if (data.error === 0) {
                        var url = K.formatUrl(data.url, 'absolute');
                        K('#url').val(url);
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

        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="htmlcontent"]', {
                allowPreviewEmoticons : false,
                allowImageUpload : true,
                allowFileManager: false,
                allowImageRemote: false,
                uploadJson: 'fileUploadController.do?saveFiles',
                fileManagerJson: 'fileUploadController.do?saveFiles',
                items : [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', '|', 'emoticons', 'image', 'link']
            });
        });

        function uploadHTML() {
            document.getElementById("content").value = editor.html();
        }
    </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="articleController.do?saveArticle" beforeSubmit="uploadHTML">
    <input id="id" name="id" type="hidden" value="${article.id}">
    <input id="connectId" name="connectId" type="hidden" value="${article.connectId}">
    <table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
            <%--
                    <tr>
                        <td align="right" width="15%" nowrap><label class="Validform_label"> 用户名: </label></td>
                        <td class="value" width="85%">
                            <c:if test="${shopInfo.id != 0 }">
                                ${shopInfo.accountName}
                            </c:if>
                            <c:if test="${shopInfo.id == 0 }">
                                <input id="accountName" class="inputxt" name="accountName" validType="user_account,user_account_name" value="${shopInfo.accountName }" datatype="s2-10"/>
                                <span class="Validform_checktip">用户名范围在2~10位字符</span>
                            </c:if>
                        </td>
                    </tr>
            --%>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 图标: </label></td>
            <td class="value">
                <input class="ke-input-text" type="text" id="url" name="icon" value="${article.icon}" readonly="readonly" /> <input type="button" id="uploadButton" value="Upload" />
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 标题: </label></td>
            <td class="value">
                <input class="inputxt" name="title" value="${article.title}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 描述: </label></td>
            <td class="value">
                <input class="inputxt" name="description" value="${article.description}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 内容: </label></td>
            <td class="value">
                <textarea id="htmltextarea" name="htmlcontent" style="width:100px;height:200px;visibility:hidden;">${article.content}</textarea>
                <input class="inputxt" id="content" name="content"  type="hidden" value="${article.content}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 关键字: </label></td>
            <td class="value">
                <input class="inputxt" name="keyWords" value="${article.keyWords}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 网址: </label></td>
            <td class="value">
                <input class="inputxt" name="webLink" value="${article.webLink}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>