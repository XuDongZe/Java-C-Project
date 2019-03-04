<%@ page language="java" contentType="text/html; charset=UTF-8"
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

<title>admin_add.html</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/login.js"></script>

	<link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>
	 <center><font size="7" color="#5E5CA7">请输入要添加的管理员用户信息</font></center>
	<br />
	<br />
	<center>
	<form  id="addadmin_form" action="/booksshop/addadmins" onsubmit="return judgenull2()">
		用户名：&nbsp;&nbsp;&nbsp;<input type="text" name="adminname"
			value="" ><br /> 
		密码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="adminpassword"><br />
		确认密码：<input type="password" name="adminpassword2"><br /> 
		<br />
		<input type="submit" value="添加" style="width: 70px;height: 35px; background-color: #B2C4D7">
	</form>
	</center>
</body>
</html>