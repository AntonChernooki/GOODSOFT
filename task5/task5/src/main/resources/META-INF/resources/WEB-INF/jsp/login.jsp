<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:myhtml title="<spring:message code='login.title'/>" cssFile="login.css">

    <div class="login-container">
        <h1><spring:message code="login.header"/></h1>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

 <form:form modelAttribute="loginForm" method="post" action="${pageContext.request.contextPath}/login.jhtml">

            <div class="form-group">
                <label for="login"><spring:message code="login.username"/></label>
                <input type="text" id="login" name="login" required>
                <form:errors path="login" cssClass="error-text"/>
            </div>

            <div class="form-group">
                <label for="password"><spring:message code="login.password"/></label>
                <input type="password" id="password" name="password" required>
                 <form:errors path="password" cssClass="error-text"/>
            </div>

            <button type="submit" class="btn-login"><spring:message code="login.submit"/></button>
        </form:form>
    </div>
</t:myhtml>
