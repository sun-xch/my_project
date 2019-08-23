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
    <script type="text/javascript">
        function queryItems(){
            document.itemsForm.action = "${pageContext.request.contextPath}/items/queryItems"
            document.itemsForm.submit();
        }
        function deleteItems(){
            document.itemsForm.action = "${pageContext.request.contextPath}/items/deleteItems"
            document.itemsForm.submit();
        }
        function editItemsQuery(){
            document.itemsForm.action = "${pageContext.request.contextPath}/items/editItemsQuery"
            document.itemsForm.submit();
        }
    </script>
</head>
<body>

    当前用户：${username},
    <c:if test="${username != null}">
        <a href="${pageContext.request.contextPath}/logout">退出</a>
    </c:if>

    <form name="itemsForm" action="${pageContext.request.contextPath}/items/queryItems" method="post">
        查询条件
        <table width="100%" border="1">
            <tr>
                <td>
                    商品名称：<input name="name"/>
                    商品类型：
                    <select name="itemType">
                        <c:forEach items="${itemTypes}" var="itemType">
                            <option value="${itemType.key}">${itemType.value}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <input type="button" value="查询" onclick="queryItems()"/>
                    <input type="button" value="批量删除" onclick="deleteItems()"/>
                    <input type="button" value="批量修改" onclick="editItemsQuery()"/>
                </td>
            </tr>
        </table>
        商品列表
        <table border="1" cellspacing="0" width="100%">
            <tr>
                <td>选择</td>
                <td>名称</td>
                <td>价格</td>
                <td>信息</td>
                <td>操作</td>
            </tr>

            <c:forEach items="${itemsList}" var="item">
                <tr>
                    <td><input type="checkbox" name="items_id" value="${item.id}"/></td>
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
