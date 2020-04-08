<%--
  Created by IntelliJ IDEA.
  User: Wu
  Date: 2019/11/24
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>person</title>
    <script
            src="https://code.jquery.com/jquery-1.12.4.min.js"
            integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
            crossorigin="anonymous"></script>
    <script src="js/getTime.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/getParameter.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <style>
        .body {
            text-align: center;
            margin: 0 auto;
        }

        body {
            background-image: url('img/2.jpg');
        }
    </style>
</head>
<body>
<%-- 导航栏 --%>
<div id="nav"></div>

<%--右侧：所有博客文章标题信息--%>
<c:forEach items="${sessionScope.userArticle}" var="str" varStatus="s">
    <%--  文章体  --%>
    <div class="body list-group" style="background: pink;width: 60%;">
        <a href="writeBlog.html?aid=${str.aid}" style="float: right;z-index: 9999" class="list-group-item">修改</a>
        <a href="showArticle.html?aid=${str.aid}" class="list-group-item">
            <div>
                    <%--    显示标题    --%>
                    ${str.title} &nbsp;&nbsp;
                    <%--    显示时间    --%>
                    ${str.time}<br>
            </div>
        </a>
    </div>
</c:forEach>
<div id="webInfo"></div>
</body>
</html>

<%-- 引入导航栏 --%>
<script src="js/include.js"></script>
