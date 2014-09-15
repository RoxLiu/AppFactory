<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/webpage/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>商家信息</title>
    <link rel="stylesheet" href="/plug-in/kindeditor/themes/default/default.css"/>
    <script src="/plug-in/kindeditor/kindeditor.js"></script>
    <script src="/plug-in/kindeditor/lang/zh_CN.js"></script>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
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
    </script>

</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="personController.do?savePerson">
    <input id="id" name="id" type="hidden" value="${person.id}">
    <input id="connectId" name="connectId" type="hidden" value="${person.connectId}">
    <table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
            <%--
                    <tr>
                        <td align="right" width="15%" nowrap><label class="Validform_label"> 用户名: </label></td>
                        <td class="value" width="85%">
                            <c:if test="${person.id != 0 }">
                                ${person.accountName}
                            </c:if>
                            <c:if test="${person.id == 0 }">
                                <input id="accountName" class="inputxt" name="accountName" validType="user_account,user_account_name" value="${person.accountName }" datatype="s2-10"/>
                                <span class="Validform_checktip">用户名范围在2~10位字符</span>
                            </c:if>
                        </td>
                    </tr>
            --%>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 名字: </label></td>
            <td class="value">
                <input class="inputxt" name="name" value="${person.name}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 公司: </label></td>
            <td class="value">
                <input class="inputxt" name="company" value="${person.company}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 学校: </label></td>
            <td class="value">
                <input class="inputxt" name="secondTitle" value="${person.secondTitle}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 简介: </label></td>
            <td class="value">
                <input class="inputxt" name="brief" value="${person.brief}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 电话: </label></td>
            <td class="value">
                <input class="inputxt" name="phone" value="${person.phone}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 描述: </label></td>
            <td class="value">
                <input class="inputxt" name="description" value="${person.description}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 图标: </label></td>
            <td class="value">
                <%--<input class="inputxt" name="icon" value="${person.icon}">--%>
                <input class="ke-input-text" type="text" id="url" name="icon" value="${person.icon}" readonly="readonly" /> <input type="button" id="uploadButton" value="Upload" />
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 地址: </label></td>
            <td class="value">
                <input class="inputxt" name="address" value="${person.address}">
                    <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
<%--        <tr>
            <td align="right" nowrap><label class="Validform_label"> 经营范围: </label></td>
            <td class="value">
                <input class="inputxt" name="product" value="${person.product}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 经度: </label></td>
            <td class="value">
                <input class="inputxt" name="lon" value="${person.lon}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 纬度: </label></td>
            <td class="value">
                <input class="inputxt" name="lat" value="${person.lat}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 网址: </label></td>
            <td class="value">
                <input class="inputxt" name="webLink" value="${person.webLink}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 商家图片: </label></td>
            <td class="value">
                <input class="inputxt" name="photoList" value="${person.photoList}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>--%>
    </table>
</t:formvalid>
</body>