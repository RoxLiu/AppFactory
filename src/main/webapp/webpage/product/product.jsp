<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/webpage/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>商品信息</title>
    <t:base type="jquery,easyui,tools"></t:base>
<%--
    <link rel="stylesheet" href="plug-in/kindeditor/themes/default/default.css" />
    <script charset="utf-8"  src="plug-in/kindeditor/kindeditor.js"/>
--%>


</head>
<body style="overflow-y: hidden">
<t:formvalid formid="formobj" dialog="true" layout="table" action="productController.do?saveProduct">
    <input id="id" name="id" type="hidden" value="${product.id}">
    <input id="connectId" name="connectId" type="hidden" value="${product.connectId}">
    <table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
            <%--
                    <tr>
                        <td align="right" width="15%" nowrap><label class="Validform_label"> 用户名: </label></td>
                        <td class="value" width="85%">
                            <c:if test="${product.id != 0 }">
                                ${product.accountName}
                            </c:if>
                            <c:if test="${product.id == 0 }">
                                <input id="accountName" class="inputxt" name="accountName" validType="user_account,user_account_name" value="${product.accountName }" datatype="s2-10"/>
                                <span class="Validform_checktip">用户名范围在2~10位字符</span>
                            </c:if>
                        </td>
                    </tr>
            --%>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 名称: </label></td>
            <td class="value">
                <input class="inputxt" name="name" value="${product.name}" datatype="s1-25">
                <span class="Validform_checktip">用户名范围在1~25位字符</span>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 品牌: </label></td>
            <td class="value">
                <input class="inputxt" name="brandId" value="${product.brandId}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 颜色: </label></td>
            <td class="value">
                <input class="inputxt" name="color" value="${product.color}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 尺寸: </label></td>
            <td class="value">
                <input class="inputxt" name="size" value="${product.size}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 简介: </label></td>
            <td class="value">
                <input class="inputxt" name="brief" value="${product.brief}">
                <%--<span class="Validform_checktip"/>--%>
            </td>
        </tr>
<%--
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 性别: </label></td>
            <td class="value">
                <input class="inputxt" name="sexual" value="${product.sexual}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
--%>
<%--        <tr>
            <td align="right" nowrap><label class="Validform_label"> 图片: </label></td>
            <td class="value">
                &lt;%&ndash;<input class="inputxt" name="picture" value="${product.picture}">&ndash;%&gt;
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 图标: </label></td>
            <td class="value">
                <input class="ke-input-text" type="text" id="url" name="icon" value="${product.icon}" readonly="readonly" /> <input type="button" id="uploadButton" value="Upload" />
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 原价: </label></td>
            <td class="value">
                <input class="inputxt" name="normalPrice" value="${product.normalPrice}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 现价: </label></td>
            <td class="value">
                <input class="inputxt" name="nowPrice" value="${product.nowPrice}">
                &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 详细描述: </label></td>
            <td class="value">
                <input class="inputxt" name="description" value="${product.description}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 库存: </label></td>
            <td class="value">
                <input class="inputxt" name="storeAmount" value="${product.storeAmount}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 副标题: </label></td>
            <td class="value">
                <input class="inputxt" name="secondTitle" value="${product.secondTitle}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 货号: </label></td>
            <td class="value">
                <input class="inputxt" name="sn" value="${product.sn}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 促售开始时间: </label></td>
            <td class="value">
                <input class="inputxt" name="startDiscountTime" value="${product.startDiscountTime}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 促售结束时间: </label></td>
            <td class="value">
                <input class="inputxt" name="endDiscountTime" value="${product.endDiscountTime}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 是否可预订: </label></td>
            <td class="value">
                <input class="inputxt" name="orderable" value="${product.orderable}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> 链接: </label></td>
            <td class="value">
                <input class="inputxt" name="webLink" value="${product.webLink}">
                    &lt;%&ndash;<span class="Validform_checktip"/>&ndash;%&gt;
            </td>
        </tr>--%>
    </table>
</t:formvalid>
</body>
</html>
