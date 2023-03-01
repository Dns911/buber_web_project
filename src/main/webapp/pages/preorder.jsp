<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 31-Jan-23
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Оформление заказа</title>
</head>
<body>
<div>
    Order ${pageContext.session.id}
</div>


<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="order_start">
    <div>
        Откуда: ${param.start_street} ${param.start_addr}
        <input type="hidden" name="start_point" value="${param.start_street} ${param.start_addr}">
    </div>
    <div>
        Куда: ${param.finish_street} ${param.finish_addr}
        <input type="hidden"name="finish_point" value="${param.finish_street} ${param.finish_addr}">
    </div>
    <div>
        Расстояние: ${param.distance}   км
        <%--  from mapbox  in m--%>
        <input type="hidden" name="distance" value="${param.distance}">
    </div>
    <div>
        Время: ${param.duration}   мин
        <%--  from mapbox  in sec--%>
        <input type="hidden" name="duration" value="${param.duration}">
    </div>
    <div>
        Класс авто:${param.class_auto}
        <input type="hidden" name="class_auto" value="${param.class_auto}">
    </div>
    <div>
        Коментрий для водителя:
        <input type="text" name="comment_for_driver" value="${param.comment_for_driver}">
    </div>
    <div>
        Цена: ${requestScope.cost}
        <input type="hidden" name="cost" value="${requestScope.cost}"> руб.
        <%--    rub --%>
    </div>
    <div>
        Количество доступных машин
        выбранного класса: ${requestScope.count_free_cars}
    </div>
    <input type="submit" name="user_button" value="Подтвердить заказ">
</form>

</body>
</html>
