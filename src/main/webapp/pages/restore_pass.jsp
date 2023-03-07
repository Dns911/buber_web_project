<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 18-Feb-23
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restore password</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<form class="form_login" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="change_pass"/>
    <input type="hidden" name="password" value=""/>
    <input class="input_registr" type="text" name="login" value="" placeholder="Ваш email"><span class="red_text"> ${requestScope.login_err}</span>
    <div>
        <input type="radio" name="user_role" checked value="client"/>Пассажир
        <input type="radio" name="user_role" value="driver"/>Водитель
    </div>
    <button class="btn_dark_blue" type="submit">Сгенерировать новый пароль</button>
</form>
</body>
</html>
