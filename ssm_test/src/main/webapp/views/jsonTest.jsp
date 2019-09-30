<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: SXC
  Date: 2019/8/22
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsonTest.jsp</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        //请求的是json 输出的是json
        function requestJson(){
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/requestJson',
                contentType:'application/json; charset=utf-8',
                dataType:'json',
                //数据格式是json
                data:'{"name":"手机","price":"999"}',
                success:function(data){
                    //反回json结果
                    alert(data);
                }
            });
        }
        //请求的是key/value 输出的是json
        function responseJson(){
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/responseJson',
                //contentType默认是key/value类型 所以不需要配置
                //数据格式是json
                data:'name=手机&price=999',
                success:function(data){
                    //反回json结果
                    alert(data);
                }
            });
        }

        function test(){
            var urlObject = window.URL || window.webkitURL || window;
            var export_blob = new Blob([123456778]);
            window.open(URL.createObjectURL(export_blob))
        }
    </script>
</head>
<body>

    <input type="button" onclick="requestJson()" value="请求的是json 输出的是json"/>

    <input type="button" onclick="responseJson()" value="请求的是key/value 输出的是json"/>

    <button onclick="test()">测试</button>
</body>
</html>
