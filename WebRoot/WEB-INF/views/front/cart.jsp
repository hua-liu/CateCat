<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
					<h3 class="page-title pull-left">购物车</h3>
					<div class="b-breadcrumbs pull-right">
						<ul class="list-unstyled">
							<li><a href="index">首页</a></li>
							<li><span>购物车</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
	<s:if test="#session.user != null">
		<s:if test="#session.cart.orderItems.size() != 0">
			<div class="row">
				<div class="col-sm-12 cart-table wow fadeInUp">
					<div class="b-table b-cart-table table-responsive">
						<table class="table">
							<thead>
								<tr>
									<td><span>购物车列表 <span class="counter">(${session.cart.cartItemCount })</span></span></td>
									<td><span>单价</span></td>
									<td><span>数量</span></td>
									<td><span>小计</span></td>
									<td><span>删除</span></td>
								</tr>
							</thead>
							<tbody>
								<s:iterator var="cartItem" value="#session.cart.orderItems">
								<tr class="cartItem-tr" data-id="${id}">
									<td>
										<div class="image">
											<img style="width:200px;heigth:200px;" src="img_requestCateImg?type=max&id=${cate.id}"
												class="img-responsive img-thumbnail center-block" alt="6s">
										</div>
										<div class="caption">
											<a class="product-name" href="cate-details?id=${cate.id}">${name}</a>
											<div class="product-options">
												<%-- <p class="opt-color">
													颜色: <span>rose gold</span>
												</p> --%>
												<p class="availability">
													<span>现有存货</span>
												</p>
											</div>
											<button type="button" class="btn btn-add-wish add-fav" data-id="${cate.id}">
												<i class="fa fa-heart-o"></i> 添加收藏
											</button>
										</div>
									</td>
									<td><span class="product-price">$${price}</span></td>
									<td>
										<div class="input-group btn-block qty-block"
											data-trigger="spinner">
											<a class="spinner-btn-mod" href="javascript:;"
												data-spin="down">-</a><input class="form-control priceInput" type="text"
												value="${count}" data-num="${count}" data-rule="quantity" style="width: 55px" data-id="${id}" data-price="${price}"><a
												class="spinner-btn-mod" href="javascript:;" data-spin="up">+</a>
										</div>
									</td>
									<td><span class="product-price total-price cart-subtotal-price" data-price="${subtotal}">$${subtotal}</span>
									</td>
									<td class="text-left">
										<button data-id="${id}" class="btn btn-remove remove-cartItem">
											<i class="fa fa-trash fa-lg"></i>
										</button>
									</td>
								</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
					<button data-id="${session.cart.id}" class="btn btn-default-color1 btn-continue btn-sm remove-allcartItem">清空购物车</button>
					<button class="btn btn-default-color1 btn-continue btn-sm goto-byCate">继续购物</button>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 wow fadeInLeft">
				</div>
				<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 wow fadeInRight">
					<div class="b-total-table clearfix">
						<table class="table table-condensed">
							<tbody>
								<tr>
									<td>购物车小计</td>
									<td class="cart-total">$${session.cart.total}</td>
								</tr>
								<!-- <tr>
									<td>运输+处理</td>
									<td>$50.00</td>
								</tr>
								<tr class="pre-total">
									<td>税收费用</td>
									<td>$20.00</td>
								</tr> -->
								<tr class="total">
									<td>总计</td>
									<td class="cart-total">$${session.cart.total}</td>
								</tr>
							</tbody>
						</table>
						<button data-id="${session.cart.id}" class="btn btn-primary-color2 btn-sm goto-ByOrders">结算</button>
					</div>
				</div>
			</div>
			</s:if>
			<s:else>
				<span style="background:#F6F6F6;"><h2>亲！购物车空空哒~去购物吧⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</h2></span>
				<button class="btn btn-default-color1 btn-continue btn-sm goto-byCate">前去购物</button>
			</s:else>
		</s:if>
		<s:else>
			<span style="background:#F6F6F6;"><h2>亲！您你还没登录哦~去登录吧⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</h2></span>
		</s:else>
		
	</div>
	</section>
	<jsp:include page="foot.jsp"></jsp:include>
	<script src="js/cart.js"></script>
</body>
</html>
