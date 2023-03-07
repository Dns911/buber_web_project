<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 19-Dec-22
  Time: 4:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<!DOCTYPE html>--%>
<html>
<head>
    <title>New client</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<style>
    body{
        background-image: url(${pageContext.request.contextPath}/resources/img/wallpaper_login_2.jpg);
        background-size: 100%;
        background-position: 0 100%;
    }
</style>
<header>
    <h1>
        Регистрация нового клиента
    </h1>
</header>
<form class="form_center" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add_user"/>
    <input type="hidden" name="user_role" value="client"/>
    <input class="input_registr" type="email" name="email" required placeholder="Ваш e-mail" value="${requestScope.email}"><span class="red_text"> ${requestScope.email_err}</span>
    <input class="input_registr" type="text" name="phone_numb" required placeholder="Ваш телефон (+375 00 000-00-00)" value="${requestScope.phone_numb}"><span class="red_text"> ${requestScope.phone_numb_err}</span>
    <input class="input_registr" type="text" name="user_name" required placeholder="Ваше имя" value="${requestScope.user_name}"><span class="red_text"> ${requestScope.user_name_err}</span>
    <input class="input_registr" type="text" name="user_lastname" placeholder="Ваша фамилия" value="${requestScope.user_lastname}"><span class="red_text"> ${requestScope.user_lastname_err}</span>
    <input class="input_registr" type="password" name="password" placeholder="Пароль" value=""/><span class="red_text"> ${requestScope.password_err}</span>
    <input class="input_registr" type="password" name="password_check" placeholder="Пароль еще раз" value=""/><span class="red_text"> ${requestScope.password_check_err}</span>
    <span class="red_text"> ${requestScope.registr_msg}</span>
    <input class="btn_dark_blue" type="submit" name="but" value="Регистрация"/>
</form>
<footer style="color: #f8f8f8;font-size: 15px; position: absolute; bottom: 5px" >
    <h6>
        Minsk, 2023
    </h6>
</footer>
</body>
</html>
