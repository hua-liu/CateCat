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
<link rel="shortcut icon" href="favicon.ico">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/master.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/jquery-ui.min.css" rel="stylesheet">
<link href="css/theme.css" rel="stylesheet">
<link href="css/typography.css" rel="stylesheet">
<link href="css/responsive.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/hover-min.css" rel="stylesheet">
<link href="css/main-slider.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<!-- 省级联动的CSS -->
<link href="css/linkage.css" rel="stylesheet">
<!-- 订单详情的CSS -->
<link href="css/ordersList/common.css" rel="stylesheet">
<link href="fonts/elegant_font/style.css" rel="stylesheet">
<link href="assets/owl-carousel/owl.carousel.css" rel="stylesheet">
<link href="assets/owl-carousel/owl.theme.default.css" rel="stylesheet">
<link href="assets/bootstrap-select/bootstrap-select.min.css" rel="stylesheet">
<link href="assets/bxslider/jquery.bxslider.css" rel="stylesheet">
<link href="assets/slider-pro-master/css/slider-pro.min.css" rel="stylesheet">
<link href="assets/flag-icon.css" rel="stylesheet">
<link href="assets/switcher/css/color1.css" rel="stylesheet">
<link href="assets/countdown/dscountdown.css" rel="stylesheet">
<!-- <link href="assets/prettyPhoto/css/prettyPhoto.css" rel="stylesheet"> -->
<link rel="stylesheet" id="switcher-css" type="text/css" href="assets/switcher/css/switcher.css" media="all" />
<link rel="alternate stylesheet" type="text/css" href="assets/switcher/css/color1.css" title="color1" media="all" />
<link rel="alternate stylesheet" type="text/css" href="assets/switcher/css/color2.css" title="color2" media="all" />
<link rel="alternate stylesheet" type="text/css" href="assets/switcher/css/color3.css" title="color3" media="all" />
<link rel="alternate stylesheet" type="text/css" href="assets/switcher/css/color4.css" title="color4" media="all" />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<!-- Loader -->
	<div id="page-preloader">
		<span class="spinner"></span>
	</div>
	<!-- Loader end -->
	<div class="b-page">
		<!-- Start Switcher -->
		<div class="switcher-wrapper">
			<div class="demo_changer">
				<div class="demo-icon customBgColor">
					<i class="fa fa-cog fa-spin fa-2x"></i>
				</div>
				<div class="form_holder">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="predefined_styles">
								<div class="skin-theme-switcher">
									<h4>主题颜色</h4>
									<a href="#" data-switchcolor="color1" class="styleswitch"
										style="background-color:#e24545;"> </a> <a href="#"
										data-switchcolor="color2" class="styleswitch"
										style="background-color:#0072bc;"> </a> <a href="#"
										data-switchcolor="color3" class="styleswitch"
										style="background-color:#ff9600;"> </a> <a href="#"
										data-switchcolor="color4" class="styleswitch"
										style="background-color:#01a664;"> </a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- End Switcher -->
		<header>
		<div class="b-header-main style-2">
			<div class="container-fluid">
				<div class="row">
					<div class="col-xs-6 col-sm-2 col-md-2 col-lg-2">
						<div class="b-logo">
							<a href="index"> <span>CateCat</span> <br> <span>love</span>
							</a>
						</div>
					</div>
					<div id="toggle-nav"
						class="col-xs-12 col-sm-10 col-md-8 col-lg-8 menu-wrapper clearfix">
						<div class="toggle-nav-btn">
							<button class="btn btn-menu">
								<i class="fa fa-bars fa-lg"></i>
							</button>
						</div>
						<div class="b-header-menu pull-right">
							<ul class="list-inline">
								<li><a class="heading-line" href="index">首页</a></li>
								<li id="all-menu-open"><a class="heading-line"
									href="list">所有菜系 <i
										class="fa fa-angle-down"></i></a>
									<div id="b-all-menu" class="hidden-xs">
										<div class="row">
											<div class="clearfix">
												<div class="all-menu-holder col-sm-12">
													<div class="row">
														<div class="all-menu-content clearfix">
															<div class="col-sm-4 col-md-6 col-lg-4">
																<div class="all-menu-offer">
																	<a href="cate-details?id=${application.recCate[2].id }"><img src="img_requestCateImg?type=cate&id=${application.recCate[2].id }"
																		class="img-responsive" alt="/"></a>
																</div>
															</div>
															<div class="col-sm-8 col-md-6 col-lg-8">
																<div class="all-menu-list">
																	<div class="row">
																		<div class="all-menu-item-holder col-sm-4">
																			<div class="all-menu-item">
																				<div class="all-menu-title">特色菜系</div>
																				<ul class="list-unstyled">
																					<s:iterator value="#application.categorys" status="c1">
																						<s:if test="name=='类别'">
																						<s:iterator value="childs" status="c2">
																							<li><a href="list?category=${id}">${name }</a></li>
																						</s:iterator>
																						</s:if>
																					</s:iterator>
																				</ul>
																			</div>
																		</div>
																		<div class="all-menu-item-holder col-sm-4">
																			<div class="all-menu-item">
																				<div class="all-menu-title">热门菜</div>
																				<ul class="list-unstyled">
																				<s:iterator value="#application.activeCate">
																					<li><a href="cate-details?id=${id}">${name }</a></li>
																				</s:iterator>
																				</ul>
																			</div>
																		</div>
																		<div class="all-menu-item-holder col-sm-4">
																			<div class="all-menu-item">
																				<div class="all-menu-title">促销活动</div>
																				<ul class="list-unstyled">
																					<li><a href="list?sidx=onLineTime">最新菜系</a></li>
																					<li><a href="list?sidx=log.buyCount">最热菜系</a></li>
																					<li><a href="list?sidx=log.grade">评价最高菜系</a></li>
																				</ul>
																			</div>
																			<div class="all-menu-item">
																				<div class="all-menu-offer">
																					<a href="cate-details?id=${application.recCate[1].id }"><img
																						src="img_requestCateImg?id=${application.recCate[1].id }"
																						class="img-responsive center-block" alt="/"></a>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div></li>
								<li><a class="heading-line" href="orderLayout">预约订座</a></li>
								<!-- <li><a class="heading-line" href="blog-main.html">论坛</a></li> -->
								<li><a class="heading-line" href="javascript:void(0)">联系我们</a></li>
								<s:if test="#session.user == null">
									<li><a class="heading-line" href="login">登陆</a></li>
								</s:if>
								<s:else>
									<li><a class="heading-line" href="javascript:void(0)"><img src="img_requestImg?type=avatar&id=<s:property value="#session.user.avatar.id"/>" class="img-circle" style="width:40px;height:40px;border-radius:20px !important"/></a>
									<div class="b-all-homes">
										<ul class="list-unstyled">
											<li><a href="#" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><i class="glyphicon glyphicon-user"></i><s:property value="#session.user.username"/></a></li>
											<li><a href="favlist.action"><i class="fa fa-heart"></i>我的收藏</a></li>
											<li><a href="ordersList_myOrdersPage?page=1"><i class="fa fa-shopping-cart"></i>我的订单</a></li>
											<li><a href="user_quit.action"><i class="fa fa-arrow-left"></i>退出</a></li>
										</ul>
									</div></li>
								</s:else>
								<li class="search"><a id="search-open" class="iconSearch"
									href="#"><i class="fa fa-search"></i></a>
									<div id="search">
										<form action="#" method="post">
											<div class="form-group">
												<input id="searchQuery" type="search" placeholder="关键字搜索本店">
											</div>
										</form>
									</div></li>
							</ul>
						</div>
					</div>
					<div id="cart-wrapper" class="col-xs-6 col-sm-12 col-md-2 col-lg-2">
						<div class="b-cart pull-right">
							<s:if test="#session.user != null">
								<s:if test="#session.cart.orderItems.size() != 0">
									<button id="cart" class="btn btn-default-color1 btn-sm">
										<span class="price"><i class="fa fa-shopping-bag"></i> $
											${session.cart.total }</span> <span class="counter-wrapper"><span
											class="counter">${session.cart.cartItemCount }</span></span>
									</button>
								</s:if>
								<s:else>
									<button id="cart" class="btn btn-default-color1 btn-sm">
										<span class="price"><i class="fa fa-shopping-bag"></i> $
											0.00</span> <span class="counter-wrapper"><span
											class="counter">0</span></span>
									</button>
								</s:else>
							</s:if>
							<s:else>
								<button id="cart" class="btn btn-default-color1 btn-sm">
									<span class="price"><i class="fa fa-shopping-bag"></i> $
										0.00</span> <span class="counter-wrapper"><span
										class="counter">0</span></span>
								</button>
							</s:else>
							<div class="cart-products">
								<div class="c-holder">
									<h3 class="title" style="margin-top:0">我的购物车</h3>
									<s:if test="#session.user != null">
										<s:if test="#session.cart.orderItems.size() != 0">		
											<ul class="products-list list-unstyled" style="height:200px;overflow:auto;">
												<s:iterator var="cartItem" value="#session.cart.orderItems">
												<li>
													<div class="b-cart-table">
														<a href="javascript:void(0)" class="image"> <img style="width:50px;height:50px;"
														src="img_requestCateImg?type=max&id=${cate.id}" alt="/">
														</a>
														<div class="caption">
															<a class="product-name" href="cate-details?id=${cate.id}">${name}</a>
															<span class="product-count smailCart-count" data-num="${count }">${count}</span>x<span class="product-price">$${price}</span>
															<div class="rating">
																<span class="star"><i class="fa fa-star"></i></span> <span
																	class="star"><i class="fa fa-star"></i></span> <span
																	class="star"><i class="fa fa-star"></i></span> <span
																	class="star"><i class="fa fa-star"></i></span> <span
																	class="star star-empty"><i class="fa fa-star-o"></i></span>
															</div>
														</div>
														<button data-id="${id}" class="btn btn-remove headRemove-cartItem">
															<i class="fa fa-trash fa-lg"></i>
														</button>
													</div>
												</li>
												</s:iterator>
												<li>
													<div class="products-subtotal text-right">
														合计 <span class="subtotal-price">$${session.cart.total}</span>
													</div>
												</li>
											</ul>
											<div class="products-buttons text-center">
												<button type="button" class="btn btn-default btn-sm goto-cart">前往购物车</button>
												<button data-id="${session.cart.id}" type="button" class="btn btn-success btn-sm goto-ByOrders">结账</button>
											</div>
										</s:if>
										<s:else>
											<span>亲！购物车空空哒~去购物吧⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</span>
											<button type="button" class="btn btn-default btn-sm refresh-cart">刷新购物车</button>
										</s:else>
									</s:if>
									<s:else>
											<span>亲！您你还没登录哦~去登录吧⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄喵~</span>
									</s:else>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
<script type="text/javascript">
$(function(){
	$(".goto-cart").unbind();
	$(".goto-cart").bind("click",function(){
		$.post("json/cart_cartPage",function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				window.location="cart_cartToPage.action";
			}else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
	});
	gotoByOrder();
	removeCartItem();
	refreshCart();
})
//跳转到订单
function gotoByOrder(){
	$(".goto-ByOrders").unbind();
	$(".goto-ByOrders").bind("click",function(){
		$.post("json/orders_ordersPage","ordersId="+$(this).attr("data-id"),function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				window.location="orders_ordersToPage.action";
			}else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
	});
}
//删除单个购物项
function removeCartItem(){
	$(".headRemove-cartItem").unbind();
	$(".headRemove-cartItem").bind("click",function(){
		var _this = this;
		swal({ 
	        title: "您确定要删除吗？",  
	        text: "您确定要删除这件商品吗？",  
	        type: "warning", 
	        showCancelButton: true, 
	        closeOnConfirm: false, 
	        confirmButtonText: "是的，我要删除", 
	        confirmButtonColor: "#ec6c62" 
	    }, function() { 
	    	$.post("json/cart_removeCartItem","cartItemId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "已成功删除商品！", "success");
					setTimeout(function(){
						location.reload();
						},1000)
				}else{
					swal("OMG", "删除操作失败!", "error");
				}
			},"json");
	    }); 
	});
}

//刷新购物车
function refreshCart(){
	$(".refresh-cart").unbind();
	$(".refresh-cart").bind("click",function(){
		location.reload();
	});
}
</script>
</body>
</html>