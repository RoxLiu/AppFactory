<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>商家信息</title>
    <link rel="stylesheet" href="/plug-in/kindeditor/themes/default/default.css"/>
    <script src="/plug-in/kindeditor/kindeditor.js"></script>
    <script src="/plug-in/kindeditor/lang/zh_CN.js"></script>

    <script charset="utf-8" >
        KindEditor.ready(function(K) {
            var uploadbutton = K.uploadbutton({
                button : K('#uploadButton')[0],
                fieldName : 'imgFile',
                url : 'productController.do?saveProduct',
                afterUpload : function(data) {
                    if (data.error === 0) {
                        var url = K.formatUrl(data.url, 'absolute');
                        K('#url').val(url);
                    } else {
                        alert(data.message);
                    }
                },
                afterError : function(str) {
                    alert('自定义错误信息: ' + str);
                }
            });

            uploadbutton.fileBox.change(function(e) {
                uploadbutton.submit();
            });
        });
    </script>

</head>
<body style="overflow-y: hidden" scroll="no">
<%--
<div class="upload">
</div>
--%>
<t:formvalid formid="formobj" dialog="true" layout="table" action="shopController.do?saveShopInfo">
    <input id="id" name="id" type="hidden" value="${shopInfo.id}">
    <table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
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
            <td align="right" nowrap><label class="Validform_label"> 手机号码: </label></td>
            <td class="value">
                <input class="inputxt" name="phone" value="${shopInfo.phone}" datatype="m" errormsg="手机号码格式不正确!" ignore="ignore">
                <span class="Validform_checktip"/>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 描述: </label></td>
            <td class="value">
                <input class="inputxt" name="description" value="${shopInfo.description}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 图标: </label></td>
            <td class="value">
                <%--<input class="inputxt" name="icon" value="${shopInfo.icon}">--%>
                <input class="ke-input-text" type="text" id="url" value="${shopInfo.icon}" readonly="readonly" /> <input type="button" id="uploadButton" value="Upload" />
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 经营范围: </label></td>
            <td class="value">
                <input class="inputxt" name="product" value="${shopInfo.product}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 地址: </label></td>
            <td class="value">
                <input class="inputxt" name="address" value="${shopInfo.address}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 经度: </label></td>
            <td class="value">
                <input class="inputxt" name="lon" value="${shopInfo.lon}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 纬度: </label></td>
            <td class="value">
                <input class="inputxt" name="lat" value="${shopInfo.lat}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 网址: </label></td>
            <td class="value">
                <input class="inputxt" name="webLink" value="${shopInfo.webLink}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 商家图片: </label></td>
            <td class="value">
                <input class="inputxt" name="photoList" value="${shopInfo.photoList}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>