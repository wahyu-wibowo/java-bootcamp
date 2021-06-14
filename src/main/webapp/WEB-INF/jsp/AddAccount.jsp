<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
    <h1>Add New Account</h1>

    <form:form modelAttribute="form">
        <form:errors path="" element="div" />
        <div>
            <form:label path="path">File Absolute Path</form:label>
            <form:input path="path" />
            <form:errors path="path" />
        </div>
        <div>
            <input type="submit" />
        </div>
    </form:form>
</body>

<br/>
<hr/>
<a href="${pageContext.request.contextPath}/" >Back to Index</a>
</html>
</html>
