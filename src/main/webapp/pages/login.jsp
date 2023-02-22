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
</head>
<body>
<header>
    <h1>
        Войти в систему или
        стать новым пользователем!
    </h1>
</header>
<form class="form_login" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="login"/>
    <div>
        <input class="input" type="text" name="login" value="" placeholder="Ваш email">
    </div>
   <div>
       <input class="input" type="password" name="password" value="" placeholder="Пароль">
   </div>
    <div>
        <input type="radio" name="user_role" checked value="client"/>Пассажир
        <input type="radio" name="user_role" value="driver"/>Водитель
    </div>
<%--    <div>--%>
<%--        <input type="file" name="driver_photo"/>--%>
<%--    </div>--%>
    ${login_msg}
    <br/>
    ${filter_attr}
    <br/>
    ${pageContext.session.id}
    <br/>
    <input class="btn" type="submit" name="but1" value="Войти в систему"/>
</form>
<form class="form_login" action="controller" method="post">
    <input type="hidden" name="command" value="restore_page"/>
    <button class="btn" type="submit">Забыли пароль?</button>
</form>
<form class="form_login" action="controller" method="post">
    <input type="hidden" name="command" value="registration"/>
    <input type="hidden" name="user_role" value="client"/>
    <%--    <input class="button" type="submit" name="but2" value="Стать пользователем"/>--%>
    <button class="btn" type="submit">Стать пользователем</button>
</form>
<form class="form_login" action="controller" method="post">
    <input type="hidden" name="command" value="registration"/>
    <input type="hidden" name="user_role" value="driver"/>
    <%--        <input class="btn1" type="submit" name="but2" value="Стать водителем"/>--%>
    <button class="btnDriver" type="submit">Стать водителем</button>
</form>

Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab architecto atque corporis deleniti eum id, illo, ipsam,
magnam magni maxime non omnis pariatur placeat quas reprehenderit sequi sunt totam voluptatibus!
</p>
<span><%--     тег строки, строчный--%>
         Lorem ipsum <a target="_blank" href="http://google.com">dolor</a> sit amet, consectetur adipisicing elit.
    </span>
<br/>
<img src="${pageContext.request.contextPath}/resources/img/244890385a3ac00d077ab2006331187b.jpg" alt="taxi">
<ul> <%--     маркированный список--%>
    <li> point</li>
    <li> point</li>
    <li> point</li>
    <li> point</li>
    <li> point</li>
</ul>
<ol> <%--     нумерованный список--%>
    <li> point</li>
    <li> point</li>
    <li> point</li>
    <li> point</li>
    <li> point</li>
</ol>
<div> <%--     поле ввода текста--%>
    <textarea name="" cols="30" rows="10"></textarea>
</div>
<div> <%--     выпадающий список--%>
    <select multiple name="color" id="">
        <option value="red">Красный</option>
        <option selected value="blue">Голубой</option>
        <option value="yellow">Желтый</option>
    </select>
</div>
<div>
    <%--    <button type="submit">Отправить</button>--%>
    <%--    <button type="reset">Очистить форму</button>--%>
</div>
<footer>
    <h6>
        Minsk, 2023
    </h6>
</footer>
</body>
<body>

</body>
</html>
