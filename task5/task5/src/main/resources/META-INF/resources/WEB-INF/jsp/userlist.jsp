<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<t:myhtml title="<spring:message code='userlist.title'/>"  cssFile="userlist.css">
    <div class="container">
        <h1><spring:message code="userlist.header"/></h1>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/useredit.jhtml" class="btn btn-add"><spring:message code="userlist.addUser"/></a>
            <a href="${pageContext.request.contextPath}/welcome.jhtml" class="btn btn-back"><spring:message code="userlist.backToMain"/></a>
        </div>

        <table class="user-table">
            <thead>
                <tr>
                    <th><spring:message code="userlist.login"/></th>
                    <th><spring:message code="userlist.name"/></th>
                    <th><spring:message code="userlist.surname"/></th>
                    <th><spring:message code="userlist.email"/></th>
                    <th><spring:message code="userlist.role"/></th>
                    <th><spring:message code="userlist.actions"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.login}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.email}</td>
                        <td>
                            <c:forEach var="r" items="${user.roles}" varStatus="status">
                                ${r}
                                <c:if test="${!status.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/useredit.jhtml?login=${user.login}" class="btn-edit"><spring:message code="userlist.edit"/></a>
                            <a href="#" onclick="confirmDelete('${user.login}')" class="btn-delete"><spring:message code="userlist.delete"/></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        function confirmDelete(login) {
            var template = '<spring:message code="userlist.deleteConfirm" arguments="{0}"/>';
            var message = template.replace('{0}', login);
            if (confirm(message)) {
                window.location.href = '${pageContext.request.contextPath}/userdelete.jhtml?login=' + login;
            }
        }
    </script>
</t:myhtml>