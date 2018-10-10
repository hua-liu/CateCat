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
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<section class="section-home home-2"> <section
		class="main-slider">
	<div class="slider-pro full-width-slider" id="main-slider"
		data-width="100%" data-height="570" data-fade="true"
		data-buttons="true" data-wait-for-layers="true"
		data-thumbnail-pointer="false" data-touch-swipe="false"
		data-autoplay="true" data-auto-scale-layers="true"
		data-visible-size="100%" data-force-size="fullWidth"
		data-autoplay-delay="5000">
		<div class="sp-slides">
			<s:iterator value="#application.specialCate">
				<div class="sp-slide">
					<img class="sp-image" src="img_requestImg?id=${img.id}&type=max"
						data-src="img_requestImg?id=${img.id}&type=max"
						data-retina="img_requestImg?id=${img.id}&type=max"
						alt="${cate.name}" />
					<div class="sp-layer slider-discount" data-horizontal="18.6%"
						data-vertical="22.5%" data-show-transition="left"
						data-hide-transition="up" data-show-delay="400"
						data-hide-delay="200">
						<span>限时9折</span>
					</div>
					<div class="sp-layer slide-tex-1" data-horizontal="18.6%"
						data-vertical="33.5%" data-show-transition="left"
						data-hide-transition="up" data-show-delay="600"
						data-hide-delay="100">
						<span><br> <span class="border-line"
							style="color:#EEE">${cate.name}</span></span>
					</div>
					<div class="sp-layer slider-text-2" data-horizontal="18.7%"
						data-vertical="54.4%" data-show-transition="left"
						data-hide-transition="up" data-show-delay="800" style="color:#EEE">
						${decript}</div>
					<div class="sp-layer" data-horizontal="18.9%" data-vertical="68.7%"
						data-show-transition="left" data-hide-transition="up"
						data-show-delay="1000">
						<button data-id="${cate.id }" class="btn btn-default-color1 btn-sm add-cart" type="button">加入美食车</button>
					</div>
				</div>
			</s:iterator>

		</div>
	</div>
	</section> <%-- <div class="b-prom-offers">
		<div class="container">
			<div class="row">
				<s:iterator value="#application.recCate" begin="0" end="1">
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="width:555px;height:330px;overflow:hidden">
					<a href="#" class="wow fadeInLeft"> <img
						src="img_requestCateImg?id=${id}"
						class="img-responsive" alt="/">
					</a>
				</div>
				</s:iterator>
				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
					<a href="#" class="wow fadeInRight"> <img
						src="media/offers/promotion-offers/prom3-2.jpg"
						class="img-responsive" alt="/">
					</a>
				</div>
			</div>
		</div>
	</div>
	<div class="b-3offer-row">
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 wow bounceInUp">
					<a href="#"> <img src="media/offers/offers-row/rowb-1.jpg"
						class="img-responsive center-block" alt="/">
					</a>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 wow bounceInUp">
					<a href="#"> <img src="media/offers/offers-row/rowb-2.jpg"
						class="img-responsive center-block" alt="/">
					</a>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 wow bounceInUp">
					<a href="#"> <img src="media/offers/offers-row/rowb-3.jpg"
						class="img-responsive center-block" alt="/">
					</a>
				</div>
			</div>
		</div>
	</div>
	 --%>
	<div class="b-hr">
		<hr>
	</div>
	<div class="b-bestsellers">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<h3 class="heading-line-long">最热门美食</h3>
				</div>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="bestsellers">
						<div class="row">
							<div class="b-related">
								<s:iterator value="#application.activeCate">
									<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 wow fadeInUp">
										<div class="b-item-card">
											<div class="image">
												<a href="javascript:void(0)"> <img
													src="img_requestCateImg?type=cate&id=${id}"
													class="img-responsive center-block" alt="/">
												</a>
												<div class="image-add-mod" data-id="${id}">
													<div class="add-description">
														<div>
															<span>${about}</span> <br> <a
																href="javascript:void(0)" title="${name}"
																class="btn btn-lightbox btn-default-color1 btn-sm">
																<i class="fa fa-search-plus fa-lg"></i>
															</a>
														</div>

													</div>
												</div>
											</div>
											<div class="card-info">
												<div class="caption">
													<p class="name-item">
														<a class="product-name" href="cate-details?id=${id}">${name }</a>
													</p>
													<span class="product-price">￥${shopPrice }</span>
												</div>
												<div class="add-buttons">
													<button data-id="${id}" type="button" class="btn btn-add btn-add-fav add-fav">
														<i class="fa fa-heart-o"></i>
													</button>
													<button data-id="${id}" type="button" class="btn btn-add btn-add-cart add-cart">
														<i class="fa fa-shopping-cart"></i>
													</button>
												</div>
											</div>
										</div>
									</div>
								</s:iterator>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="b-featured b-hot-deal-mod">
		<div class="container">
			<div class="row">
				<div class="col-sm-8">
					<div class="row">
						<div class="hot-deal-mod-item clearfix">
							<div>
								<div class="col-sm-5">
									<div class="image">
										<img
											src="img_requestCateImg?id=${countdownCate.id}&type=countdown"
											class="img_requestCateImg?id=${countdownCate.id}&type=countdown"
											alt="/">
									</div>
								</div>
								<div class="col-sm-7">
									<div class="detail-info">
										<div class="card-info">
											<div class="caption">
												<div class="name-item">${countdownCate.name}</div>
												<div class="rating heading-line">
													<span class="star"><i class="fa fa-star"></i></span> <span
														class="star"><i class="fa fa-star"></i></span> <span
														class="star"><i class="fa fa-star"></i></span> <span
														class="star"><i class="fa fa-star"></i></span> <span
														class="star star-empty"><i class="fa fa-star-o"></i></span>
												</div>
												<div class="product-description">
													<p>${countdownCate.introduce}</p>
													<a href="cate-details?id=${countdownCate.id}">阅读评论</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-4">
					<div class="b-hot-deal wow fadeInRight">
						<div class="hot-deal-card">
							<div class="countdown" data-end-date="23:59:00"
								data-theme="custom" data-title-days="天" data-title-hours="小时"
								data-title-minutes="分钟" data-title-seconds="秒"></div>
							<div class="card-info">
								<div class="caption">
									<div class="deal-prices clearfix">
										<div class="deal pull-left">
											<span>交易价格</span> <br> <span class="product-price">￥${countdownCate.marketPrice}</span>
										</div>
										<div class="regular pull-right">
											<span>市场价格</span> <br> <span class="product-price-old">￥${countdownCate.shopPrice}</span>
										</div>
									</div>
								</div>
								<div class="cart-add-buttons">
									<button data-id="${countdownCate.id}" id="add-cart1" type="button"
										class="btn btn-add-cart-full add-cart">
										<span><i class="fa fa-shopping-cart"></i></span>添加到美食车
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="b-latest-mod">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<h3 class="heading-line-long">最新上架美食</h3>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="row">
						<div class="latest-mod-wrapper enable-owl-carousel"
							data-loop="false" data-auto-width="false" data-dots="false"
							data-nav="false" data-margin="0" data-responsive-class="true"
							data-responsive='{"0":{"items":1},"991":{"items":1},"992":{"items":2}}'
							data-slider-next=".latest-mod-next"
							data-slider-prev=".latest-mod-prev">
							<s:iterator value="#application.newCate">
								<div class="latest-mod-item">
									<div>
										<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
											<div class="image">
												<ul class="latest-mod-imgs enable-bx-slider"
													data-pager-custom="null" data-controls="false"
													data-min-slides="1" data-max-slides="1"
													data-slide-width="0" data-slide-margin="0"
													data-pager="true" data-mode="horizontal"
													data-infinite-loop="false">
													<s:iterator value="images">
														<li style="width:253;height:250px;"><a
															href="javascript:void(0)"> <img
																src="img_requestImg?id=${id}&type=cate" alt="/">
														</a></li>
													</s:iterator>
												</ul>
											</div>
										</div>
										<div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
											<div class="detail-info">
												<div class="card-info">
													<div class="caption">
														<div class="name-item heading-line">${name}</div>
														<div class="add-description">
															<ul class="list-unstyled">
																<li>简述：<span>${about }</span></li>
															</ul>
														</div>
														<div class="caption">
															<div class="deal-prices clearfix">
																<div class="deal pull-left">
																	<span>交易价格</span> <br> <span class="product-price">￥${countdownCate.marketPrice}</span>
																</div>
																<div class="regular pull-right">
																	<span>市场价格</span> <br> <span
																		class="product-price-old">￥${countdownCate.shopPrice}</span>
																</div>
															</div>
														</div>
														<a href="cate-details?id=${id}"
															class="btn btn-default-color1 btn-sm">了解详情</a>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</s:iterator>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- <div class="b-filter-smart container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<h3 class="heading-line-long">推荐套餐</h3>
			</div> -->
			<!-- <div class="filter-smart-item col-xs-12 col-sm-4 wow fadeInLeft">
				<div class="image">
					<div class="hold">
						<img src="media/filter-smart/smart1.jpg"
							class="img-responsive center-block" alt="/">
						<div class="image-add">
							<a href="blog-post.html" class="btn btn-default-color1 btn-sm">view
								all</a>
						</div>
					</div>
					<div class="smart-caption">
						<p>最好的预约套餐</p>
					</div>
				</div>
			</div>
			<div class="filter-smart-item col-xs-12 col-sm-4">
				<div class="image">
					<div class="hold">
						<img src="media/filter-smart/smart2.jpg"
							class="img-responsive center-block" alt="/">
						<div class="image-add">
							<a href="blog-post.html" class="btn btn-default-color1 btn-sm">查看详情</a>
						</div>
					</div>
					<div class="smart-caption">
						<p>最好的预约套餐</p>
					</div>
				</div>
			</div>
			<div class="filter-smart-item col-xs-12 col-sm-4 wow fadeInRight">
				<div class="image">
					<div class="hold">
						<img src="media/filter-smart/smart3.jpg"
							class="img-responsive center-block" alt="/">
						<div class="image-add">
							<a href="blog-post.html" class="btn btn-default-color1 btn-sm">查看详情</a>
						</div>
					</div>
					<div class="smart-caption">
						<p>最好的预约套餐</p>
					</div>
				</div>
			</div> -->
	<!-- 	</div>
	</div> -->
	<div class="b-editor-choice">
		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<h3 class="heading-line-long">本店推荐</h3>
				</div>
				<div class="col-sm-12">
					<div class="row">
						<div class="b-related">
							<s:iterator value="#application.recCate">
								<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 wow fadeInUp">
									<div class="b-item-card wow fadeInUp">
										<div class="image">
											<a href="javascript:void(0)"> <img
												src="img_requestCateImg?id=${id}&type=cate"
												class="img-responsive center-block" alt="/">
											</a>
											<div class="image-add-mod" data-id="${id}">
												<div class="add-description">
													<div>
														<span>${about}</span> <br> <a
															href="javascript:void(0)"
															class="btn btn-lightbox btn-default-color1 btn-sm"> <i
															class="fa fa-search-plus fa-lg"></i>
														</a>
													</div>

												</div>
											</div>
										</div>
										<div class="card-info">
											<div class="caption">
												<p class="name-item">
													<a class="product-name" href="cate-details?id=${id}">${name }</a>
												</p>
												<span class="product-price">￥${shopPrice }</span>
											</div>
											<div class="add-buttons">
												<button data-id="${id}" type="button" class="btn btn-add btn-add-fav add-fav">
													<i class="fa fa-heart-o"></i>
												</button>
												<button data-id="${id}" type="button" class="btn btn-add btn-add-cart add-cart">
													<i class="fa fa-shopping-cart"></i>
												</button>
											</div>
										</div>
									</div>
								</div>

							</s:iterator>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="b-hr-mod">
		<hr>
	</div>
	</section>
	<div class="modal fade" id="showPic" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="border-radius:10px">
				<div class="modal-body" style="padding:0;">
					<img id="img" width="100%" />
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp"></jsp:include>
	<script type="text/javascript">
$(function(){
	$(".image-add-mod").unbind("click");
	$(".image-add-mod").bind("click",function(){
		console.log(this)
		$("#img").attr("src","img_requestCateImg?type=max&id="+$(this).attr("data-id"));
		$('#showPic').modal('show');
	})
	addCartItem();
	addFavItem();
})
//添加到购物车
function addCartItem(){
	$(".add-cart").unbind();
	$(".add-cart").bind("click",function(){
		$.post("json/cart_addCart","cateId="+$(this).attr("data-id")+"&count=1",function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				swal("Good!", "成功添加到购物车！", "success");
			}else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
	});
}
//添加到我的收藏
function addFavItem(){
	$(".add-fav").unbind();
	$(".add-fav").bind("click",function(){
		$.post("json/fav_addFav","cateId="+$(this).attr("data-id")+"&count=1",function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result == true){
				swal("Good!", "成功添加到我的收藏！", "success");
			}else if(data.result == 'warning'){
				swal("OMG","请勿重复添加","warning");
			}	
			else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
	})
}
</script>
</body>

</html>