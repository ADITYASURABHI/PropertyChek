<!-- src/main/webapp/WEB-INF/views/form.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Enter Address</title>
</head>
<body>
    <h2>Address Input Form</h2>
    <form action="submit" method="post">
        <label for="address">Enter Address:</label>
        <input type="text" name="address" id="address" required />
        <button type="submit">Submit</button>
    </form>

    <c:if test="${not empty enteredAddress}">
        <p>You entered: <strong>${enteredAddress}</strong></p>
    </c:if>
</body>
</html>