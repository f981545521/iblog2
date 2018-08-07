<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Hello Word!</h1>
    <p>测试：http://localhost:9080/hello/hellojsp/666?name=%E6%BB%A8%E5%8D%97%E4%BD%A0</p>
    <h2>my name is ${id}</h2><!--@PathVariable 可以直接访问到参数-->
    <h2>my name is ${name}</h2><!--@RequestParam 不可以直接访问到参数-->
    <h2>my name is ${param.name}</h2><!--@RequestParam 是要param.xx 来访问-->
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
