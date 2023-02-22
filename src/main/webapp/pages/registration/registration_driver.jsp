<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 16-Jan-23
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<header>
    <h1>
        Регистрация нового водителя
    </h1>
</header>
<form class="form_center" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add_user"/>
    <input type="hidden" name="user_role" value="driver"/>
    <input class="input" type="email" name="email" required placeholder="Ваш e-mail" value="${requestScope.email}"><span class="red_text"> ${requestScope.email_err}</span>
    <input class="input" type="text" name="phone_numb" required placeholder="Ваш телефон (+375 00 000-00-00)" value="${requestScope.phone_numb}"><span class="red_text"> ${requestScope.phone_numb_err}</span>
    <input class="input" type="text" name="user_name" required placeholder="Ваше имя" value="${requestScope.user_name}"><span class="red_text"> ${requestScope.user_name_err}</span>
    <input class="input" type="text" name="user_lastname" placeholder="Ваша фамилия" value="${requestScope.user_lastname}"><span class="red_text"> ${requestScope.user_lastname_err}</span>

    <input class="input" type="text" name="driver_lic_number" placeholder="Номер ВУ" value="${requestScope.driver_lic_number}"><span class="red_text"> ${requestScope.driver_lic_number_err}</span>
    <input class="input" type="date" name="driver_lic_valid" placeholder="Дата окончания ВУ" value="${requestScope.driver_lic_valid}"><span class="red_text"> ${requestScope.driver_lic_valid_err}</span>

    <input class="input" type="password" name="password" placeholder="Пароль" value=""/><span class="red_text"> ${requestScope.password_err}</span>
    <input class="input" type="password" name="password_check" placeholder="Пароль еще раз" value=""/><span class="red_text"> ${requestScope.password_check_err}</span>
    <span class="red_text"> ${requestScope.registr_msg}</span>
    <input class="btn" type="submit" name="but" value="Регистрация"/>

    <br/>

    ${requestScope.user_role}
    ${filter_attr}
</form>
<simpleType name="phone_numb">
    <restriction base="string">
        <pattern value="+375([0-9][0-9])[0-9][0-9][0-9][0-9][0-9][0-9][0-9]"/>
    </restriction>
</simpleType>
</body>
</html>
