<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<body>
    <h1>Summary</h1>
    <div>
        Date : ${trx.date}
    </div>
    <div>
        Withdraw : $${trx.amount}
    </div>
    <div>
        Balance : $${trx.balance}
    </div>
</body>

<br/>
Is this transaction correct?<br/>
<a href="${pageContext.request.contextPath}/withdrawal/confirmed?id=${trx.id}" >Yes, Proceed</a><br/><br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction?acc=${trx.account}" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>