<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>美食猫</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="favicon.ico">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<section class="section-shopping-cart">
	<div class="b-page-header">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 clearfix">
					<h3 class="page-title pull-left">订单</h3>
					<div class="b-breadcrumbs pull-right">
						<ul class="list-unstyled">
							<li><a href="index">首页</a></li>
							<li><span>订单</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
	<s:if test="#session.user != null">
			<div class="row">
				<div class="col-sm-12 cart-table wow fadeInUp">
					<div class="b-table b-cart-table table-responsive">
						<table class="table">
							<thead>
								<tr>
									<td><span>订单列表 <span class="counter">(${session.orders.cartItemCount })</span></span></td>
									<td><span>单价</span></td>
									<td><span>数量</span></td>
									<td><span>小计</span></td>
								</tr>
							</thead>
							<tbody>
								<s:iterator var="ordersItem" value="#session.orders.orderItems">
								<tr class="cartItem-tr" data-id="${id}">
									<td>
										<div class="image">
											<img style="width:100px;heigth:100px;" src="img_requestCateImg?type=max&id=${cate.id}"
												class="img-responsive img-thumbnail center-block" alt="6s">
										</div>
										<div class="caption">
											<a class="product-name" href="cate-details?id=${cate.id}">${name}</a>
										</div>
									</td>
									<td><span class="product-price">$${price}</span></td>
									<td>
										<span class="orders-count">${count}</span>
									</td>
									<td><span class="product-price total-price cart-subtotal-price" data-price="${subtotal}">$${subtotal}</span>
									</td>
								</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
					<div>
						<span style="border: 2px #DDDDDD solid;padding:12px;"><b>配送地址</b></span>
						<input type="text" id="city" name="city" placeholder="填写订单配送地址(不能为空)" class="address-city" style="border: 2px #DDDDDD solid;padding:10px;margin-top:5px;margin-left:5px;width:400px;height:40px;"/>
						<div class="clear" style="clear: left;"></div>
					</div>
					<br/>
					<div>
						<span style="border: 2px #DDDDDD solid;padding:12px;"><b>联系电话</b></span>
						<input type="text" id="contact-phone" placeholder="填写联系号码" value="${session.user.phoneNo }" style="border: 2px #DDDDDD solid;padding:10px;margin-top:5px;margin-left:5px;width:200px;height:40px;"/>
						<div class="clear" style="clear: left;"></div>
					</div>
					<br/>
					<div>
						<span style="border: 2px #DDDDDD solid;padding:12px;"><b>联系名字</b></span>
						<input type="text" id="contact-name" placeholder="填写联系名字" value="${session.user.name }" style="border: 2px #DDDDDD solid;padding:10px;margin-top:5px;margin-left:5px;width:200px;height:40px;"/>
						<div class="clear" style="clear: left;"></div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 wow fadeInLeft"></div>
				<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 wow fadeInRight">
					<div class="b-total-table clearfix">
						<table class="table table-condensed">
							<tbody>
								<tr>
									<td>订单小计</td>
									<td class="cart-total">$${session.orders.total}</td>
								</tr>
								<tr class="total">
									<td>总计</td>
									<td class="cart-total">$${session.orders.total}</td>
								</tr>
							</tbody>
						</table>
						<div>
							<button data-id="${session.orders.id}" style="float:right;margin-left:30px;margin-top:-0.5px;" class="btn btn-default-color1 btn-continue btn-sm cancel-orders">取消订单</button>
							<button data-id="${session.orders.id}" class="btn btn-primary-color2 btn-sm confirm-orders">确认订单</button>
							<div style="clear: right;" class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			</s:if>
			<s:else>
				<span style="background:#F6F6F6;"><h2>亲！您还没登录哦~去登录吧⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</h2></span>
			</s:else>
	</div>
	</section>
	<jsp:include page="foot.jsp"></jsp:include>
	<script src="js/orders.js"></script>
</body>
</html>
