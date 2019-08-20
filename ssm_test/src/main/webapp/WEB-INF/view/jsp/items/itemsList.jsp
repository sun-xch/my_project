<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/19
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>itemsList.jsp</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/items/queryItems.action" method="post">
        <table border="1" cellspacing="0" width="100%">
            <tr>
                <td>名称</td>
                <td>价格</td>
                <td>信息</td>
                <td>操作</td>
            </tr>

            <c:forEach items="${itemsList}" var="item">
                <tr>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.detail}</td>
                    <td><a href="${pageContext.request.contextPath}/items/queryItemsById?id=${item.id}">修改</a></td>
                </tr>
            </c:forEach>

        </table>
    </form>

</body>
</html>
