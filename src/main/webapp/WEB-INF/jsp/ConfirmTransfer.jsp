<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<body>
<h1>Fund Transfer Summary</h1>
    <div>
        Destination Account : ${trx.destinationAccount}
    </div>
    <div>
        Transfer Amount : $${trx.amount}
    </div>
    <div>
        Reference Number : ${trx.referenceNumber}
    </div>
    <div>
        Balance : $${trx.balance}
    </div>
</body>

<br/>
Is this transaction correct?<br/>
<a href="${pageContext.request.contextPath}/transfer/confirmed?id=${trx.id}" >Yes, Proceed</a><br/><br/>
<hr/>
<a href="${pageContext.request.contextPath}/transaction?acc=${trx.account}" >Back to Transaction</a><br/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>