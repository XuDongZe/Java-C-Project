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
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 

<title>删除用户</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">

</head>

<body>
  <center><font size="7" color="#5E5CA7">删除用户</font></center><br/><br/>
  <center>
   <form id="deleteuser_form" action="/booksshop/deleteusers">
   	   用户名称：&nbsp;&nbsp;&nbsp;<input type="text" name="userName" ><br/>
       <input type="submit" value="删除" style="width: 70px;height: 35px; background-color: #B2C4D7">  	   
   </form>
   </center>
</body>
</html>