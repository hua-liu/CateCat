<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>我的收藏</title>
<link rel="stylesheet" href="css/favList/base.css">
<style type="text/css">
.space-seo {
	display: none;
}

.wrapper {
	width: 1100px;
	margin: 0 auto;
	position: relative;
}

#browser-version-tip {
	position: absolute;
	display: none;
	top: 42px;
	left: 0;
	z-index: 100;
	width: 100%;
	height: 40px;
	line-height: 40px;
	background-color: #e40c0c;
	text-align: center;
	font-size: 14px;
	color: #fff;
}

#browser-version-tip a {
	margin: 0 2px;
	text-decoration: underline;
	color: #0077ff;
}

#browser-version-tip #close-browser-tip {
	position: absolute;
	right: 0;
	top: 10px;
	display: inline-block;
	margin-left: 15px;
	width: 20px;
	height: 20px;
	background-image: url(//space.bilibili.com/icons2.png);
	background-position: -1368px -278px;
	vertical-align: middle;
}
</style>

<link href="css/favList/app_319a7ee2.css" rel="stylesheet">
<style type="text/css">
#page-favlist .content {
	width: 1160px;
	margin-top: 20px
}

#page-favlist .fav-item {
	float: left;
	margin: 0 20px 16px 0;
	position: relative
}

#page-favlist .fav-item:hover .fav-action {
	visibility: visible
}

#page-favlist .fav-item[data-count="0"] .fav-cover-placeholder {
	border-radius: 2px;
	background-image:
		url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJgAAACYCAYAAAAYwiAhAAADpUlEQVR4Ae3dNXRkZRjH4RtrcHd37XB3d3d3p6Xbigp3p94KlwqXDqcLTQxZ3waL/O/iDskMM9+d5z3naSLv2C+efKnGvlh8dIzHdAvBeBxdiYs2Gq/aeQEgMASGwEBgCAyBgcAQGAIDgSEwBAYCQ2AgMASGwEBgCAyBgcAQGAIDgSEwEBgCQ2AgMASGwEBgCAyBgcAQGAIDgSEwEBgCQ2DQusBYErfH7rHKj3aPO2LJXHYLjOdjw+ovpn5ePD+b3QLjoRio/mHql4mH/stugXF/9R+nfp1/s1tgvBwDswhsIF7+u90CYzTWrGY59evG6J/tFhhTcVg1x6l3xNTv9wuMB6oWTb3r17sFxoJYu4WBrR0LQmArcE3V4snO60JgYwzHYBsCG4xhgXFB1aapdwust30cg20MbDA+EVjzfR/vx/1xVRwQG1T/09SXFQfEVXF/vB/fC6xsY3FvHBGrVl029XWKI+LeGBNYGabi2Tgw+qtCpr6ucVA8G1MC605vx65V4VPfhnhHYN1jKubFYNWQqW9LzIspgXXeLVVDp75tAuust6KvwYH1xbsC65zTq4ZPbuMZAuucDXsgsA0F1jkr90Bgqwisc7bugcC2EVjnXNADgV0osM55ugcCe1pgnTMZ2zT8w+OkwDprfoMDm+8brd3h8AbGdbifRXaP8VinQXGtHeMC6y7Px0AD4hqI5/02RXe6tQGB3e73wbrb1QXHdbXfaO1+k3FBgXFdEN8LrAzfx/kFxXV+WXEJrDYZVxYQ15Ux6Y8+yrSggMAW+LO1cr1aQGCvCqxc9xUQ2H0CK9fNBQR2s8DKdWIBgZ0osHLtXkBguwusXFsWENhWAivXGgUEtqbAyrV6AYGtLrByrV1AYGsJrFybFxDY5gIr1x4FBLaHwMp1QQGBnS+wcj1WQGCPCaxcC2OohcdcnhlnRX+Ldg7FQoGV7ZoWhHVKfPCrnR/EKXMNrb5uTpku34LYZhYP/mpxVXz6N7s/jatitVn+1fZCgTXDcOz0L08U3CcejeX/Yf/yeDT2jr5/cTk7xWfOyW+Wb+LJOC52iLVj49gzLo0nYqxF5/M/EZfFXrFprB07xnHxZHzTW//pAwSGwEBgCAyBgcAQGAIDgSEwBAYCQ2AgMASGwEBgCAyBgcAQGAIDgSEwBAYCQ2AgMASGwEBgCAyBgcAQGAIDgSEwEBgCQ2AgMASGwEBgCAyBgcAQGAIbb9NymKgDOyYmWrwYRuOYGRQSx031+RTwAAAAAElFTkSuQmCC);
	width: 152px;
	height: 152px
}

#page-favlist .fav-item[data-count="1"] .fav-cover-0 {
	border-radius: 2px;
	height: 152px
}

#page-favlist .fav-item[data-count="2"] .fav-cover-1 {
	border-radius: 0 0 2px 2px;
	width: 152px
}

#page-favlist .fav-item[data-public="1"], #page-favlist .fav-item[data-public="3"]
	{
	display: none
}

#page-favlist .fav-item .state {
	color: #99a2aa;
	float: right;
	line-height: 24px
}

#page-favlist .fav-item .name {
	display: block;
	font-size: 18px;
	font-family: Microsoft Yahei UI Light, Microsoft Yahei Light;
	line-height: 24px;
	height: 24px;
	width: 7em;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap
}

#page-favlist .fav-item .delete, #page-favlist .fav-item .edit {
	color: #00a1d6;
	cursor: pointer;
	display: inline-block;
	-webkit-transition: none;
	transition: none;
	text-indent: 1.2em
}

#page-favlist .fav-item .edit {
	background-position: -540px -465px
}

#page-favlist .fav-item .delete {
	background-position: -540px -404px;
	float: right
}

#page-favlist .fav-covers {
	border: 1px solid #ccd0d7;
	border-radius: 4px;
	display: block;
	width: 152px;
	height: 152px;
	margin-bottom: 11px;
	padding: 3px;
	overflow: hidden;
	position: relative
}

#page-favlist .fav-cover {
	background-size: cover;
	background-position: 50%;
	display: block;
	float: left
}

#page-favlist .fav-cover-0 {
	border-radius: 2px 2px 0 0;
	width: 152px;
	height: 74px;
	margin-bottom: 4px
}

#page-favlist .fav-cover-1 {
	border-radius: 0 0 0 2px;
	width: 74px;
	height: 74px;
	margin-right: 4px
}

#page-favlist .fav-cover-2 {
	border-radius: 0 0 2px 0;
	width: 74px;
	height: 74px
}

#page-favlist .fav-count {
	background: rgba(0, 0, 0, .5);
	border-radius: 9px;
	color: #fff;
	display: block;
	line-height: 18px;
	text-align: center;
	width: 32px;
	position: absolute;
	top: 10px;
	right: 10px
}

#page-favlist .fav-action {
	visibility: hidden;
	margin-top: 7px
}

#page-favlist .list-create {
	width: 158px;
	height: 158px
}

#page-favlist .switcher-container {
	margin-top: 10px
}

#page-favlist .breadcrumb {
	position: relative
}

#page-favlist .fav-poptip {
	top: -15px;
	left: 100px
}

@media ( min-width :1420px) {
	#page-favlist .content {
		width: 1270px
	}
	#page-favlist .fav-covers {
		width: 182px;
		height: 182px
	}
	#page-favlist .fav-cover-0 {
		width: 182px;
		height: 89px
	}
	#page-favlist .fav-cover-1, #page-favlist .fav-cover-2 {
		width: 89px;
		height: 89px
	}
	#page-favlist .fav-item[data-count="0"] .fav-cover-placeholder {
		border-radius: 2px;
		background-image:
			url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJgAAACYCAYAAAAYwiAhAAADpUlEQVR4Ae3dNXRkZRjH4RtrcHd37XB3d3d3p6Xbigp3p94KlwqXDqcLTQxZ3waL/O/iDskMM9+d5z3naSLv2C+efKnGvlh8dIzHdAvBeBxdiYs2Gq/aeQEgMASGwEBgCAyBgcAQGAIDgSEwBAYCQ2AgMASGwEBgCAyBgcAQGAIDgSEwEBgCQ2AgMASGwEBgCAyBgcAQGAIDgSEwEBgCQ2DQusBYErfH7rHKj3aPO2LJXHYLjOdjw+ovpn5ePD+b3QLjoRio/mHql4mH/stugXF/9R+nfp1/s1tgvBwDswhsIF7+u90CYzTWrGY59evG6J/tFhhTcVg1x6l3xNTv9wuMB6oWTb3r17sFxoJYu4WBrR0LQmArcE3V4snO60JgYwzHYBsCG4xhgXFB1aapdwust30cg20MbDA+EVjzfR/vx/1xVRwQG1T/09SXFQfEVXF/vB/fC6xsY3FvHBGrVl029XWKI+LeGBNYGabi2Tgw+qtCpr6ucVA8G1MC605vx65V4VPfhnhHYN1jKubFYNWQqW9LzIspgXXeLVVDp75tAuust6KvwYH1xbsC65zTq4ZPbuMZAuucDXsgsA0F1jkr90Bgqwisc7bugcC2EVjnXNADgV0osM55ugcCe1pgnTMZ2zT8w+OkwDprfoMDm+8brd3h8AbGdbifRXaP8VinQXGtHeMC6y7Px0AD4hqI5/02RXe6tQGB3e73wbrb1QXHdbXfaO1+k3FBgXFdEN8LrAzfx/kFxXV+WXEJrDYZVxYQ15Ux6Y8+yrSggMAW+LO1cr1aQGCvCqxc9xUQ2H0CK9fNBQR2s8DKdWIBgZ0osHLtXkBguwusXFsWENhWAivXGgUEtqbAyrV6AYGtLrByrV1AYGsJrFybFxDY5gIr1x4FBLaHwMp1QQGBnS+wcj1WQGCPCaxcC2OohcdcnhlnRX+Ldg7FQoGV7ZoWhHVKfPCrnR/EKXMNrb5uTpku34LYZhYP/mpxVXz6N7s/jatitVn+1fZCgTXDcOz0L08U3CcejeX/Yf/yeDT2jr5/cTk7xWfOyW+Wb+LJOC52iLVj49gzLo0nYqxF5/M/EZfFXrFprB07xnHxZHzTW//pAwSGwEBgCAyBgcAQGAIDgSEwBAYCQ2AgMASGwEBgCAyBgcAQGAIDgSEwBAYCQ2AgMASGwEBgCAyBgcAQGAIDgSEwEBgCQ2AgMASGwEBgCAyBgcAQGAIbb9NymKgDOyYmWrwYRuOYGRQSx031+RTwAAAAAElFTkSuQmCC);
		width: 182px;
		height: 182px
	}
	#page-favlist .fav-item[data-count="1"] .fav-cover-0 {
		border-radius: 2px;
		height: 182px
	}
	#page-favlist .fav-item[data-count="2"] .fav-cover-1 {
		border-radius: 0 0 2px 2px;
		width: 182px
	}
	#page-favlist .list-create {
		width: 190px;
		height: 190px
	}
}
</style>
<style type="text/css">
.switcher-container[_v-0a6b178c] {
	height: 20px
}

.switcher-container.switcher-on .switcher[_v-0a6b178c] {
	background-color: #00a1d6
}

.switcher-container.switcher-on .cursor[_v-0a6b178c] {
	left: 17px
}

.switcher[_v-0a6b178c] {
	position: relative;
	display: inline-block;
	width: 31px;
	height: 16px;
	border-radius: 8px;
	background-color: #ccd0d7;
	vertical-align: middle;
	cursor: pointer;
	-webkit-transition: background-color .2s ease;
	transition: background-color .2s ease
}

.cursor[_v-0a6b178c] {
	position: absolute;
	top: 2px;
	left: 2px;
	width: 12px;
	height: 12px;
	border-radius: 12px;
	background: #fff;
	-webkit-transition: left .2s ease;
	transition: left .2s ease
}

.label[_v-0a6b178c] {
	display: inline-block;
	vertical-align: middle
}
</style>
<style type="text/css">
.sp-input[_v-82bca4d6] {
	position: relative;
	font-size: 12px
}

.sp-input input[_v-82bca4d6] {
	display: block;
	width: 100%;
	line-height: 28px;
	height: 28px;
	padding: 0 50px 0 5px;
	color: #222;
	border-radius: 4px;
	box-sizing: border-box;
	border: 1px solid #ccd0d7;
	box-shadow: inset 0 2px 4px rgba(0, 0, 0, .1);
	-webkit-transition: all .3s ease;
	transition: all .3s ease
}

.sp-input input[_v-82bca4d6]:focus {
	border-color: #00a1d6
}

.sp-input .letter-count[_v-82bca4d6] {
	position: absolute;
	right: 5px;
	top: 3px;
	height: 20px;
	color: #99a2aa
}
</style>
<style type="text/css">
.tabs[_v-2887c0c0] {
	margin-bottom: 24px
}

.tabs a[_v-2887c0c0] {
	font-size: 16px;
	line-height: 1em
}

.tabs a[_v-2887c0c0]:after {
	color: #ddd;
	content: "|";
	margin: 0 15px
}

.tabs a[_v-2887c0c0]:last-child:after {
	display: none
}

.tabs a.active[_v-2887c0c0] {
	color: #00a1d6;
	font-size: 20px
}
</style>
<style type="text/css">
.sp-poptip[_v-572ca416] {
	position: absolute;
	padding: 13px 47px 13px 20px;
	line-height: 20px;
	background: #00a1d6;
	opacity: .95;
	font-size: 12px;
	z-index: 10;
	color: #fff;
	border-radius: 4px;
	white-space: nowrap
}

.sp-poptip.top[_v-572ca416] {
	text-align: center
}

.sp-poptip.top .sp-arrow[_v-572ca416] {
	left: 50%;
	bottom: -6px;
	-webkit-transform: translateX(-50%);
	transform: translateX(-50%);
	border-left: 7px solid transparent;
	border-right: 7px solid transparent;
	border-top: 6px solid #00a1d6
}

.sp-poptip.right[_v-572ca416] {
	text-align: left
}

.sp-poptip.right .sp-arrow[_v-572ca416] {
	top: 50%;
	left: -6px;
	-webkit-transform: translateY(-50%);
	transform: translateY(-50%);
	border-top: 7px solid transparent;
	border-bottom: 7px solid transparent;
	border-right: 6px solid #00a1d6
}

.sp-poptip.bottom[_v-572ca416] {
	text-align: center
}

.sp-poptip.bottom .sp-arrow[_v-572ca416] {
	left: 50%;
	top: -6px;
	-webkit-transform: translateX(-50%);
	transform: translateX(-50%);
	border-left: 7px solid transparent;
	border-right: 7px solid transparent;
	border-bottom: 6px solid #00a1d6
}

.sp-poptip.left[_v-572ca416] {
	text-align: right
}

.sp-poptip.left .sp-arrow[_v-572ca416] {
	top: 50%;
	right: -6px;
	-webkit-transform: translateY(-50%);
	transform: translateY(-50%);
	border-top: 7px solid transparent;
	border-bottom: 7px solid transparent;
	border-left: 6px solid #00a1d6
}

.sp-poptip .sp-close[_v-572ca416] {
	position: absolute;
	top: 15px;
	right: 18px;
	width: 16px;
	height: 16px;
	background-image: url(//s1.hdslb.com/bfs/static/space/icons3.png);
	background-position: -1369px -281px;
	cursor: pointer
}

.sp-poptip .sp-arrow[_v-572ca416] {
	position: absolute;
	width: 0;
	height: 0
}
</style>
</head>

<body>
	<jsp:include page="head.jsp"></jsp:include>
	<div id="browser-version-tip">
		<div class="wrapper">
			抱歉，您正在使用不支持的浏览器访问个人空间。推荐您<a
				href="http://www.google.cn/chrome/browser/desktop/index.html">安装
				Chrome 浏览器</a>以获得更好的体验 ヾ(o◕∀◕)ﾉ<a href="javascript:;"
				id="close-browser-tip"></a>
		</div>
	</div>
	<div id="space-body" class="owner">
		<div class="h">
			<div class="wrapper">
				<div class="h-inner"
					style="background-image: url(&quot;//i0.hdslb.com/bfs/space/9ccc0447aebf0656809b339b41aa5b3705f27c47.png&quot;);">
					<div class="h-gradient"></div>
					<div class="h-user">
						<div class="h-avatar">
							<img id="h-avatar" do-not-click-me-anymore=""
								src="img_requestImg?type=avatar&id=<s:property value="#session.user.avatar.id"/>">
							<span class="user-auth-subscript avatar-x"></span>
						</div>
						<div class="h-info">
							<div class="h-basic">
								<span id="h-name">${user.username}</span> 
							</div>
							<div class="h-sign" title="" style="display: none;"></div>
							
						</div>
					</div>
					
					<div class="h-version clearfix">
						<div class="h-v-btn popup-select" id="h-v-role">
							视角：<span class="popup-selected">我自己</span><span
								class="h-role-icon d-arrow d-arrow-dark"></span>
						</div>
					</div>
				</div>
			</div>
			<div id="space-theme" class="t">
				<div id="theme-header">
					<div class="wrapper">
						<div class="theme-topright">
							<div class="theme-ann" style="">
								
							</div>
						</div>
						<div class="theme-tabs clearfix">
						</div>
					</div>
				</div>
					<div class="theme-panel">
						<div class="wrapper">
							<div class="theme-list ps-container ps-theme-default" name="toutu"
								id="toutu-scroll-container"
								data-ps-id="795be0bc-a6e4-d3b4-71f7-2f3d7c6951eb">
								<div class="theme-list-inner clearfix" id="toutu-list"></div>
								<div class="ps-scrollbar-x-rail" style="left: 0px; bottom: 0px;">
									<div class="ps-scrollbar-x" tabindex="0"
										style="left: 0px; width: 0px;"></div>
								</div>
								<div class="ps-scrollbar-y-rail" style="top: 0px; right: 0px;">
									<div class="ps-scrollbar-y" tabindex="0"
										style="top: 0px; height: 0px;"></div>
								</div>
							</div>
							<div class="edit-container" style="display: none;">
								<div class="crop-container" style="display: none;">
									<div class="preview-container"></div>
									<div class="option-pane">
									</div>
									<div class="btn-container btn-center" _v-008c1078="">
									</div>
								</div>
								<div class="uploading-container" style="display: none;">
									<div class="uploading">
										<div class="upload-content">
											<div class="loading-icon loading">
												<img src="css/favList/loading.png">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
				</div>
				<div class="theme-panel" style="display: none;">
					<div class="wrapper">
						<div class="theme-list" id="theme-scroll-container">
							<div class="theme-list-inner clearfix" id="theme-list"></div>
						</div>
					</div>
				</div>
				<div class="theme-buy-layer" id="theme-buy-layer"
					style="display: none;">
					<div class="theme-buy-layer-wrapper" id="theme-buy-layer-wrapper">
						<div class="theme-buy-close" action="close"></div>
						<div id="theme-buy-select-length" class="theme-buy-step"
							style="display: none;">
							<div class="theme-buy-header">
								<img class="theme-buy-preview" src="">
								<p class="theme-buy-name"></p>
							</div>
							<div class="theme-buy-body">
								
								<div class="theme-buy-price">
								
								</div>
							</div>
							<div class="theme-buy-footer">
							</div>
						</div>
						<div id="theme-buy-success" class="theme-buy-step"
							style="display: none;">
							<div class="theme-buy-body">
							
							</div>
							<div class="btn-container theme-buy-footer btn-center"
								_v-008c1078="">
							
							</div>
						</div>
						<div id="theme-buy-fail" class="theme-buy-step"
							style="display: none;">
							<div class="theme-buy-body">
								
							</div>
							<div class="btn-container theme-buy-footer btn-center"
								_v-008c1078="">
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="n">
			<div class="wrapper">
				<div class="n-inner clearfix">
				</div>
				<div class="n-cursor" style="width: 81px; left: 364px;"></div>
			</div>
		</div>
		<div class="s-space" style="">
			<div class="wrapper" id="page-favlist">
				<div class="col-full">
					<div class="breadcrumb">
						<p class="item cur">我的收藏夹</p>
						
					</div>
					
					<div class="section fav">
						<div class="content clearfix">
						<s:iterator value="#session.fav.orderItems">
							<%-- <div class="fav-item" data-count="3">
								<a class="fav-covers"
									 href="cate-details?id=${cate.id}">
								<img style="width:200px;heigth:200px;" src="img_requestCateImg?type=max&id=${cate.id}"
												class="img-responsive center-block" alt="6s">
								</a>
								<div class="m">
									<a class="name"
										href="cate-details?id=${cate.id}">${name}</a>
								</div>
							</div> --%>
							<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3 wow fadeInUp">
										<div class="b-item-card">
											<div class="image">
												<a href="javascript:void(0)"> <img
													src="img_requestCateImg?type=cate&id=${cate.id}"
													class="img-responsive center-block" alt="/">
												</a>
												<div class="image-add-mod" data-id="${cate.id}">
													<div class="add-description">
														<div>
															<span>${cate.about}</span> <br> <a
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
														<a class="product-name" href="cate-details?id=${cate.id}">${name }</a>
													</p>
												</div>
												<div class="add-buttons">
													<button data-id="${id}" type="button" class="btn btn-remove remove-favItem">
														<i class="glyphicon glyphicon-remove"></i>
													</button>
													<button data-id="${cate.id}" type="button" class="btn btn-add btn-add-cart add-cart">
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
		
	<ul class="bilibili-suggest"
		style="top: 42px; left: 936.5px; display: none; min-width: 188px; max-width: 360px;"></ul>
		<jsp:include page="foot.jsp"></jsp:include>
		<script type="text/javascript" src="js/fav.js"></script>
</body>
</html>