<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h1>Cash Withdrawal Confirmation</h1>

<%--todo: receive object, not string--%>
<br/>
${message}
<br/><br/><br/>

<br/>
Is this transaction correct?<br/>
<a href="${pageContext.request.contextPath}/" >Yes, Proceed</a><br/><br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>