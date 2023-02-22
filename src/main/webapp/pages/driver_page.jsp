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
    <div> ${requestScope.status_err}</div>
</form>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="driver_shift_start">
    <input type="submit" name="иге" value="Завершить смену на смену">
</form>
</body>
</html>
