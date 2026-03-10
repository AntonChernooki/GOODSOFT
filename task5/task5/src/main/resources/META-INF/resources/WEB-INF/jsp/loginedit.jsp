<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:myhtml title="<spring:message code='password.title'/>"  cssFile="loginedit.css">
    <div class="password-card">
        <h2><spring:message code="password.header"/></h2>
        <p style="text-align: center; margin-bottom: 20px; color: #666;">
                    <spring:message code="password.userLabel"/>
                    <span class="username-highlight">
                        <sec:authentication property="principal.name" />
                    </span>
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
                <label for="newPassword"><spring:message code="password.newPassword"/></label>
                <form:password path="newPassword" id="newPassword" cssClass="form-control" />
                <form:errors path="newPassword"  />
            </div>

            <button type="submit" class="btn-submit"><spring:message code="password.submit"/></button>
        </form:form>

        <a href="${pageContext.request.contextPath}/welcome.jhtml" class="back-link"><spring:message code="password.back"/></a>
    </div>
</t:myhtml>