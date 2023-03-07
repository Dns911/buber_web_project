<%--
  Created by IntelliJ IDEA.
  User: vds15
  Date: 19-Dec-22
  Time: 5:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error 500</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
Error 500
Request from: ${pageContext.errorData.requestURI} is failed<br/>
Servlet name: ${pageContext.errorData.servletName}<br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.exception}<br/>
Exception massage: ${pageContext.exception.message}<br/>
<br/><br/>
Message from exception: ${error_msg}
<br/><br/>
<form class="form_center" action="${pageContext.request.contextPath}/index.jsp">
    <input class="btn_dark_blue" type="submit" name="but1" value="Вернуться на главную"/>
</form>
</body>
</html>
