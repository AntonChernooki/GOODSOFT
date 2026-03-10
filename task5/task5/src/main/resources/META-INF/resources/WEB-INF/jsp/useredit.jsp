<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:myhtml title="<spring:message code='useredit.title'/>" cssFile="useredit.css">
    <div class="edit-card">
        <h2>
            <c:if test="${empty user.originalLogin}">
                <spring:message code="useredit.addHeader"/>
            </c:if>
            <c:if test="${not empty user.originalLogin}">
                <spring:message code="useredit.editHeader" arguments="${user.login}"/>
            </c:if>
        </h2>

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/useredit.jhtml">
            <input type="hidden" name="action" value="save">
            <form:hidden path="originalLogin" />

            <div class="form-group">
                <label for="login"><spring:message code="useredit.login"/></label>
                <form:input path="login" id="login" cssClass="form-control"/>
                <form:errors path="login" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="password"><spring:message code="useredit.password"/></label>
                <form:password path="password" id="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="email"><spring:message code="useredit.email"/></label>
                <form:input path="email" id="email" cssClass="form-control" type="email"/>
                <form:errors path="email" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="surname"><spring:message code="useredit.surname"/></label>
                <form:input path="surname" id="surname" cssClass="form-control"/>
                <form:errors path="surname" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="name"><spring:message code="useredit.name"/></label>
                <form:input path="name" id="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="patronymic"><spring:message code="useredit.patronymic"/></label>
                <form:input path="patronymic" id="patronymic" cssClass="form-control"/>
                <form:errors path="patronymic" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="birthday"><spring:message code="useredit.birthday"/></label>
                <form:input path="birthday" id="birthday" cssClass="form-control" type="date"/>
                <form:errors path="birthday" cssClass="error-text" />
            </div>

            <div class="form-group">
                <label><spring:message code="useredit.roles"/></label>
                <div class="checkbox-group">
                    <form:checkboxes items="${allRoles}" path="roles" delimiter="<br/>"/>
                </div>
                <form:errors path="roles" cssClass="error-text"/>
            </div>

            <div class="actions">
                <button type="submit" class="btn-submit"><spring:message code="useredit.save"/></button>
                <a href="${pageContext.request.contextPath}/userlist.jhtml" class="btn-cancel"><spring:message code="useredit.cancel"/></a>
            </div>
        </form:form>
    </div>
</t:myhtml>