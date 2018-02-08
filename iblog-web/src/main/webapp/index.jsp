<%@ page import="java.io.File" %>
<%@page pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>JSP</title>
</head>
<body>
<h2>还是乱码吗？</h2>
<!-- 3.JSP声明 -->
<%!
    public double multiple(double d){
        return d*100;
    }
%>
<ul>
    <!-- 1.JSP脚本 -->
    <%
        for (int i = 0; i < 10; i++) {
    %>
    <!-- 2.JSP表达式 -->
    <li><%= multiple(Math.random()) %></li>
    <%
        }
    %>
</ul>
<h3>当前路径</h3>
<%=application.getRealPath("/")%>
<%=application.getRealPath(request.getRequestURL().toString())%>


</body>
</html>