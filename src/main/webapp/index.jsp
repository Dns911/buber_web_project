<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xml:lang="html">
<head>
    <title>Welcome to BUBER</title>
</head>
<body>
<jsp:forward page="/controller">
    <jsp:param name="command" value="goto_main"/>
</jsp:forward>
</body>
</html>