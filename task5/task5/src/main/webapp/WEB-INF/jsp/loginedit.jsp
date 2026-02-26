<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Смена пароля</title>
</head>
<body>
    <h2>Смена пароля для пользователя ${sessionScope.user.login}</h2>
    <form action="${pageContext.request.contextPath}/loginedit.jhtml" method="post">
        <input type="hidden" name="action" value="changePassword">
        Старый пароль: <input type="password" name="oldPassword" required><br>
        Новый пароль: <input type="password" name="newPassword" required><br>
        <input type="submit" value="Изменить пароль">
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/welcome.jhtml">На главную</a>
</body>
</html>