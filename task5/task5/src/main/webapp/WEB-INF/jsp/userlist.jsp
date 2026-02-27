<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список пользователей</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userlist.css">
</head>
<body>
    <div class="container">
        <h1>Управление пользователями</h1>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/useredit.jhtml" class="btn btn-add">Добавить пользователя</a>
            <a href="${pageContext.request.contextPath}/welcome.jhtml" class="btn btn-back">На главную</a>
        </div>

        <table class="user-table">
            <thead>
                <tr>
                    <th>Логин</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Email</th>
                    <th>Роль</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.login}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/useredit.jhtml?id=${user.login}" class="btn-edit">Редактировать</a>
                            <a href="#" onclick="confirmDelete('${user.login}')" class="btn-delete">Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        function confirmDelete(login) {
            if (confirm('Вы уверены, что хотите удалить пользователя ' + login + '?')) {
                window.location.href = '${pageContext.request.contextPath}/userdelete.jhtml?login=' + login;
            }
        }
    </script>
</body>
</html>