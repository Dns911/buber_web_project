<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 23-Feb-23
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order info client</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="order_finish">
    <input type="hidden" name="id" value="${requestScope.id}">
    <input type="hidden" name="driver_id" value="${requestScope.driver_id}">
<table>
    <tr>
        <th>
            Номер заказа
        </th>
        <th>
            Дата, время
        </th>
        <th>
            Пункт назначения
        </th>
        <th>
            Водитель
        </th>
        <th>
            Авто
        </th>
        <th>
            Стоимость
        </th>
        <th>
            Оценка водителю
        </th>
        <th>
            Статус заказа
        </th>
    </tr>
    <tr>
        <td>
            ${requestScope.id}
        </td>
        <td>
            ${requestScope.date} ${requestScope.start_time}
        </td>
        <td>
            ${requestScope.finish_point}
        </td>
        <td>
            ${requestScope.driver_name}
        </td>
        <td>
            ${requestScope.car_info}
        </td>
        <td>
            ${requestScope.cost}
        </td>
        <td>
            <select name="rate">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option selected value="5">5</option>
            </select>
        </td>
        <td>
            ${requestScope.status}
        </td>
    </tr>
</table>
<input type="submit" name="but_fin" value="Завершить заказ">
</form>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="goto_main"/>
    <input type="submit" name="button_main" value="Вернуться на главную"/>
</form>
</body>
</html>
