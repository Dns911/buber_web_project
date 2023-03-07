<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 24-Jan-23
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Client page</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<form class="form_info" action="${pageContext.request.contextPath}/controller" method="post">
    <div class="bold_blue_text">
        Страница клиента ${sessionScope.user_login} !
    </div>
    <input type="hidden" name="command" value="order_info"/>
    <input class="btn_dark_blue" type="submit" name="button_main" value="Текущий заказ"/>
    <div class="bold_red_text">${requestScope.current_order_msg}</div>
</form>
<form class="form_info" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="user_info"/>
    <input class="btn_dark_blue" type="submit" name="button_inf" value="Информация о пользователе"/>
</form>
<table class="table">
    <tr>
        <th>
            Номер телефона
        </th>
        <th>
            Имя
        </th>
        <th>
            Фамилия
        </th>
        <th>
            Сумма поездок
        </th>
        <th>
            Дата регистрации
        </th>
        <th>
            Средняя оценка
        </th>
    </tr>
    <tr>
        <td>
            ${requestScope.phone_numb}
        </td>
        <td>
            ${requestScope.user_name}
        </td>
        <td>
            ${requestScope.user_lastname}
        </td>
        <td>
            ${requestScope.payment_sum}
        </td>
        <td>
            ${requestScope.date_registry}
        </td>
        <td>
            ${requestScope.rate}
        </td>
    </tr>
</table>
<form class="form_info" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="goto_main"/>
    <input class="btn_red" type="submit" name="button_main" value="Вернуться на главную"/>
</form>
<form class="form_info" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="change_page"/>
    <input class="btn_red" type="submit" name="button_main" value="Смена пароля"/>
</form>
<form class="form_info" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="logout"/>
    <input class="btn_red" type="submit" name="button_logout" value="Выйти из системы"/>
</form>
<footer style="color: #f8f8f8;font-size: 15px; position: absolute; bottom: 5px" >
    <h6>
        Minsk, 2023
    </h6>
</footer>
</body>
</html>
