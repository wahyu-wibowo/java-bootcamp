<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>

<h1>Cash Withdrawal</h1>

<a href="${pageContext.request.contextPath}/withdrawal/confirm?acc=${acc}&amt=10" >$10</a><br/>
<a href="${pageContext.request.contextPath}/withdrawal/confirm?acc=${acc}&amt=50" >$50</a><br/>
<a href="${pageContext.request.contextPath}/withdrawal/confirm?acc=${acc}&amt=100" >$100</a><br/>
<a href="${pageContext.request.contextPath}/withdrawal/other?acc=${acc}" >Other</a><br/>

<br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction?acc=${acc}" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>