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
    <title>Taxi Buber</title>
    <meta name='viewport' content='width=device-width, initial-scale=1' />
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v2.9.2/mapbox-gl.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v2.9.2/mapbox-gl.css' rel='stylesheet' />
    <script src="https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-geocoder/v5.0.0/mapbox-gl-geocoder.min.js"></script>
    <link rel="stylesheet" href="https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-geocoder/v5.0.0/mapbox-gl-geocoder.css" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
    <style>
        body { margin: 0; padding: 0; }
        #map { position: absolute; top: 0; bottom: 0; left: 350px;  right: 0;}
    </style>
</head>
<body>
<div class="bold_blue_text">
    <span> Добро пожаловать, ${sessionScope.user_login}!</span>
</div>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="user_page">
    <input class="btn_red" type="submit" name="user_button" value="Страница пользователя/Вход">
</form>
<div id='map'></div>
<nav id="menu"></nav>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <div id="geocoder_from" name="from" class="geocoder"></div>
    <div id="geocoder_to" name="to" class="geocoder1"></div>
    <br/>
    <div>
        <div class="form_radio_group">
            <div class="form_radio_group-item">
                <input id="radio-1" type="radio" name="class_auto" value="economy">
                <label for="radio-1">Эконом</label>
            </div>
            <div class="form_radio_group-item">
                <input id="radio-2" type="radio" name="class_auto" value="standard"  checked>
                <label for="radio-2">Стандарт</label>
            </div>
        </div>
    </div>
    <div>
        <div class="form_radio_group">
            <div class="form_radio_group-item">
                <input id="radio1-1" type="radio" name="class_auto" value="business">
                <label for="radio1-1">Бизнес</label>
            </div>
            <div class="form_radio_group-item">
                <input id="radio1-2" type="radio" name="class_auto" value="minivan">
                <label for="radio1-2">Минивен</label>
            </div>
    </div></div>
    <div>
        <select class="select_main" name="pay_method">
            <option selected value=cash">Наличные</option>
            <option value="card">Оплата картой</option>
        </select>
    </div>
    <div>
        <textarea name="comment_for_driver" style="resize: none; width: 350px" rows="2" placeholder="Комментарий водителю..." maxlength="60"></textarea>
    </div>
    <div><input type="hidden" step="0.01" id="duration" name="duration"></div>

    <div><input type="hidden" step="0.01" id="distance" name="distance"></div>
    <div>
        <input type="hidden" id="start_street" name="start_street">
    </div>
    <div>
        <input type="hidden" id="start_addr" name="start_addr">
    </div>
    <div class="bold_red_text" id="duration_txt"><span> </span></div>
    <div class="bold_red_text" id="distance_txt"><span> </span></div>
    <div>
        <input type="hidden" id="finish_street" name="finish_street">
    </div>
    <div>
        <input type="hidden" id="finish_addr" name="finish_addr">
    </div>
    <input type="hidden" name="command" value="preorder"/>
    <input class="btn_dark_blue" type="submit" name="button_order" value="Перейти к заказу">
    <div class="bold_red_text" style="width: 300px">${requestScope.preorder_msg}</div>
</form>
<script src="${pageContext.request.contextPath}/pages/map/main.js"></script>
</body>
</html>
