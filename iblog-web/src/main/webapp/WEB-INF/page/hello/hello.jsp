<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Hello Word!</h1>
    <h2>my name is ${name}</h2>
    <h2>my name is ${param.name}</h2>
    <h2>my name is ${param["name"]}</h2>
    <h2>my age is ${sessionScope.age}</h2>
    <ul>
        <c:forEach items="${tBossList}" var="item">
            <li>${item.name}</li>
        </c:forEach>
    </ul>
    <!--
     EL表达式的取值范围
        pageScope、requestScope、sessionScope、applicationScope

    -->
</body>
</html>
