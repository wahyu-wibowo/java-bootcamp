<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h1>Choose Transaction</h1>

<a href="${pageContext.request.contextPath}/withdrawal?acc=${acc}" >Withdrawal</a><br/>
<a href="${pageContext.request.contextPath}/transfer?acc=${acc}" >Fund Transfer</a><br/>

<br/>
<hr/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>