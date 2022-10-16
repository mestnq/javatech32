<%--
  Created by IntelliJ IDEA.
  User: Ксения
  Date: 15.10.2022
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Manager</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<form method="post">
    <input type="submit" name="exit" value="Exit"/>
</form>
<h5>${date}</h5>
<h1>${currentPath}</h1>
<ul>
    <b><a href="?path=${currentPath.substring(0, currentPath.lastIndexOf("\\") + (currentPath.lastIndexOf("\\") != currentPath.indexOf("\\") ? 0 : 1))}">Вверх</a></b><br><br>
    <c:forEach var="directory" items="${directories}">
        <a href="?path=${directory.getAbsolutePath()}">${directory.getName()}/</a><br>
    </c:forEach>
    <c:forEach var="file" items="${files}">
        <a href="?path=${file.getAbsolutePath()}">${file.getName()}</a><br>
    </c:forEach>
</ul>
</body>
</html>
