<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<t:myhtml title="<spring:message code='welcome.title'/>" cssFile="welcome.css">
    <div class="profile-card">
        <h1><spring:message code="welcome.header" arguments="${sessionScope.userData.name}"/></h1>
        <p class="greeting"><spring:message code="welcome.greeting"/></p>

        <div class="info-grid">
            <span class="info-label"><spring:message code="welcome.login"/></span>
            <span class="info-value">${sessionScope.userData.login}</span>

            <span class="info-label"><spring:message code="welcome.name"/></span>
            <span class="info-value">${sessionScope.userData.name}</span>

            <span class="info-label"><spring:message code="welcome.surname"/></span>
            <span class="info-value">${sessionScope.userData.surname}</span>

            <span class="info-label"><spring:message code="welcome.patronymic"/></span>
            <span class="info-value">${sessionScope.userData.patronymic}</span>

            <span class="info-label"><spring:message code="welcome.email"/></span>
            <span class="info-value">${sessionScope.userData.email}</span>

            <span class="info-label"><spring:message code="welcome.birthday"/></span>
            <span class="info-value">${sessionScope.userData.birthday}</span>

            <span class="info-label"><spring:message code="welcome.roles"/></span>
            <span class="info-value">
                <c:forEach var="r" items="${sessionScope.userData.roles}" varStatus="status">
                    ${r}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </span>

            <c:if test="${sessionScope.userData.isAdmin()}">
                <a href="${pageContext.request.contextPath}/userlist.jhtml" class="btn btn-primary"><spring:message code="welcome.manageUsers"/></a>
            </c:if>
            <a href="${pageContext.request.contextPath}/loginedit.jhtml" class="btn btn-primary"><spring:message code="welcome.changePassword"/></a>
            <a href="${pageContext.request.contextPath}/welcome.jhtml?action=logout" class="btn btn-danger"><spring:message code="welcome.logout"/></a>
        </div>
    </div>
</t:myhtml>