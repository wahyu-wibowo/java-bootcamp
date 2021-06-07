<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1>Other Withdrawal</h1>

<form:form modelAttribute="form">
    <div>
        <form:label path="amount">Enter amount to withdraw</form:label>
        <form:input path="amount" />
        <%--<form:input path="account"  value="${account}" type="hidden" />--%>
    </div>
    <div>
        <input type="submit" />
    </div>
</form:form>
</body>


<br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>
</html>
