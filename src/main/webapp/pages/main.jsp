<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 04-Dec-22
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<form action="controller" method="post">
    <div>
        <input type="text" name="start_point" placeholder="Откуда?">
    </div>
    <div>
        <input type="text" name="end_point" placeholder="Куда?">
    </div>
    <div>
        <input type="radio" name="class_auto" value="economy"/>Эконом
        <input type="radio" name="class_auto" value="standard"/>Стандарт
        <input type="radio" name="class_auto" value="business"/>Бизнес
        <input type="radio" name="class_auto" value="minivan"/>Минивэн
    </div>
    <div>
        <select name="pay_method" id="">
            <option selected value=cash">Наличные</option>
            <option value="card">Оплата картой</option>
        </select>
    </div>
    <div>
        <input type="text" name="start_point" placeholder="Комментарий водителю...">
    </div>
    <input type="hidden" name="command" value="order"/>
</form>
<div>
    <script type="text/javascript" charset="utf-8" async src="https://api-maps.yandex.ru/services/constructor/1.0/js/?um=constructor%3A5cd3881d387491e399a7ea11ba7124e73e891ecf36c74dc45b7d82ec777d8a96&amp;width=100%25&amp;height=593&amp;lang=ru_RU&amp;scroll=true"></script>
</div>
</body>
</html>
