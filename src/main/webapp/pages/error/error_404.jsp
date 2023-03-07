<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 19-Dec-22
  Time: 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ERROR 404</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div>
    Error 404
    Request from: ${pageContext.errorData.requestURI} is failed<br/>
    Servlet name: ${pageContext.errorData.servletName}<br/>
    Status code: ${pageContext.errorData.statusCode}<br/>
    ${pageContext.errorData.info}
</div>
<form class="form_center" action="${pageContext.request.contextPath}/index.jsp">
    <input class="btn_dark_blue" type="submit" name="but1" value="Вернуться на главную"/>
</form>
</body>
</html>
