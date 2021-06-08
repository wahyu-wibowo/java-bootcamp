<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>

<h1>Cash Withdrawal</h1>

<a href="${pageContext.request.contextPath}/withdrawal/confirm?acc=${acc}&amt=10" >$10</a><br/>
<a href="${pageContext.request.contextPath}/withdrawal/confirm?acc=${acc}&amt=50" >$50</a><br/>
<a href="${pageContext.request.contextPath}/withdrawal/confirm?acc=${acc}&amt=100" >$100</a><br/>
<a href="${pageContext.request.contextPath}/withdrawal/other?acc=${acc}" >Other</a><br/>

<%--<form method="get" id="myForm1" action="OtherWithdrawal.jsp">
    <Input type="Hidden" name="account"  id="account" value="TEST">
    <Input type="Hidden" name="amount"  id="amount" value="123">
    &lt;%&ndash;<a href="$('#myForm1').submit();">Please Enroll Finger 1</a>&ndash;%&gt;
    <Input type="submit" value ="Please Enroll Finger 1">
</form>--%>

<%--<form:form modelAttribute="form">
    <form:errors path="" element="div" />
    <div>
        <form:input path="account"  value="10000" type="hidden" />
    </div>
    <div>
        <input type="submit" value="Other" />
    </div>
</form:form>--%>

<br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction?acc=${acc}" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>