﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
out.println("<base href=\""+basePath+"\">");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录 - 三味书屋</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">
<script type="text/javascript" src="js/login.js"></script>
</head>

		<body>
			<div id="top">
				<div id="topnavi">
					<a href="index.jsp">三味书屋欢迎您！</a>
					<a href="register.jsp">免费注册</a>
				</div>
				<div id="toplogo"></div>
			</div>
			<div id="login_main">
			${error}
				<form action="/booksshop/login" method="post" onsubmit="return logcheck()">
					用户名:
					<input type="text" id="myusn" name="username" value="">
					<br />
					<br /> 密&nbsp;码&nbsp;:&nbsp;
					<input type="password" id="mypwd" name="userpassword">
					<br />
					<br /> &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="shenfen" value="0" checked style="width: 70px;height: 35px; background-color: #B2C4D7" />用户
					<input type="radio" name="shenfen" value="1" style="width: 70px;height: 35px; background-color: #B2C4D7" />管理员
					<br />
					<br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="登陆" style="width: 70px;height: 35px; background-color: #B2C4D7"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" value="重写" style="width: 70px;height: 35px; background-color: #B2C4D7">
				</form>
			</div>
			<div id="bottom" style="background-color: #E0E8F2">
				Copyright @ 2018-2019 三味书屋
			</div>
		</body>

		</html>