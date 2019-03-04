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
		<!---->

		<title>后台管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="styles.css">

	</head>

	<body>
		<div id="admin_top">
			<a href="admin_iframe.html"></a>
		</div>
		<div id="admin_main">
			<div id="admin_left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="admin_leftspan" style="color: #5E5CA7">欢迎管理员:${admin.adminName}</span>
				<br />
				<ul class="live">
					<li>
						<b>书籍管理</b>
					</li>
					<ul class="live">
						<li>
							<a href="/booksshop/showallbooks" target="iframe" >查看书籍</a>
						</li>
						<li>
							<a href="add_books.jsp" target="iframe">添加书籍</a>
						</li>
						<li>
							<a href="update_books.jsp" target="iframe">修改书籍</a>
						</li>
						<li>
							<a href="delete_books.jsp" target="iframe">删除书籍</a>
						</li>
					</ul>
					<li>
						<b>用户管理</b>
					</li>
					<ul class="live">
						<li>
							<a href="/booksshop/userinfo" target="iframe">查看用户</a>
						</li>
						<li>
							<a href="add_users.jsp" target="iframe">添加用户</a>
						</li>
						<li>
							<a href="fali.jsp" target="iframe">修改用户</a>
						</li>
						<li>
							<a href="user_delete.jsp" target="iframe">删除用户</a>
						</li>
					</ul>
					<li>
						<b>系统管理</b>
					</li>
					<ul class="live">
						<li>
							<a href="/booksshop/showalladmins" target="iframe">查看管理员</a>
						</li>
						<li>
							<a href="adminadd.jsp" target="iframe">添加管理员</a>
						</li>
						<li>
							<a href="admin_modify.jsp" target="iframe">修改管理员</a>
						</li>
						<li>
							<a href="admin_delete.jsp" target="iframe">删除管理员</a>
						</li>
					</ul>
				</ul>
			</div>
			<div id="admin_right">
				<iframe id="admin_iframe" src="admin_iframe.html" name="iframe"></iframe>
			</div>

	<div id="bottom" style="margin-top:4%">
		Copyright @ 2018-2019 三味书屋
	</div>
		</div>

	</body>
</html>
