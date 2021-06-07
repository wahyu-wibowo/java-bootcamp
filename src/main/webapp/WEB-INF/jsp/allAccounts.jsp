<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h1>Account Page</h1>

<ul>
<c:forEach items="${accounts}" var="account">
   <li>${account}</li>
</c:forEach>
</ul>

<hr/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>