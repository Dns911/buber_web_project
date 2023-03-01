<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 24-Jan-23
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver page</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>

<div>
    ${pageContext.session.id}
</div>
<div>
    Статус водителя в системе: ${sessionScope.driver_system_status}
</div>
<div>
    "ожидание подтверждения, бан, активен"
</div>
<div>
    Рабочий статус: ${sessionScope.driver_work_status}
</div>
"ожидание заказа, работа с заказом, не на смене"
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="driver_shift_start">
    <input type="text" name="car_id" value="" placeholder="Номер авто">
    <input type="submit" name="иге" value="Выйти на смену">
    <div>${requestScope.car_id_err}</div>
    <div> ${requestScope.status_err}</div>
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="driver_shift_end">
    <input type="submit" name="иге" value="Завершить смену">
</form>
<div>${requestScope.driver_out}</div>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="goto_main"/>
    <input type="submit" name="button_main" value="Вернуться на главную"/>
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="order_info"/>
    <input type="submit" name="button_main" value="Текущий заказ"/>
    <div>${requestScope.current_order_msg}</div>
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="user_info"/>
    <input type="submit" name="button_inf" value="Информация о пользователе"/>
</form>
<table class="table">
    <tr>
        <th>
            Персональный номер
        </th>
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
            ${requestScope.id}
        </td>
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
            ${requestScope.income_sum}
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
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" name="button_logout" value="Выйти из системы"/>
</form>

</body>
</html>
