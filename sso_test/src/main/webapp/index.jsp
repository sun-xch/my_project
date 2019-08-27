<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>jsonTest.jsp</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function login() {
            var username = $("#username").val();
            var password = $("#password").val();
            var params = "username="+username+"&password="+password;
            $.ajax({
                'url':'${pageContext.request.contextPath}/login',
                'data':params,
                'success':function (data) {
                    if(data.code == 200){
                        var token = data.token;
                        //local storage - 本地存储数据 长期有效
                        //session storage - 会话存储的数据 一次会话有效
                        var localStorage = window.localStorage;
                        localStorage.token = token;
                        alert(data.msg);
                    }else{
                        alert(data.msg);
                    }
                }
            });
        }
        
        function setHeader(xhr) {
            // xht xml http request 异步请求对象
            xhr.setRequestHeader("Authorization",window.localStorage.token);
        }
        
        function testLocalStorage() {
            $.ajax({
                'url':'${pageContext.request.contextPath}/testAll',
                'success':function (data) {
                    if(data.code == 200){
                        window.localStorage.token = data.token;
                        alert(data.data);
                    }else{
                        alert(data.msg);
                    }
                },
                'beforeSend':setHeader
            });
        }
    </script>
</head>
<body>
<h2>Hello World!</h2>

    <table>
        <caption>登录测试</caption>
        <tr>
            <td style="text-align: right;padding-right: 5px">
                用户名：
            </td>
            <td style="text-align: right;padding-left: 5px">
                <input type="text" name="username" id="username"/>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;padding-right: 5px">
                密码：
            </td>
            <td style="text-align: right;padding-left: 5px">
                <input type="text" name="password" id="password"/>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;padding-right: 5px" colspan="2">
                <input type="button" value="登录" onclick="login();"/>
            </td>
        </tr>
    </table>
    <input type="button" value="testLocalStorage" onclick="testLocalStorage();">
</body>
</html>
