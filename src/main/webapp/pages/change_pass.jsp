
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Change password</title>
  <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<form class="form_login" action="controller" method="post">
  <input type="hidden" name="command" value="change_pass"/>
  <input class="input_registr" type="text" name="password" value="" placeholder="Новый пароль"><span class="red_text"/>
  <input class="input_registr" type="text" name="password_check" value="" placeholder="Введите еще раз"/>
  <span class="red_text"> ${requestScope.password_err}</span>
  <button class="btn_dark_blue" type="submit">Изменить пароль</button>
</form>
</body>
</html>

