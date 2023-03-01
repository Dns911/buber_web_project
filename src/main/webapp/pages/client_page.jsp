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
Страница клиента ${sessionScope.user_login} !
<br/>


<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" name="button_logout" value="Выйти из системы"/>
    <br/>
    ${filter_attr}
    <br/>
    ${pageContext.session.id}
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="goto_main"/>
    <input type="submit" name="button_main" value="Вернуться на главную"/>
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="user_info"/>
    <input type="submit" name="button_inf" value="Информация о пользователе"/>
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
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="order_info"/>
    <input type="submit" name="button_main" value="Текущий заказ"/>
    <div>${requestScope.current_order_msg}</div>
</form>
</body>
</html>
