<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>统一认证中心</title>
</head>
<body>
    <p>统一认证中心 单点登录</p>
    <form id="slick-login" method="post" action="login">
        <input type="hidden" name="redirectUrl" value="${redirectUrl}">
        <label>用户名：</label><input type="text" name="username" placeholder="账号"/><br/>
        <label>密码：</label><input type="password" name="password" placeholder="密码"/><br/>
        <input type="submit" value="登录"/>
    </form>
</body>
</html>
