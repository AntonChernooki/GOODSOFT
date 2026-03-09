<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<t:myhtml title="главная страница" cssFile="welcome.css">
    <div class="profile-card">
        <h1>Добро пожаловать, ${sessionScope.userData.name}!</h1>
        <p class="greeting">Рады видеть вас снова</p>

        <div class="info-grid">
            <span class="info-label">Логин:</span>
            <span class="info-value">${sessionScope.userData.login}</span>

            <span class="info-label">Имя:</span>
            <span class="info-value">${sessionScope.userData.name}</span>

            <span class="info-label">Фамилия:</span>
            <span class="info-value">${sessionScope.userData.surname}</span>

            <span class="info-label">Отчество:</span>
            <span class="info-value">${sessionScope.userData.patronymic}</span>

            <span class="info-label">Email:</span>
            <span class="info-value">${sessionScope.userData.email}</span>

            <span class="info-label">Дата рождения:</span>
            <span class="info-value">${sessionScope.userData.birthday}</span>

            <span class="info-label">Роли:</span>
            <span class="info-value">
                <c:forEach var="r" items="${sessionScope.userData.roles}" varStatus="status">
                    ${r}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </span>

            <c:if test="${sessionScope.userData.isAdmin()}">
                <a href="${pageContext.request.contextPath}/userlist.jhtml" class="btn btn-primary">Управление пользователями</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/loginedit.jhtml" class="btn btn-primary">Сменить пароль</a>
            <a href="${pageContext.request.contextPath}/welcome.jhtml?action=logout" class="btn btn-danger">Выйти</a>
        </div>
    </div>
</t:myhtml>