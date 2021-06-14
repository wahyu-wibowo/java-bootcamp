<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1>Inquiry Trx By Account</h1>

<form:form modelAttribute="form">
    <div>
        <form:label path="account">Enter account</form:label>
        <form:input path="account" />
    </div>
    <div>
        <input type="submit" />
    </div>
</form:form>
</body>


<br/>
<hr/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>
</html>
