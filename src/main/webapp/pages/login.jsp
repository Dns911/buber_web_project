<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 24-Jan-23
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
<%--    <div class="background">--%>
<%--        <img src="${pageContext.request.contextPath}/resources/img/wallpaper_login.jpg">--%>
<%--    </div>--%>
</head>
<body>
<style>
    body{
        background-image: url(${pageContext.request.contextPath}/resources/img/wallpaper_login_4.jpg);
        background-size: 100%;
        background-position: 0 100%;
    }
    ::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
        color: #ffffff;
        opacity: 1; /* Firefox */
    }
</style>

    <header>
        <h1>
            С возвращением, в bUber!
        </h1>
    </header>
    <form class="form_login" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <div>
            <input class="input_login" type="text" name="login" value="" placeholder="Ваш email">
        </div>
        <div>
            <input class="input_login" type="password" name="password" value="" placeholder="Пароль">
        </div>
        <div>
            <span ali>${login_msg}</span>
        </div>
<%--        <br/>--%>
<%--        ${filter_attr}--%>
<%--        <br/>--%>
<%--        ${pageContext.session.id}--%>
<%--        <br/>--%>
        <div class="form_radio_group">
            <div class="form_radio_group-item">
                <input id="radio-1" type="radio" name="user_role" value="client" checked>
                <label for="radio-1">Клиент</label>
            </div>
            <div class="form_radio_group-item">
                <input id="radio-2" type="radio" name="user_role" value="driver">
                <label for="radio-2">Водитель</label>
            </div>
        </div>
        <div>
            <input class="btn_dark_blue" type="submit" name="but1" value="Войти в систему"/>
        </div>
    </form>
    <form class="form_login" action="controller" method="post">
        <input type="hidden" name="command" value="restore_page"/>
        <button class="btn_dark_blue" type="submit">Забыли пароль?</button>
    </form>
    <form class="form_login" action="controller" method="post">
        <input type="hidden" name="command" value="registration"/>
        <input type="hidden" name="user_role" value="client"/>
        <%--    <input class="button" type="submit" name="but2" value="Стать пользователем"/>--%>
        <button class="btn_red" type="submit">Стать пользователем</button>
    </form>
    <form class="form_login" action="controller" method="post">
        <input type="hidden" name="command" value="registration"/>
        <input type="hidden" name="user_role" value="driver"/>
        <%--        <input class="btn1" type="submit" name="but2" value="Стать водителем"/>--%>
        <button class="btn_red" type="submit">Стать водителем</button>
    </form>

    <span><%--     тег строки, строчный--%>
         Lorem ipsum <a target="_blank" href="http://google.com">dolor</a> sit amet, consectetur adipisicing elit.
    </span>
    <br/>

    
    <footer style="color: #f8f8f8;font-size: 10px; position: absolute; bottom: 5px" >
        <h6>
            Minsk, 2023
        </h6>
    </footer>

</body>

</html>
