<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 16-Jan-23
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<header>
    <h1>
        Регистрация нового водителя
    </h1>
</header>
<form class="form" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add_user">
    <input class="input" type="email" name="email" required placeholder="Ваш e-mail">
    <input class="input" type="phone_numb" name="phone" required placeholder="Ваш телефон (+375 00 000-00-00)">
    <input class="input" type="text" name="user_name" required placeholder="Ваше имя">
    <input class="input" type="text" name="user_lastname" placeholder="Ваша фамилия">
    <input class="input" type="password" placeholder="Пароль">
    <input class="input" type="password" placeholder="Пароль еще раз">
    <input class="btn" type="submit" name="but" value="Registration"/>
    <br/>
    ${usertype}
    ${filter_attr}
</form>
<simpleType name="phone_numb">
    <restriction base="string">
        <pattern value="+375([0-9][0-9])[0-9][0-9][0-9][0-9][0-9][0-9][0-9]"/>
    </restriction>
</simpleType>
</body>
</html>
