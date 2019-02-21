<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <main class="background">
        <div class="main-div">
            <div class="content-box login-size">
                <c:if test="${not empty error}">
                    <div style="color: red">
                            ${error}
                    </div>
                </c:if>
                <form action="<c:url value="/security_check"/>" method="post">
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username">

                    <label for="pass">Password</label>
                    <input type="password" name="password" id="pass">
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    </main>

</head>
<body>

</body>
</html>
