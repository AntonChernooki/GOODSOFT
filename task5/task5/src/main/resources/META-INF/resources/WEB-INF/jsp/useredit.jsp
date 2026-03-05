<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


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

        <form action="${pageContext.request.contextPath}/useredit.jhtml" method="post">
            <input type="hidden" name="action" value="save">
            <c:if test="${not empty user}">
                <input type="hidden" name="originalLogin" value="${user.login}">
            </c:if>

            <div class="form-group">
                <label for="login">Логин *</label>
                <input type="text" id="login" name="login" value="${user.login}" required>
                <c:if test="${not empty errors.login}">
                    <span class="error-text">${errors.login}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="password">Пароль *</label>
                <input type="password" id="password" name="password" value="${user.password}" required>
                <c:if test="${not empty errors.password}">
                    <span class="error-text">${errors.password}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" value="${user.email}" required>
                <c:if test="${not empty errors.email}">
                    <span class="error-text">${errors.email}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="surname">Фамилия *</label>
                <input type="text" id="surname" name="surname" value="${user.surname}" required>
                <c:if test="${not empty errors.surname}">
                    <span class="error-text">${errors.surname}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="name">Имя *</label>
                <input type="text" id="name" name="name" value="${user.name}" required>
                <c:if test="${not empty errors.name}">
                    <span class="error-text">${errors.name}</span>
                </c:if>
            </div>

            <div class="form-group">
                <label for="patronymic">Отчество</label>
                <input type="text" id="patronymic" name="patronymic" value="${user.patronymic}">
            </div>

            <div class="form-group">
                <label for="birthday">Дата рождения *</label>
                <input type="date" id="birthday" name="birthday" value="${user.birthday}" required>
                <c:if test="${not empty errors.birthday}">
                    <span class="error-text">${errors.birthday}</span>
                </c:if>
            </div>

           <div class="form-group">
               <label>Роли *</label>
               <div class="checkbox-group">
                   <c:forEach var="r" items="${allRoles}">
                       <label>
                           <input type="checkbox" name="roles" value="${r}"
                               <c:if test="${user != null && user.roles.contains(r)}">checked</c:if> />
                           <c:if test="${r == 'ADMIN'}">Администратор</c:if>
                           <c:if test="${r != 'ADMIN'}">Пользователь</c:if>
                       </label><br/>
                   </c:forEach>
               </div>
               <c:if test="${not empty errors.role}">
               <div class="message error">${error}</div>
               <c:if test="${not empty errors.global}">
                   <div class="message error">${errors.global}</div>
               </c:if>
                   <span class="error-text">${errors.role}</span>
               </c:if>
           </div>

            <div class="actions">
                <button type="submit" class="btn-submit">Сохранить</button>
                <a href="${pageContext.request.contextPath}/userlist.jhtml" class="btn-cancel">Отмена</a>
            </div>
        </form>
    </div>
</t:myhtml>
