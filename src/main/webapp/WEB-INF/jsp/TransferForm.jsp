<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1>Fund Transfer</h1>

<form:form modelAttribute="form">
    <div>
        <form:label path="destinationAccount">Destination Account</form:label>
        <form:input path="destinationAccount" />
    </div>
    <div>
        <form:label path="amount">Amount to Transfer</form:label>
        <form:input path="amount" />
        <form:input path="account"  value="${account}" type="hidden" />
    </div>
    <div>
        <input type="submit" />
    </div>
</form:form>
</body>


<br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction?acc=${form.account}" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>
</html>
