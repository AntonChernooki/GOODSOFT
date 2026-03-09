<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="cssFile" required="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/switcher.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/${cssFile}">
</head>
<body>

<c:set var="currentLocale" value="${pageContext.response.locale.language}" />

<c:if test="${not empty requestScope['javax.servlet.forward.servlet_path']}">
    <c:set var="currentPath" value="${requestScope['javax.servlet.forward.servlet_path']}" />
</c:if>
<c:if test="${empty requestScope['javax.servlet.forward.servlet_path']}">
    <c:set var="currentPath" value="${pageContext.request.servletPath}" />
</c:if>

<c:set var="targetLang" value="en" />
<c:if test="${currentLocale == 'en'}">
    <c:set var="targetLang" value="ru" />
</c:if>

<c:url var="toggleLangUrl" value="${currentPath}">
    <c:param name="lang" value="${targetLang}" />
</c:url>

<div class="top-bar">
    <a href="${toggleLangUrl}" class="lang-toggle">
        <c:if test="${currentLocale == 'ru'}">
            <img src="${pageContext.request.contextPath}/images/flag_en.png" alt="Switch to English">
        </c:if>
        <c:if test="${currentLocale != 'ru'}">
            <img src="${pageContext.request.contextPath}/images/flag_ru.png" alt="Переключить на русский">
        </c:if>
    </a>
</div>

<jsp:doBody/>
</body>
</html>