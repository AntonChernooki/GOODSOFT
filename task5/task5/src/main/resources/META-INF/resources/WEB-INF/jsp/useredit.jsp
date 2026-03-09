<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<t:myhtml title="управление пользователем"  cssFile="useredit.css">
    <div class="edit-card">
        <h2>
            <c:choose>
                <c:when test="${empty user}">Добавление нового пользователя</c:when>
                <c:otherwise>Редактирование пользователя ${user.login}</c:otherwise>
            </c:choose>
        </h2>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

<form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/useredit.jhtml">            <input type="hidden" name="action" value="save">
            <c:if test="${not empty user}">
                <input type="hidden" name="originalLogin" value="${user.login}">
            </c:if>

            <div class="form-group">
                <label for="login">Логин *</label>
                <form:input path="login" id="login" cssClass="form-control" />
                <form:errors path="login"  />
            </div>

            <div class="form-group">
                <label for="password">Пароль *</label>
                 <form:password path="password" id="password" cssClass="form-control" />
                 <form:errors path="password"  />
            </div>

            <div class="form-group">
                <label for="email">Email *</label>
                <form:input path="email" id="email" cssClass="form-control" type="email" />
                <form:errors path="email" />
            </div>

            <div class="form-group">
                <label for="surname">Фамилия *</label>
                <form:input path="surname" id="surname" cssClass="form-control" />
                <form:errors path="surname" />
            </div>

            <div class="form-group">
                <label for="name">Имя *</label>
                <form:input path="name" id="name" cssClass="form-control" />
                <form:errors path="name"  />
            </div>

            <div class="form-group">
                <label for="patronymic">Отчество</label>
                <form:input path="patronymic" id="patronymic" cssClass="form-control" />
                <form:errors path="patronymic"  />
                   </div>

            <div class="form-group">
                <label for="birthday">Дата рождения *</label>
                <form:input path="birthday" id="birthday" cssClass="form-control" type="date" />
                <form:errors path="birthday"  />
            </div>

           <div class="form-group">
               <label>Роли *</label>
               <div class="checkbox-group">
                   <form:checkboxes items="${allRoles}" path="roles" delimiter="<br/>" />
                   </div>
                   <form:errors path="roles" cssClass="error-text" />
           </div>

            <div class="actions">
                <button type="submit" class="btn-submit">Сохранить</button>
                <a href="${pageContext.request.contextPath}/userlist.jhtml" class="btn-cancel">Отмена</a>
            </div>
        </form:form>
    </div>
</t:myhtml>
