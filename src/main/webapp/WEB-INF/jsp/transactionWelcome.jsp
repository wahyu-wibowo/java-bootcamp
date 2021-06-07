<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1>Welcome</h1>

<form:form modelAttribute="form">
    <form:errors path="" element="div" />
    <div>
        <form:label path="accountNumber">Account Number</form:label>
        <form:input path="accountNumber" />
        <form:errors path="accountNumber" />
    </div>
    <div>
        <form:label path="pin">PIN</form:label>
        <form:input path="pin" />
        <form:errors path="pin" />
    </div>
    <div>
        <input type="submit" />
    </div>
</form:form>
</body>
</html>
