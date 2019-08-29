<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: SXC
  Date: 2019/8/23
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login.jsp</title>
</head>
<body>
    <p>ssm_test 自己的登录</p>
    <form action="${pageContext.request.contextPath}/login" method="post">
        用户账号：<input type="text" name="username"/><br/>
        用户密码：<input type="password" name="password"/><br/>
        <input type="submit" value="登录"/>
    </form>
</body>
</html>
