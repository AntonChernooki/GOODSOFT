<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>Вход<head>
<body>
<h1>Страница входа<h1>


<form action="${pageContext.request.contextPath}/login.jhtml" method="post">
<input type="hidden" name="action" value="login">
        Логин: <input type="text" name="login" required><br>
        Пароль: <input type="password" name="password" required><br>
        <input type="submit" value="Войти">
    </form>
</body>
</html>