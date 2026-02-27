<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Смена пароля</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginedit.css">
</head>
<body>
    <div class="password-card">
        <h2>Смена пароля</h2>
        <p style="text-align: center; margin-bottom: 20px; color: #666;">
            Пользователь: <span class="username-highlight">${sessionScope.userData.name}</span>
        </p>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <c:if test="${not empty message}">
            <div class="message success">${message}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/loginedit.jhtml" method="post">
            <input type="hidden" name="action" value="changePassword">

            <div class="form-group">
                <label for="oldPassword">Старый пароль</label>
                <input type="password" id="oldPassword" name="oldPassword" required>
            </div>

            <div class="form-group">
                <label for="newPassword">Новый пароль</label>
                <input type="password" id="newPassword" name="newPassword" required>
            </div>

            <button type="submit" class="btn-submit">Изменить пароль</button>
        </form>

        <a href="${pageContext.request.contextPath}/welcome.jhtml" class="back-link">← На главную</a>
    </div>
</body>
</html>