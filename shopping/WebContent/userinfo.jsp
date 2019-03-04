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

<title>查看用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">

</head>

<body>
	<center><font size="7" color="#5E5CA7">显示全部用户信息</font></center><br/><br/>
	<center>
	<table   border="1" width="1000" bgcolor="#A9C0E2" style="text-align: center;" >
		<tr>
			<td>用户ID</td>
			<td>用户名</td>
			<td>用户密码</td>
			<td>真实姓名</td>
			<td>家庭地址</td>
			<td>电话号码</td>
			<td>电子邮件</td>
		</tr>
		
		<c:forEach items="${users}" var = "user">
		<tr>
			<td>${user.userId}</td>
			<td>${user.userName}</td>
			<td>${user.userPwd}</td>
			<td>${user.userRealName}</td>
			<td>${user.userAddress}</td>
			<td>${user.userTel}</td>
			<td>${user.userEmail}</td>
		</tr>
		</c:forEach>
	</table>
	</center>
</body>
</html>
