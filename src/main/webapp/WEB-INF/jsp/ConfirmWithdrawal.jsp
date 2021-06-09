<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h1>Cash Withdrawal Confirmation</h1>

<%--todo: receive object, not string--%>
<br/>
${trx.amount}
<br/><br/><br/>

<br/>
Is this transaction correct?<br/>
<a href="${pageContext.request.contextPath}/withdrawal/confirmed?id=${trx.id}" >Yes, Proceed</a><br/><br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction?acc=${trx.account}" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>