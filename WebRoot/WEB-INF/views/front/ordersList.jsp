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
					<h3 class="page-title pull-left">我的订单</h3>
					<div class="b-breadcrumbs pull-right">
						<ul class="list-unstyled">
							<li><a href="index">首页</a></li>
							<li><span>我的订单</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ordersDetails ordersList-ordersDetails">
		<s:if test="#session.user != null">
		<s:if test="pageBean.list.size() != 0">
		<div class="span24">
			<table>
				<tbody>
				<s:iterator var="order" value="pageBean.list">
					<tr>
						<th colspan="5">订单编号：<span>${id}</span>&nbsp;&nbsp;&nbsp;&nbsp;
							订单金额：<font color="red">$${total }</font>
							&nbsp;&nbsp;&nbsp;&nbsp;订单状态：<font color="red">
								<s:if test="#order.status.id == '1489367760150'">
									<button data-id="${id}" class="btn btn-default-color1 btn-continue btn-sm payMent-orders" 
									style="width:60px;height:30px;border:2px solid red;padding:5px;margin-bottom:5px;">
									<font color="red">付款</font></a></button>
									<button data-id="${id}" class="btn btn-default-color1 btn-continue btn-sm cancel-orders" 
									style="width:60px;height:30px;border:2px solid red;padding:5px;margin-bottom:5px;">
									<font color="red">取消订单</font></a></button>
								</s:if>
								<s:if test="#order.status.id == '1489367817472'">
									已付款,待发货
								</s:if>
								<s:if test="#order.status.id == '1489367920065'">
									<button data-id="${id}" class="btn btn-default-color1 btn-continue btn-sm Confirm-receipt" 
									style="width:60px;height:30px;border:2px solid red;padding:5px;margin-bottom:5px;">
									<font color="red">确认收货</font></a></button>
								</s:if>
								<s:if test="#order.status.id == '1489368039310'">
									交易成功
								</s:if>
						</font>
						</th>
					</tr>
						<tr>
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<s:iterator var="orderItem" value="#order.orderItems">
						<tr>
							<td width="60">
								<img src="img_requestCateImg?type=max&id=${cate.id}"/>
							</td>
							<td>
								<a target="_blank" href="cate-details?id=${cate.id}">${name}</a>
							</td>
							<td>
								$${price}
							</td>
							<td class="quantity" width="60">
								${count}
							</td>
							<td width="140">
								<span class="subtotal">$${subtotal}</span>
							</td>
						</tr>
					</s:iterator>
					</s:iterator>
					<tr>
						<td colspan="5">
							<div class="ordersList-page">
								<span>第 <s:property value="pageBean.page"/>/<s:property value="pageBean.totalPage"/> 页</span>
								<s:if test="pageBean.page != 1">
									<a href="ordersList_myOrdersPage?page=1" class="firstPage">&nbsp;</a>
									<a href="ordersList_myOrdersPage?page=<s:property value="pageBean.page-1"/>" class="previousPage">&nbsp;</a>
								</s:if>
								<s:iterator var="i" begin="1" end="pageBean.totalPage">
									<s:if test="pageBean.page != #i">
										<a href="ordersList_myOrdersPage?page=<s:property value="#i"/>"><s:property value="#i"/></a>
									</s:if>
									<s:else>
										<span class="currentPage"><s:property value="#i"/></span>
									</s:else>
								</s:iterator>
								<s:if test="pageBean.page != pageBean.totalPage">	
									<a class="nextPage" href="ordersList_myOrdersPage?page=<s:property value="pageBean.page+1"/>">&nbsp;</a>
									<a class="lastPage" href="ordersList_myOrdersPage?page=<s:property value="pageBean.totalPage"/>">&nbsp;</a>
								</s:if>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		</s:if>
		<s:else>
			<span style="background:#F6F6F6;"><h2>亲！订单空空的哟，快去购物吧~⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</h2></span>
			<button class="btn btn-default-color1 btn-continue btn-sm goto-byCate">前去购物</button>
		</s:else>
		</s:if>
		<s:else>
			<span style="background:#F6F6F6;"><h2>亲！您你还没登录哦~去登录吧⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</h2></span>
		</s:else>
	</div>
	</section>
	<jsp:include page="foot.jsp"></jsp:include>
	<script src="js/ordersList.js"></script>
</body>
</html>