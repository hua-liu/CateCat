<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

	<section class="section-mywishlist">
	<div class="b-page-header">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 clearfix">
					<h3 class="page-title pull-left">我的收藏</h3>
					<div class="b-breadcrumbs pull-right">
						<ul class="list-unstyled">
							<li><a href="#">主页</a></li>
							<li><span>我的收藏</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-sm-12 text-right">
				<button class="btn btn-default-color1 btn-sm btn-clear"
					type="button">清空收藏</button>
			</div>
			<div class="col-sm-12 mywishlist-container">
				<div class="row">
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/huaew-mate.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">Huawei Mate S</a>
									</p>
									<span class="product-price">$280.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/xperia.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">SONY XPERIA Z5</a>
									</p>
									<span class="product-price">$550.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/mi41.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">Xiaomi Mi 4i</a>
									</p>
									<span class="product-price">$350.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/g84g.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">HUAWEI G8 4G</a>
									</p>
									<span class="product-price">$335.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/RoseGold.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">iPhone Rose Gold</a>
									</p>
									<span class="product-price">$280.00</span> <span
										class="product-price-old">$649.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/g84g.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">HUAWEI G8 4G</a>
									</p>
									<span class="product-price">$335.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/core-prime.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">Galaxy Core Prime </a>
									</p>
									<span class="product-price">$399.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
						<div class="b-item-card wow fadeInUp">
							<div class="image">
								<a href="#"> <img src="media/item-card-media/6s.jpg"
									class="img-responsive center-block" alt="/">
								</a>
								<div class="image-add">
									<button class="btn btn-default-color1 btn-sm" type="button">从收藏中删除</button>
								</div>
							</div>
							<div class="card-info">
								<div class="caption">
									<p class="name-item">
										<a class="product-name" href="#">Apple iPhone 6S</a>
									</p>
									<span class="product-price">$550.00</span>
								</div>
								<div class="add-buttons">
									<button type="button" class="btn btn-add btn-add-remove">
										<i class="fa fa-trash"></i>
									</button>
									<button type="button" class="btn btn-add btn-add-cart">
										<i class="fa fa-shopping-cart"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-12 text-center">
				<nav class="pagination-full clearfix">
				<ul class="pagination wow fadeInUp">
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li class="disabled"><a href="#">...</a></li>
					<li><a href="#">26</a></li>
				</ul>
				<ul class="pagination pagination-add">
					<li><a href="#" aria-label="Previous">上一页</a></li>
					<li><a href="#" aria-label="Next">下一页</a></li>
				</ul>
				</nav>
			</div>
		</div>
	</div>
	<div class="b-hr">
		<hr>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div class="b-features-wrapper wow fadeInUp">
					<div class="b-store-features clearfix">
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-thumbs-up"></i>
								</div>
								<div class="feature-info">
									<p>完整的保修</p>
									<p>阅读保修条款</p>
								</div>
							</div>
						</div>
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-truck"></i>
								</div>
								<div class="feature-info">
									<p>快递运输</p>
									<p>当地及国际航运</p>
								</div>
							</div>
						</div>
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-commenting"></i>
								</div>
								<div class="feature-info">
									<p>在线评论</p>
									<p>评论你最喜欢的食物</p>
								</div>
							</div>
						</div>
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-headphones"></i>
								</div>
								<div class="feature-info">
									<p>24/7 客服在线</p>
									<p>全天为你服务</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
