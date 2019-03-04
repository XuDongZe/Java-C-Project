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
		<title>三味书屋</title>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="styles.css">

</head>
	<!-- List<OrderDetail> orderDetails = new ArrayList<OrderDetail>(); -->

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

			订单详情列表：
			<br>
			<br>
			<!--js处理-->
			<div id="cartitem_navi_od">
				<div class="area">图书订单号</div>
				<div class="area">图书名称</div>
				<div class="area">数量</div>
				<div class="area">单价</div>
				<div class="area">总价格</div>
				
			</div>
		
		<c:forEach items="${OrderDetail}" var="bookorder" >
		<div id="cartitem">
			<div class="area">${bookorder.bookorderId}</div>
			<div class="area">${bookorder.book.bookName}</div>
			<div class="area">${bookorder.bookNum}</div>
			<div class="area">${bookorder.book.bookPrice}</div>
			<div class="area">${bookorder.bookNum*bookorder.book.bookPrice}</div>
		</div>
		<br/>
		</c:forEach>
		</div>
		<div id="bottom" style="background-color: #E0E8F2">
			Copyright © 2018-2019 三味书屋
		</div>

	</body>
</html>
