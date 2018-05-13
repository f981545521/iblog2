<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = "//" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Smart Error Page Responsive, Login Form Web Template, Flat Pricing Tables, Flat Drop-Downs, Sign-Up Web Templates, Flat Web Templates, Login Sign-up Responsive Web Template, Smartphone Compatible Web Template, Free Web Designs for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!-- font files -->
    <link href="http://fonts.googleapis.com/css?family=Raleway:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="http://fonts.googleapis.com/css?family=Poiret+One" rel="stylesheet">
    <link href="/css/404.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>
<div class="container demo-2">
    <div class="content">
        <div id="large-header" class="large-header">
            <h1 class="header-w3ls">当前访问的页面已经不知所踪！</h1>
            <p class="w3-agileits1">SORRY!</p>
            <canvas id="demo-canvas"></canvas>
            <img src="/images/owl.gif" alt="flashy" class="w3l">
            <h2 class="main-title"><span>404</span></h2>
            <p class="w3-agileits2">We can't seem to find the page you're looking for.</p>
            <p class="copyright">© acyou.cn  All Rights Reserved</p>
        </div>
    </div>
</div>
<!-- js files -->
<script src="/js/404.js"></script>
<!-- /js files -->
</body>
</html>
