<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 19-Dec-22
  Time: 4:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>Registration</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<header>
    <h1>
        Регистрация нового пользователя
    </h1>
</header>
<form class="form" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add_user">
    <input class="input" type="email" name="email" required placeholder="Ваш e-mail" value="">
    <input class="input" type="text" name="phone_numb" required placeholder="Ваш телефон (+375 00 000-00-00)" value="">
    <input class="input" type="text" name="user_name" required placeholder="Ваше имя" value="">
    <input class="input" type="text" name="user_lastname" placeholder="Ваша фамилия" value="">
    <input class="input" type="password" name="password" placeholder="Пароль" value="">
    <input class="input" type="password" placeholder="Пароль еще раз">
    <input class="btn" type="submit" name="but" value="Registration"/>
    <br/>
    ${sessionScope.registr_msg}
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
