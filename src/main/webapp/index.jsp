<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to BUBER</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>

<body>
<h1> "Welcome to BUBER!"
</h1>
<br/>
<form class="form_login" action="controller" method="post">
    ${logout_msg}
    <br/>
    <input type="hidden" name="command" value="login"/>
    Login: <input class="input" type="text" name="login" value=""/>
    <br/>
    Password: <input class="input" type="password" name="password" value=""/>
    <%--    <input list="type" name="mathOperator"/>--%>
    <%--    <datalist id="type">--%>
    <%--        <option>+</option>--%>
    <%--        <option>-</option>--%>
    <%--        <option>x</option>--%>
    <%--        <option>/</option>--%>
    <%--    </datalist>--%>
    <br/>
    ${login_msg}
    <br/>
    <input class="btn" type="submit" name="but" value="Submit"/>
</form>
<form class="form_login" action="pages/registration.jsp">
    <input class="btn" type="submit" name="but" value="Registration"/>
</form>
</body>
</html>