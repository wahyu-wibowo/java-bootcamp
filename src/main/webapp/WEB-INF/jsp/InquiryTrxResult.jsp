<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<h1>Transaction Inquiry</h1>

<table width="59%" border="1">
   <thead>
   <tr>
      <td><b>Source Account</b></td>
      <td><b>Amount</b></td>
      <td><b>Transaction Time</b></td>
      <td><b>Destination Account</b></td>
      <td><b>Reference</b></td>
   </tr>
   </thead>
   <tbody>
   <c:forEach items="${trxs}" var="trx">
      <tr>
         <td>${trx.account}</td>
         <td>$${trx.amount}</td>
         <td>${trx.date}</td>
         <td>${trx.destinationAccount}</td>
         <td>${trx.referenceNumber}</td>
      </tr>
   </c:forEach>
   </tbody>
</table>

<hr/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>