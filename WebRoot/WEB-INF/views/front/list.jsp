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
<title>美食搜索</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="favicon.ico">
<style type="text/css">
	.pagination li a{
		font-weight:bold;
		color:#000;
	}
	.pagination li.disabled a{
		font-weight:normal;
		color:#999;
	}
	.b-grid-list .card-price-block>span{
		float:right;padding:0 10px;
	}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	<section class="section-category-2">
	<div class="b-page-header">
		<div class="container">
			<div class="row">
				<div class="col-sm-12 clearfix">
					<h3 class="page-title pull-left">分类</h3>
					<div class="b-breadcrumbs pull-right">
						<ul class="list-unstyled">
							<li><a href="javascript:void(0)">主页</a></li>

							<li><span>分类</span></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
				<div class="lb-content lb-content-accordion condition">
					<s:iterator value="#application.categorys" status="c1">
						<s:if test="#c1.count==2">
							<div id="accordion9"
							class="accordion-l-box wow fadeInUp enable-accordion"
							data-active="0" data-collapsible="true"
							data-height-style="content">
							<h3>
								<span class="heading-line title-accordion-menu-item">价格(<i class="glyphicon glyphicon-yen"></i>)</span> <span
									class="accordion-icon"></span>
							</h3>
							<div>
								<div class="price-block">
									<div id="slider-range"></div>
									<input type="text" name="price" id="price-min" value="${session.search.priceMin<0?0:session.search.priceMin}" class="number"> <span
										class="price-diveder">-</span> <input type="text" name="price2" class="number"
										id="price-max" value="${session.search.priceMax<=0?999:sesson.search.priceMax}">
									<button class="btn btn-default-color1 btn-sm price-button">确定</button>
								</div>
							</div>
							</div>
							<div id="accordion10"
							class="accordion-l-box wow fadeInUp enable-accordion"
							data-active="0" data-collapsible="true"
							data-height-style="content">
							<h3>
								<span class="heading-line title-accordion-menu-item">关键字搜索</span><span class="accordion-icon"></span>
							</h3>
							<div>
								<div class="price-block">
									<input type="text" class="searchInput" placeholder="美食名称关键字搜索" value="${session.search.searchString}" style="width:100%">
									<button class="btn btn-default-color1 btn-sm key-button" style="margin:20px 0">确定</button>
								</div>
							</div>
							</div>
						</s:if>
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
											href="javascript:void(0)"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
									</s:if>
									<s:elseif test="#c2.count==6">
										<li class="more"><span class="load-more">更多</span>
											<ul class="more-list">
											<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="javascript:void(0)"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
									</s:elseif>
									<s:elseif test="#c2.last">
										<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="javascript:void(0)"> <i
												class="fa fa-caret-square-o-right"></i>${name } <span
												class="category-counter">[<span class="num">0</span>]</span>
										</a></li>
										</ul>
										</li>
									</s:elseif>
									<s:else>
										<li data-id="${id}" data-text="${name }" class="con-item"><a
											href="javascript:void(0)"> <i
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
				<%-- <div class="side-offer">
					<div class="b-offers wow fadeInUp">
						<a href="cate-details?id=${application.specialCate[2].id }"> <img
							src="img_requestImg?id=${application.specialCate[2].img.id }" class="img-responsive"
							alt="/">
						</a>
					</div>
				</div> --%>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
				<div class="b-offers wow fadeInUp">
					<a href="cate-details?id=${application.specialCate[1].cate.id }"> <img
						src="img_requestImg?id=${application.specialCate[1].img.id }"
						class="img-responsive center-block" alt="/">
					</a>
				</div>
				<div class="b-cat-slider clearfix wow fadeInUp">
					<div class="clearfix">
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2"
							style="max-width:100px;">
							<h3 class="heading-line">条件筛选</h3>
						</div>
						<div
							class="b-brand-filters col-xs-12 col-sm-10 col-md-10 col-lg-10">
							<div>
								<ul id="brands-slide-category"
									class="list-unstyled enable-owl-carousel condition-result"
									data-loop="false" data-auto-width="true" data-dots="false"
									data-nav="false" data-margin="0" data-responsive-class="true"
									data-responsive='{"0":{"items": "2","margin":"15"},"481":{"items": "3"}}'
									data-slider-next=".slider-next" data-slider-prev=".slider-prev">


								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="b-settings">
					<div class="settings-tools">
						<h3 class="heading-line pull-left">
							检索结果 <span class="settings-counter">[ 总记录: <span class="total">0</span>
								]
							</span>
						</h3>
						<div class="settings-block pull-right">
							<div class="settings-options">
								<div class="select-block">
									<span class="select-title">显示数</span> <select
										class="selectpicker" id="show-items" data-width="75px">
										<option value="9" ${session.search.rows==9?'selected':''}>9</option>
										<option value="15" ${session.search.rows==15?'selected':''}>15</option>
										<option value="30" ${session.search.rows==30?'selected':''}>30</option>
										<option value="45" ${session.search.rows==45?'selected':''}>45</option>
									</select>
								</div>
								<div class="select-block">
									<span class="select-title">排序</span> <select
										class="selectpicker" id="sort-product" data-width="134px">
										<option value="onLineTime" data-sort="desc" ${session.search.sidx=='onLineTime'?'selected':''}>最新上线</option>
										<option value="log-grade" data-sort="desc" ${session.search.sidx=='log.grade'?'selected':''}>评价由高到低</option>
										<option value="marketPrice" data-sort="asc"  ${session.search.sidx=='marketPrice'?'selected':''}>价格由低到高</option>
										<option value="shopPrice" data-sort="desc" ${session.search.sidx=='shopPrice'?'selected':''}>价格由高到低</option>
										<option value="log-buyCount" data-sort="asc" ${session.search.sidx=='log.buyCount'?'selected':''}>销量由低到高</option>
										<option value="log-viewCount" data-sort="desc" ${session.search.sidx=='log.viewCount'?'selected':''}>浏览量高到低</option>
									</select>
								</div>
							</div>
							<div class="settings-view hidden-md hidden-sm hidden-xs">
								<ul id="type-of-display" class="list-unstyled">

									<li>
										<button class="btn toogle-view grid-3 active-view">
											<i class="fa fa-th-large fa-fw"></i>
										</button>
									</li>
									<li>
										<button class="btn toogle-view grid-list">
											<i class="fa fa-th-list fa-fw"></i>
										</button>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!--<div class="settings-result text-center">
									<p>Showing restults 1 to 12 of 140 total</p>
								</div>-->
				</div>
				<div class="b-grid">
					<div class="row show-catebox">
					</div>
					<nav class="pagination-full clearfix">
					<ul class="pagination-num pagination wow fadeInUp">
					</ul>
					<ul class="pagination pagination-add">
						<li class="prev"><a href="javascript:void(0)">上一页</a></li>
						<li class="next"><a href="javascript:void(0)">下一页</a></li>
					</ul>
					</nav>
				</div>
			</div>
		</div>
	</div>
	<div class="b-hr">
		<hr>
		<hr>
	</div>
	<div class="cate-box" style="display:none">
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">
			<div class="b-item-card wow fadeInUp">
				<div class="special-plank new">
					<span class="sign"></span>
				</div>
				<div class="image">
					<a href="javascript:void(0)"
						data-gal="prettyPhoto" title=" " class="cate-attr"> <img
						src=""
						class="img-responsive center-block" alt=" " class="cate-attr">
						<div class="image-add-mod">		
							<span class="btn btn-lightbox btn-default-color1 btn-sm">
								<i class="fa fa-search-plus fa-lg"></i>
							</span>
						</div>
					</a>
				</div>
				<div class="card-info">
					<div class="caption">
						<div class="name-item">
							<a class="product-name" href="cate-details"> </a>
							<div class="rating">
								<span class="star"><i class="fa fa-star"></i></span> <span
									class="star"><i class="fa fa-star"></i></span> <span
									class="star"><i class="fa fa-star"></i></span> <span
									class="star"><i class="fa fa-star"></i></span> <span
									class="star star-empty"><i class="fa fa-star-o"></i></span>
								<div class="cate-log">
									<span><span class="watch-count">0</span>次浏览</span>
									<span class="sell">总销售<span class="sell-count">0</span>份</span>
								</div>
							</div>
						</div>
						<div class="card-price-block">
							<span><span class="price-title">优惠价格</span> <span
								class="product-price"><i class="glyphicon glyphicon-yen"></i><span class="cate-price">0.00</span></span>
							</span>
							<span class="product-price-old"><span class="price-title">价格</span> <span
								class="product-shopPrice"><i class="glyphicon glyphicon-yen"></i><span class="cate-shopPrice">0.00</span></span>
							</span>
						</div>
						<div class="product-description">
							<p class="description"></p>
						</div>
					</div>
					<div class="add-buttons">
						<!-- <button type="button" class="btn btn-add btn-add-compare">
							<i class="fa fa-refresh"></i>
						</button> -->
						<button type="button" class="btn btn-add btn-add-fav add-fav">
							<i class="fa fa-heart-o"></i>
						</button>
						<button type="button" class="btn btn-add btn-add-cart add-cart">
							<i class="fa fa-shopping-cart"></i>
						</button>
						<div class="cart-add-buttons">
							<button type="button" class="btn btn-cart-color1 add-cart">
								<i class="fa fa-shopping-cart"></i> 添加到购物车
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<div class="modal fade" id="showPic" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="border-radius:10px">
      <div class="modal-body" style="padding:0;">
        	<img id="img" width="100%"/>
      </div>
    </div>
  </div>
</div>
	<form id="search">
		<input type="hidden" class="page" name="cateRequest.page" value="${session.search.page}"/>
		<input type="hidden" class="rows" name="cateRequest.rows" value="${session.search.rows}"/>
		<input type="hidden" class="sidx" name="cateRequest.sidx" value="${session.search.sidx}"/>
		<input type="hidden" class="sort" name="cateRequest.sort" value="${session.search.sort}"/>
		<input type="hidden" class="category" name="cateRequest.category" value="${session.search.category}"/>
		<input type="hidden" class="priceMin" name="cateRequest.priceMin" value="${session.search.priceMin}"/>
		<input type="hidden" class="priceMax" name="cateRequest.priceMax" value="${session.search.priceMax}"/>
		<input type="hidden" class="searchString" name="cateRequest.searchString" value="${session.search.searchString}"/>
	</form>
	</section>
	<jsp:include page="foot.jsp"></jsp:include>
	<script type="text/javascript" src="js/list.js"></script>
</body>
</html>
