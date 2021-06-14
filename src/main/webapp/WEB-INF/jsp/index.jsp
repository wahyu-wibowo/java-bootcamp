<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h1>Mitrais Java Bootcamp Index Page</h1>

<a href="${pageContext.request.contextPath}/welcome" >Transaction</a><br/>
<br/>
<br/>


<h2>Inquiries</h2>
<a href="${pageContext.request.contextPath}/transaction/inquiry?param=acc" >Inquiry Transaction by Account</a><br/>
<a href="${pageContext.request.contextPath}/transaction/inquiry?param=date" >Inquiry Transaction by Date</a><br/>

<br/>
<h2>Account</h2>
<a href="${pageContext.request.contextPath}/account" >List Account</a><br/>
<a href="${pageContext.request.contextPath}/account/upload" >Upload Account CSV</a><br/>