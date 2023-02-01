<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 04-Dec-22
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<form action="controller" method="post">
    <div>
<%--        <input type="text" name="start_point" placeholder="Откуда?">--%>
        <select name="start_point">
            <option selected disabled value="">Откуда?</option>
            <option value="store">Магазин</option>
            <option value="bakery">Пекарня</option>
            <option value="school">Школа</option>
        </select>
    </div>
    <div>
        <select name="finish_point">
            <option selected disabled value="">Куда?</option>
            <option value="store">Магазин</option>
            <option value="bakery">Пекарня</option>
            <option value="school">Школа</option>
        </select>
    </div>
    <div>
        <input type="radio" name="class_auto" value="economy"/>Эконом
        <input type="radio" name="class_auto" value="standard"/>Стандарт
        <input type="radio" name="class_auto" value="business"/>Бизнес
        <input type="radio" name="class_auto" value="minivan"/>Минивэн
    </div>
    <div>
        <select name="pay_method">
            <option selected value=cash">Наличные</option>
            <option value="card">Оплата картой</option>
        </select>
    </div>
    <div>
        <input type="text" name="start_point" placeholder="Комментарий водителю...">
    </div>
    <input type="hidden" name="command" value="preorder"/>
    <input type="submit" name="button_order" value="Оформление заказа">
</form>
<div>
    <script type="text/javascript" charset="utf-8" async src="https://api-maps.yandex.ru/services/constructor/1.0/js/?um=constructor%3A65074fdda736f61b11365f2500689711231110d248e85ee90da2c770422a386d&amp;width=100%25&amp;height=582&amp;lang=ru_RU&amp;scroll=true"></script>
</div>
</body>
</html>
