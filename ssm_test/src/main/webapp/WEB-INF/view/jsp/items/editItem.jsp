<%--
  Created by IntelliJ IDEA.
  User: SXC
  Date: 2019/8/20
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>editItem.jsp</title>
</head>
<body>
    <form id="itemForm" action="${pageContext.request.contextPath}/items/updateItemById" method="post">
        <input type="hidden" name="id" value="${item.id}">
        修改商品信息
        <table width="100%" border="1">
            <tr>
                <td>商品名称</td>
                <td><input type="text" name="name" value="${item.name}"></td>
            </tr>
            <tr>
                <td>商品价格</td>
                <td><input type="text" name="price" value="${item.price}"></td>
            </tr>
            <tr>
                <td>商品详情</td>
                <td><input type="text" name="detail" value="${item.detail}"></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="提交"/>
                </td>
            </tr>
        </table>
    </form>

</body>
</html>
