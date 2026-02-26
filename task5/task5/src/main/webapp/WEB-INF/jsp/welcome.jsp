<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Главная</title>
</head>
<body>
    <h1>Добро пожаловать, ${sessionScope.userData.username}!</h1>
    <p>Это главная страница</p>

    <a href="${pageContext.request.contextPath}/loginedit.jhtml">Сменить пароль</a><br>

    <a href="${pageContext.request.contextPath}/welcome.jhtml?action=logout">Выйти</a>
</body>
</html>