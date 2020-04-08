<%--
  Created by IntelliJ IDEA.
  User: Wu
  Date: 2019/11/24
  Time: 0:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="js/jquery-1.12.4.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/getTime.js"></script>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <title>welcome</title>
    <style>
        .artBody {
            text-align: center;
            margin: 0 auto;
            width: 60%;
        }

        body {
            background-image: url('img/2.jpg');
        }
    </style>
</head>
<body>
<%-- 导航栏 --%>
<div id="nav"></div>
<%--  文章体  --%>
<div class="artBody list-group"></div>
<%-- 加入网站信息 --%>
<div id="webInfo"></div>
</body>

</html>
<%-- 引入统一信息 --%>
<script src="js/include.js"></script>
<%--获得标体信息--%>
<script>
    $(function () {
        $.post("loadHtml/getAllArticle", null, function (data) {
            if (data == null){
                return;
            }

            let content = "";
            for (let i = 0; i < data.length; i++) {
                content += "\n" +
                    "    <a href=\"showArticle.html?aid=" + data[i]["aid"] + "\" class=\"body list-group-item\">\n" +
                    "        <div>\n" +
                    "                <%--    显示标题    --%>\n" +
                    "                " + data[i]["title"] + " &nbsp;&nbsp;\n" +
                    "                <%--    显示时间    --%>\n" +
                    "                " + getTime(data[i]["time"]) + " &nbsp;&nbsp;\n" +
                    "                 <%--    显示作者    --%>\n" +
                    "                作者：" + data[i]["username"] +
                    "        </div>\n" +
                    "    </a>";
            }

            $(".artBody").html(content);
        });
    })
</script>
