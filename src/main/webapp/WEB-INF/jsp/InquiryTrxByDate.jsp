<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1>Inquiry Trx By Date</h1>

<form:form modelAttribute="form">
    <div>
        <form:label path="date">Enter date (ddMMyy)</form:label>
        <form:input path="date" />
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
