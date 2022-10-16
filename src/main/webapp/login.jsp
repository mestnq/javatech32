<%--
  Created by IntelliJ IDEA.
  User: Ксения
  Date: 16.10.2022
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post">
    <label>Login:
        <input type="text" name="login"><br/>
    </label>

    <label>Password:
        <input type="password" name="password"><br/>
    </label>

    <button type="submit">Login</button>
</form>
<a href="/registration">
    <button>Registration</button>
</a>
</body>
</html>
