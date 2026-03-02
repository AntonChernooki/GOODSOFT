<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="cssFile" required="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/${cssFile}">
</head>
<body>
    <jsp:doBody/>
</body>
</html>