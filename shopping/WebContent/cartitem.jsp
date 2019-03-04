<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>购物车</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">

</head>

<body>
	<div id="top">
		<div id="topnavi"  >
			<span>${user.userName}：</span>
			<a href="user_update.jsp">修改个人信息</a>
			<a href="cartitem.jsp">购物车</a>
			<a href="/booksshop/HistoryOrder?op=${user.userId}">订单列表</a>
			<a href="/booksshop/logout">退出登录</a>
		</div>
		<div id="toplogo"></div>
		<div id="navi">
			<div id="navitag">
				<a href="index.jsp" target="iframe">首页</a> 
				<c:forEach items="${typelist}" var="type">
				<a href="/booksshop/booksbytype?typeName=${type.typeName}">${type.typeName}</a> 
				</c:forEach>
			</div>
		</div>
	</div>
	<div id="index_main">
		<br> 购物车列表：<br> <br>
		<div id="cartitem_navi">
			<div class="area">图书ID</div>
			<div class="area">书名</div>
			<div class="area">作者</div>
			<div class="area">价格</div>
			<div class="area">出版社</div>
			<div class="area">购买数量</div>
			<div class="area">小计</div>
		</div>
		<c:forEach items="${cartList}" var="cartItem">
			<div id="cartitem">
				<div class="area">${cartItem.book.bookId}</div>
				<div class="area">${cartItem.book.bookName}</div>
				<div class="area">${cartItem.book.bookAuthor}</div>
				<div class="area">${cartItem.book.bookPrice}</div>
				<div class="area">${cartItem.book.bookPub}</div>
				<div class="area">${cartItem.bookNum}</div>
				<div class="area">${cartItem.sumCount}</div>
				<br />
			
			</div>
		</c:forEach>
		<br />
		<center>
		<a
			href="./firmOrder.jsp" >下订单</a>
		<!-- customerId=<%=session.getAttribute("customerId")%>& -->
		<br />
		<br />
		<a
			href="./loginsuccessindex.jsp" >继续购买 </a>
		</center>
	</div>
	<div id="bottom" style="background-color: #E0E8F2">Copyright © 2018-2019 三味书屋</div>
</body>
</html>
