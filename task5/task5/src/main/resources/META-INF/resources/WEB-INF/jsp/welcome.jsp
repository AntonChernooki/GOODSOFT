<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:myhtml title="<spring:message code='welcome.title'/>" cssFile="welcome.css">
    <div class="profile-card">
        <sec:authentication property="principal" var="currentUser"/>
        <h1><spring:message code="welcome.header" arguments="${currentUser.name}"/></h1>
        <p class="greeting"><spring:message code="welcome.greeting"/></p>

        <div class="info-grid">
            <span class="info-label"><spring:message code="welcome.login"/></span>
            <span class="info-value">${currentUser.login}</span>

            <span class="info-label"><spring:message code="welcome.name"/></span>
            <span class="info-value">${currentUser.name}</span>

            <span class="info-label"><spring:message code="welcome.surname"/></span>
            <span class="info-value">${currentUser.surname}</span>

            <span class="info-label"><spring:message code="welcome.patronymic"/></span>
            <span class="info-value">${currentUser.patronymic}</span>

            <span class="info-label"><spring:message code="welcome.email"/></span>
            <span class="info-value">${currentUser.email}</span>

            <span class="info-label"><spring:message code="welcome.birthday"/></span>
            <span class="info-value">${currentUser.birthday}</span>

            <span class="info-label"><spring:message code="welcome.roles"/></span>
            <span class="info-value">
                <c:forEach var="r" items="${currentUser.roles}" varStatus="status">
                    ${r}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </span>

            <sec:authorize access="hasRole('ADMIN')">
                <a href="${pageContext.request.contextPath}/userlist.jhtml" class="btn btn-primary"><spring:message code="welcome.manageUsers"/></a>
            </sec:authorize>

            <a href="${pageContext.request.contextPath}/loginedit.jhtml" class="btn btn-primary"><spring:message code="welcome.changePassword"/></a>


            <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="post" style="display:none;">
                <sec:csrfInput/>
            </form>
            <a href="#" onclick="document.getElementById('logoutForm').submit(); return false;" class="btn btn-danger"><spring:message code="welcome.logout"/></a>
        </div>
    </div>
</t:myhtml>