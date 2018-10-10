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
<link rel="stylesheet" href="css/plugins/zoom/ShopShow.css" type="text/css" />
<link rel="stylesheet" href="css/plugins/zoom/MagicZoom.css" type="text/css" />
<style type="text/css">
	#tsImgSArrR,#tsImgSArrL{
	 -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    -khtml-user-select: none;
    user-select: none;
	}
	#imgs-box{
		position:absolute;
	}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<section class="section-product-detail">
	<div class="b-page-header">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 clearfix">
					<h3 class="page-title pull-left">美食细节</h3>
					<div class="b-breadcrumbs pull-right">
						<ul class="list-unstyled">
							<li><a href="index">首页</a></li>
							<li><a href="list">类别</a></li>
							<li><span>${detail.name}</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
				<div class="lb-content lb-content-accordion">
					<s:iterator value="#application.categorys" status="c1">
						<div id="accordion${c1.count}" class="accordion-l-box wow fadeInUp enable-accordion"
							data-active="${c1.count<3?'0':'1'}" data-collapsible="true" data-height-style="content">
						<h3 class="accordion-header-mod">
							<span class="heading-line title-accordion-menu-item">${name=='工艺'?'烹饪工艺':name=='主成'?'主要成分':name=='场景'?'食用场景':name}</span> <span
								class="accordion-icon"></span>
						</h3>
						<div>
							<ul parent-id="${c1.count}" class="parent">
								<s:iterator value="childs" status="c2">
									<s:if test="#c2.count<6">
										<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="list?category=${id}"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
									</s:if>
									<s:elseif test="#c2.count==6">
										<li class="more"><span class="load-more">更多</span>
											<ul class="more-list">
											<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="list?category=${id}"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
									</s:elseif>
									<s:elseif test="#c2.last">
										<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="list?category=${id}"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
										</ul>
										</li>
									</s:elseif>
									<s:else>
										<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="list?category=${id}"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
									</s:else>
								</s:iterator>
							</ul>
						</div>
					</div>
						
					</s:iterator>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
				<div class="detail-title">
					<h3 class=heading-line>${detail.name}</h3>
					<p>${detail.about}</p>
				</div>
				<div class="detail-main">
					<div class="row">
						<div class="col-sm-6 product-image wow fadeInLeft">
							<%-- <ul class="max-image bxslider-product enable-bx-slider" data-pager-custom="#bx-pager" data-controls="false" data-min-slides="1" data-max-slides="1" data-slide-width="0" data-slide-margin="0" data-pager="true" data-mode="horizontal" data-infinite-loop="true">
								<s:iterator value="detail.images" status="s">
									<s:if test="#s.count==1">
										<li data-id="${id}"><img class="MagicZoom" id="MagicZoom" src="img_requestImg?type=max&id=${id}" alt="/"/></li>
									</s:if>
								</s:iterator>
							</ul>
							<div class="product-image-thumbs">
								<ul id="bx-pager" class="min-image pager-custom list-unstyled enable-bx-slider" data-pager-custom="null" data-controls="true" data-min-slides="4" data-max-slides="5" data-slide-width="60" data-slide-margin="5" data-pager="false" data-mode="horizontal" data-infinite-loop="false">
									<s:iterator value="detail.images">
									<li data-id="${id}"><a data-slide-index="0" href="#"><img
											src="img_requestImg?type=min&id=${id}" alt="/" /></a></li>
									</s:iterator>
								</ul>
							</div> --%>
							<div id="tsShopContainer">
							<s:iterator value="detail.images" status="c">
								<s:if test="#c.count==1">
								<div id="tsImgS"><a href="img_requestImg?type=max&id=${id}" title="Images" class="MagicZoom" id="MagicZoom"><img width="300" height="300" src="img_requestImg?type=max&id=${id}" /></a></div>
								</s:if>
							</s:iterator>
								<div id="tsPicContainer" class="row" style="position:relative;">
									<div id="tsImgSArrL" class="col-md-1"><i class="glyphicon glyphicon-chevron-left" style="font-size:30px;line-height:60px"></i></div>
									<div id="tsImgSCon" class="col-sm-10 col-md-10 col-lg-10">
										<ul id="imgs-box">
										<s:iterator value="detail.images">
											<li class="min-image" rel="MagicZoom" data-id="${id}"><img height="42" width="42" src="img_requestImg?type=max&id=${id}" tsImgS="img_requestImg?type=max&id=${id}" /></li>
										</s:iterator>
										</ul>
									</div>
									<div id="tsImgSArrR" class="col-md-1"><i class="glyphicon glyphicon-chevron-right" style="font-size:30px;line-height:60px"></i></div>
								</div>
								<img class="MagicZoomLoading" width="16" height="16" src="images/loading.gif" alt="Loading..." />
							</div>
						</div>
						<div class="col-sm-6 wow fadeInRight">
							<div class="detail-info pd1">
								<div class="card-info">
									<div class="caption">
										<div class="name-item">
											<div class="card-price-block clearfix">
												<span class="price-title">优惠价格</span> <span
													class="product-price"><i class="glyphicon glyphicon-yen"></i>${detail.marketPrice}</span> <span
													class="product-price-old"><i class="glyphicon glyphicon-yen"></i>${detail.shopPrice}</span>
											</div>
											<div class="rating">
												<span class="star"><i class="fa fa-star"></i></span> <span
													class="star"><i class="fa fa-star"></i></span> <span
													class="star"><i class="fa fa-star"></i></span> <span
													class="star"><i class="fa fa-star"></i></span> <span
													class="star star-empty"><i class="fa fa-star-o"></i></span>
												<%-- <div class="add-review">
													<span><span class="review-counter">4</span>星评价</span> <a
														href="product-details.html">添加你的评论</a>
												</div> --%>
											</div>
										</div>
										<div class="product-description">
											<h6 class="heading-line">所属菜系</h6>
											<s:iterator value="detail.categoryName" var="val">
												<span class="category-span">${val}</span>
											</s:iterator>
										</div>
										<div class="product-description">
											<h6 class="heading-line">主要食材</h6>
											<s:iterator value="detail.materialName" var="val">
												<span class="category-span">${val}</span>
											</s:iterator>
										</div>
										<div class="product-description">
											<h6 class="heading-line">美食口味</h6>
											<s:iterator value="detail.tasteName" var="val">
												<span class="category-span">${val}</span>
											</s:iterator>
										</div>
										<div class="product-description">
											<h6 class="heading-line">食用功效</h6>
											<s:iterator value="detail.effectName" var="val">
												<span class="category-span">${val}</span>
											</s:iterator>
										</div>
									</div>
								</div>
								<div class="detail-qty-color">
									<div class="input-group spinner" data-trigger="spinner">
										<h6 class="heading-line">数量</h6>
										<input type="text" id="CateCount" data-rule="quantity" value="1">
										<div class="spinner-btn">
											<a class="btn btn-default" href="javascript:;" data-spin="up"><i
												class="fa fa-chevron-up"></i></a> <a class="btn btn-default"
												href="javascript:;" data-spin="down"><i
												class="fa fa-chevron-down"></i></a>
										</div>
									</div>
								</div>
								<div class="detail-buttons clearfix">
									<div class="add-buttons">
										<!-- <button type="button" class="btn btn-add btn-add-compare">
											<i class="fa fa-refresh"></i>
										</button> -->
										<button type="button" class="btn btn-add btn-add-fav add-fav" data-id="${id}">
											<i class="fa fa-heart-o"></i>
										</button>
									</div>
									<div class="cart-add-buttons">
										<button type="button" class="btn btn-cart-color2 add-cart" data-id="${id}">
											<i class="fa fa-shopping-cart fa-lg"></i> 添加到购物车
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<div class="b-hr">
								<hr>
							</div>
							<div class="detail-tabs wow fadeInUp">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" class="active"><a
										class="heading-line" href="product-details.html#description"
										aria-controls="description" role="tab" data-toggle="tab">描述</a>
									</li>
									<li role="presentation"><a class="heading-line"
										href="product-details.html#reviews" aria-controls="reviews"
										role="tab" data-toggle="tab">用户评论</a></li>
								</ul>
								<div class="tab-content">
									<div role="tabpanel" class="tab-pane active" id="description">
										<p>${detail.introduce}</p>
									</div>
									<div role="tabpanel" class="tab-pane" id="reviews">
										<div class="form-group">
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src=""
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
											<hr style="margin-top:15px; margin-bottom:15px;" />
											<div class="social-feed-box">
												<div class="social-footer">
													<div class="social-comment">
														<a href="javascript:void(0)" class="pull-left"> <img
															alt="image" class="img-circle"
															src="images/gougouadmin.jpg"
															style="width:40px;height:32px;margin-right:10px;">
														</a>
														<div class="media-body">
															<a href="javascript:void(0)"> Tomato丶 </a> <span
																class="text-content">这个菜很好吃，我每次都会点一次，⁄(⁄ ⁄•⁄ω⁄•⁄
																⁄)⁄~</span><br /> <a href="javascript:void(0)"
																class="small praise"><i class="fa fa-thumbs-up"></i>
																<span>13</span></a> 2月-21号 <small class="text-muted"></small>
														</div>
													</div>
												</div>
											</div>
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
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="b-hr custom-hr-1">
		<hr>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-sm-12 clearfix">
				<h3 class="heading-line-long">喜欢吃这道美食的人还看了这些</h3>
				<div class="custom-nav-mod">
					<div class="slider-nav">
						<a class="slider-prev"><i class="fa fa-chevron-left"></i></a> <span
							class="nav-divider"></span> <a class="slider-next"><i
							class="fa fa-chevron-right"></i></a>
					</div>
				</div>
			</div>
			<div class="col-sm-12 wow fadeInUp" style="height:450px;">
				<div class="row">
					<div id="detail-related" class="b-related enable-owl-carousel"
						data-loop="true" data-auto-width="false" data-dots="false"
						data-nav="false" data-margin="0" data-responsive-class="true"
						data-responsive='{"0":{"items":1},"479":{"items":2},"768":{"items":3},"1199":{"items":4}}'
						data-slider-next=".slider-next" data-slider-prev=".slider-prev">
						<s:iterator value="#request.recommend">
							<div class="related-item">
							<div class="b-item-card">
								<div class="image">
									<a href="javascript:void(0)"> <img
										src="img_requestCateImg?id=${id}&type=cate"
										class="img-responsive center-block" alt="/">
									</a>
									<div class="image-add-mod">
										<div class="add-description">
											<div>
												<span>${introduce}</span> <br>
												<a title="${name }" data-id="${id}"
													class="btn btn-lightbox btn-default-color1 btn-sm cate-img"> <i
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
										<span class="product-price">${marketPrice}</span>
									</div>
									<div class="add-buttons">
										<button type="button" class="btn btn-add btn-add-fav add-fav" data-id="${id}">
											<i class="fa fa-heart-o"></i>
										</button>
										<button type="button" class="btn btn-add btn-add-cart add-cart" data-id="${id}">
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
	</section>
	<jsp:include page="foot.jsp"></jsp:include>
<script type="text/javascript" src="js/plugins/zoom/MagicZoom.js"></script>
<script type="text/javascript" src="js/plugins/zoom/ShopShow.js"></script>
<div class="modal fade" id="showPic" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog">
   <div class="modal-content" style="border-radius:10px">
    <div class="modal-body" style="padding:0;">
		<img id="img" width="100%">
      </div>
    </div>
  </div>
</div>
<form action="cate-list" method="post" id="searchForm">
	<input type="hidden" name="category"/>
</form>
<script type="text/javascript">
$(function(){
	/*获取分类下美食数量*/
	var item = $(".con-item");
	$.post("json/cate_categoryOfCateNum",function(data){
		if(data==null)return;
		if(!(data instanceof Object))data = eval("("+data+")");
		for(var i=0;i<item.length;i++){
			var num = data[$(item[i]).attr("data-id")];
			if(num==undefined)num=0;
			$(item[i]).find(".num").html(num);
		}
	},"json");
	/*获取分类下美食数量end*/
	/* 分类点击事件 */
	/* $(".con-item").click(function(){
		$("input[name=category]").val($(this).attr("data-id"));
		$("#searchForm").submit();
	}) */
	/* 分类点击事件 end*/
	$(".min-image").mouseover(function(){
		$("#MagicZoom img").attr("src","img_requestImg?type=max&id="+$(this).attr("data-id"));
	}).click(function(){
		$("#img").attr("src","img_requestImg?type=max&id="+$(this).attr("data-id"));
		$('#showPic').modal('show');
	})
	$(".cate-img").click(function(){
		$("#img").attr("src","img_requestCateImg?type=max&id="+$(this).attr("data-id"));
		$('#showPic').modal('show');
	})
	$("#tsImgSArrL").click(function(){
		var imgbox = document.getElementById("imgs-box");
		var left = document.getElementById("tsImgSArrL");
		if(imgbox.offsetLeft<10)
		imgbox.style.left = imgbox.offsetLeft+20+"px";
		if(imgbox.offsetLeft<10)
		imgbox.style.left = "10px";
	})
	$("#tsImgSArrR").click(function(){
		var imgbox = document.getElementById("imgs-box");
		var right = document.getElementById("tsImgSArrR");
		if(imgbox.offsetLeft+imgbox.offsetWidth>right.offsetLeft-10)
		imgbox.style.left = imgbox.offsetLeft-20+"px";
		if(imgbox.offsetLeft+imgbox.offsetWidth>right.offsetLeft-10)
		imgbox.style.left = right.offsetLeft-imgbox.offsetWidth-20+"px";
	})
	binkEvent();
	addFavItem();
	/* $(".max-image li").unbind("click");
	$(".max-image li").bind("click",function(){
		$("#img").attr("src","img_requestImg?type=max&id="+$(this).attr("data-id"));
		$('#showPic').modal('show');
	}) */
})
/* //js获取URL后的参数值
function getUrlParam(k) {
    var regExp = new RegExp('([?]|&)' + k + '=([^&]*)(&|$)');
    var result = window.location.href.match(regExp);
    if (result) {
        return decodeURIComponent(result[2]);
    } else {
        return null;
    }
} */
function binkEvent(){
	$(".add-cart").unbind();
	$(".add-cart").bind("click",function(){
		var CateId = $(this).attr("data-id");
		var CateCount = $("#CateCount").val();
		if(CateCount == null){
			var CateCount = 1;
		}
		$.post("json/cart_addCart","cateId="+CateId+"&count="+CateCount,function(data){
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
