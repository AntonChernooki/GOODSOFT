<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:myhtml title="смена пароля"  cssFile="loginedit.css">
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

       <form:form modelAttribute="passwordChangeForm" method="post" action="${pageContext.request.contextPath}/loginedit.jhtml">
            <input type="hidden" name="action" value="changePassword">

            <div class="form-group">
                <form:password path="oldPassword" id="oldPassword" cssClass="form-control" />
                <form:errors path="oldPassword"/>
            </div>

            <div class="form-group">
                <label for="newPassword">Новый пароль</label>
                <form:password path="newPassword" id="newPassword" cssClass="form-control" />
                <form:errors path="newPassword"  />
                   </div>

            <button type="submit" class="btn-submit">Изменить пароль</button>
        </form:form>

        <a href="${pageContext.request.contextPath}/welcome.jhtml" class="back-link">← На главную</a>
    </div>
</t:myhtml>