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
    <title>Title</title>
</head>
<body>

<form class="form_login" action="controller" method="post">
    <input type="hidden" name="command" value="restore_pass"/>
    <input class="input" type="text" name="login" value="" placeholder="Ваш email"><span class="red_text"> ${requestScope.login_err}</span>
    <div>
        <input type="radio" name="user_role" checked value="client"/>Пассажир
        <input type="radio" name="user_role" value="driver"/>Водитель
    </div>
    <%--    <input class="button" type="submit" name="but2" value="Стать пользователем"/>--%>
    <button class="btn" type="submit">Сгенерировать новый пароль</button>
</form>


</body>
</html>
