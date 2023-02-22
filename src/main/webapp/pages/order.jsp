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
<div>
    Откуда:
    <input type="text" disabled value="${param.start_street} ${param.start_addr}">
</div>
<div>
    Куда: ${param.finish_street} ${param.finish_addr}
</div>
<div>
    Расстояние: ${param.distance}
    <%--  from mapbox  in m--%>
</div>
<div>
    Время: ${param.duration}
<%--  from mapbox  in sec--%>
</div>
<div>
    Коментрий для водителя:
    ${param.comment_for_driver}
</div>
<div>
    Цена:
    ${requestScope.cost}
<%--    rub --%>
</div>
<div>
    ${requestScope.count_cars_online}
</div>

</body>
</html>
