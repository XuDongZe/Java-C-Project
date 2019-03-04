<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>确认订单</title>
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
		<div id="topnavi" style="text-align: right;padding-right: 5%;" >
			<span>${user.userName}：</span>
			<a href="user_update.jsp">修改个人信息</a>
			<a href="cartitem.jsp">购物车</a>
			<a href="/booksshop/HistoryOrder?op=${user.userId}">订单列表</a>
			<a href="/booksshop/logout">退出登录</a>
		</div>
		<div id="toplogo"></div>
		<div id="navi">
			<div id="navitag">
				<a href="index.jsp">首页</a>
				<c:forEach items="${typelist}" var="type">
				<a href="/booksshop/booksbytype?typeName=${type.typeName}">${type.typeName}</a> 
				</c:forEach>
			</div>
		</div>
	</div>
	<div id="index_main">
		<br> 订单列表：<br> <br>
		<div style="margin-left:20%">
		<table>
		<c:forEach items="${cartList}" var="cartItem">
			<tr style="height:200px">
				<td style="width:180px">
					<div id="index_bookimage">
						<a href="/booksshop/bookdetails?op=${cartItem.book.bookId}" target="_blank">
						<img alt="#无法加载此图片" src="${cartItem.book.bookImage}" style="width: 120px;height: 160px"/>
						</a>
					</div>
				</td>
				<td style="width:200px">
					<div>
						书&nbsp;&nbsp;名：${cartItem.book.bookName}<br><br>
						作&nbsp;&nbsp;者：${cartItem.book.bookAuthor}<br><br>
						出版社：${cartItem.book.bookPub}<br><br>
						价&nbsp;&nbsp;格：${cartItem.book.bookPrice}元<br>
					</div>
				</td>
				<td>
					<div>
						付款人ID：	${user.userId}<br><br>
						付款人姓名：	${user.userName}<br><br>
						总金额：		￥ ${cartItem.sumCount}<br><br>
						结算日期：	${cartItem.date}
					</div>
				</td>
			<tr>
		</c:forEach>
		</table>
		</div>
		<center>
		<a
			href="./doorder" > 下订单</a>
		</center>

	</div>
	<div id="bottom" style="background-color: #E0E8F2">Copyright © 2018-2019 三味书屋</div>
</body>
</html>
