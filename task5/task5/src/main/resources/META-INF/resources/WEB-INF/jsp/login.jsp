<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:myhtml title="Вход в систему" cssFile="login.css">

    <div class="login-container">
        <h1>Вход в систему</h1>


         <c:if test="${not empty errors}">
                    <div class="error-message">
                        <c:forEach var="err" items="${errors}">
                            ${err.value}<br/>
                        </c:forEach>
                    </div>
                </c:if>


        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login.jhtml" method="post">
            <input type="hidden" name="action" value="login">

            <div class="form-group">
                <label for="login">Логин</label>
                <input type="text" id="login" name="login" required>
            </div>

            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="btn-login">Войти</button>
        </form>
    </div>
</t:myhtml>
