<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to BUBER</title>
</head>
<body>
<jsp:forward page="/controller">
    <jsp:param name="command" value="index"/>
</jsp:forward>
</body>
</html>