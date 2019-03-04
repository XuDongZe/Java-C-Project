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

<title>添加书籍</title>
<script type="text/javascript" src="js/login.js"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">

</head>

<body>
   <center><font size="7" color="#5E5CA7">添加书籍: </font></center> <br />

  <center>
   <form id="addbook_form" action="/booksshop/addbooks" method ="get" onsubmit = "return judgenull()">
       图书名称：&nbsp;&nbsp;&nbsp;<input type="text" name="bookName" ><br/>
       图书作者：&nbsp;&nbsp;&nbsp;<input type="text" name="bookAuthor" ><br/>
       图书封面：&nbsp;&nbsp;&nbsp;<input type="text" name="bookPic" ><br/>
       图书价格：&nbsp;&nbsp;&nbsp;<input type="text" name="bookPrice"><br/>
       图书出版社：<input type="text" name="bookPub"><br/>
       图书简介：&nbsp;&nbsp;&nbsp;<input type="text" name="bookDesc"><br/>
       图书类别：&nbsp;&nbsp;&nbsp;<input type="text" name="typeId"><br/><br />
      <input type="submit" value="添加" style="width: 70px;height: 35px; background-color: #B2C4D7">  	   
   </form>
 </center>
</body>
</html>