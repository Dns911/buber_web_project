<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 19-Dec-22
  Time: 4:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success registration!</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<body>
<style>
    body{
        background-image: url(${pageContext.request.contextPath}/resources/img/wallpaper_login.jpg);
        background-size: 100%;
        background-position: 0 100%;
    }
</style>
<header>
    <h1 class="h1_center">
        Registration was success!
    </h1>
</header>
<form class="form_center" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="default">
    <input class="btn_dark_blue" type="submit" name="but" value="Вернуться на главную"/>
</form>
</body>
</html>
