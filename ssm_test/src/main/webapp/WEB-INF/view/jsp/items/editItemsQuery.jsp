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
        function editItemsAllSubmit(){
            document.itemsForm.action = "${pageContext.request.contextPath}/items/editItemsAllSubmit"
            document.itemsForm.submit();
        }
        function queryItems(){
            document.itemsForm.action = "${pageContext.request.contextPath}/items/queryItems"
            document.itemsForm.submit();
        }
    </script>
</head>
<body>

    <form name="itemsForm" action="${pageContext.request.contextPath}/items/queryItems" method="post">
        查询条件
        <table width="100%" border="1">
            <tr>
                <td>商品名称：<input name="name"/></td>
                <td>
                    <input type="button" value="查询" onclick="queryItems()"/>
                    <input type="button" value="批量修改提交" onclick="editItemsAllSubmit()"/>
                </td>
            </tr>
        </table>
        商品列表
        <table border="1" cellspacing="0" width="100%">
            <tr>
                <td>名称</td>
                <td>价格</td>
                <td>信息</td>
            </tr>

            <c:forEach items="${itemsList}" var="item" varStatus="index">
                <tr>
                    <td><input name="itemsList[${index.index}].name" value="${item.name}"/></td>
                    <td><input name="itemsList[${index.index}].price" value="${item.price}"/></td>
                    <td><input name="itemsList[${index.index}].detail" value="${item.detail}"/></td>
                </tr>
            </c:forEach>

        </table>
    </form>

</body>
</html>
