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
    <title>Order info driver</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="order_finish">

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
                Клиент
            </th>
            <th>
                Номер тефона
            </th>
            <th>
                Стоимость
            </th>
            <th>
                Статус заказа
            </th>
            <th>
                Оценка пассажиру
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
                ${requestScope.client_name}
            </td>
            <td>
                ${requestScope.phone_numb}
            </td>
            <td>
                ${requestScope.cost}
            </td>
            <td>
                ${requestScope.status}
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
