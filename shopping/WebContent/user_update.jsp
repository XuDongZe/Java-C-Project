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

<title>修改用户</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">

</head>

<body>
  <div id="admin_top">
      	<div id="topnavi" style="text-align: right;padding-right: 5%;" >
			<span>${user.userName}：</span>
			<a href="user_update.jsp">修改个人信息</a>
			<a href="cartitem.jsp">购物车</a>
			<a href="order.jsp">订单列表</a>
			<a href="/booksshop/logout">退出登录</a>
		</div>
    </div>
    	<div id="navi">
			<div id="navitag">
				<a href="index.jsp" target="iframe">首页</a> 
				<c:forEach items="${typelist}" var="type">
				<a href="/booksshop/booksbytype?typeName=${type.typeName}">${type.typeName}</a> 
				</c:forEach>
			</div>
		</div>
    <div id="admin_main">
      <center><font size="7" color="#5E5CA7">用户信息修改</font></center><br/><br/>
  <center>
   <form id="updateuser_form" action="/booksshop/modifyuserinfo" method="post">
       用户密码：&nbsp;<input type="text" name="newuserpass"><br/>
       真实姓名：&nbsp;<input type="text" name="newuserrealname"><br/>
       家庭住址：&nbsp;<input type="text" name="newuseraddress"><br/>
       电话号码：&nbsp;<input type="text" name="newusertel"><br/>
       电子邮件：&nbsp;<input type="text" name="newuseremail"> <br/>
       <br />
       	<input type="hidden" name="username" value="${user.userName}">
       	<input type="hidden" name="userid" value="${user.userId}">
       <input type="submit" value="修改" style="width: 70px;height: 35px; background-color: #B2C4D7">      
   </form>
   </center>

  <div id="bottom">
    Copyright @ 2018-2019 三味书屋
  </div>
    </div>
  
</body>
</html>