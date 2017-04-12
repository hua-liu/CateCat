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
<link href="css/plugins/iCheck/custom.css" rel="stylesheet">
</head>
<body>
	<footer class="style-2">
	<div class="container">

		<div class="row">
			<div class="col-xs-12 col-sm-3">
				<div class="b-logo">
					<a href="#"> <span>Catecat</span> <br> <span>love</span>
					</a>
				</div>
				<div class="b-footer-contacts wow pulse nimated">
					<div class="footer-contacts-info">
						<p>本店支持预约定座，快餐外送， 本店支持预约定座，快餐外送 本店支持预约定座，快餐外送</p>
					</div>
					<div class="footer-contacts-list">
						<ul class="list-unstyled">
							<li><i class="fa fa-map-pin fa-fw"></i> <span>天上人间555街，地上天堂222号</span>
							</li>
							<li><i class="fa fa-phone fa-fw"></i> <span>+ 123 456
									7890</span></li>
							<li><i class="fa fa-envelope-o fa-fw"></i> <span>our@qq.com</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-9">
				<div class="row">
					<div class="b-footer-menu clearfix">
						<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
							<div class="footer-menu-item wow pulse nimated">
								<div class="heading-line">本店快链</div>
								<ul class="list-unstyled">
									<li><a href="#">所有菜系</a></li>
									<li><a href="#">最新菜系</a></li>
									<li><a href="#">热销菜系</a></li>
									<li><a href="#">预约订座</a></li>
									<li><a href="#">论坛</a></li>
									<li><a href="#">联系我们</a></li>
								</ul>
							</div>
						</div>
						<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
							<div class="footer-menu-item wow pulse nimated">
								<div class="heading-line">我们菜系</div>
								<ul class="list-unstyled">
								<s:iterator value="#application.categorys" status="c1">
									<s:if test="name=='类别'">
									<s:iterator value="childs" status="c2">
										<s:if test="#c2.count<7">
										<li><a href="list?category=${id}">${name }</a></li>
										</s:if>
									</s:iterator>
									</s:if>
								</s:iterator>
								</ul>
							</div>
						</div>
						<div class="clearfix visible-xs-block"></div>
						<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
							<div class="footer-menu-item wow pulse nimated">
								<div class="heading-line">信息</div>
								<ul class="list-unstyled">
									<li><a href="#">我的账户</a></li>
									<li><a href="#">我的购物车</a></li>
									<li><a href="#">我的收藏</a></li>
									<li><a href="#">帮助中心</a></li>
								</ul>
							</div>
						</div>
						<div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
							<div class="b-latest-tweets wow pulse nimated">
								<div class="heading-line">最新活动</div>
								<div class="tweet-item">
									<p>
										本店新开张,全场菜系9.5折,时间有限<a href="#">点击了解详情</a>
									</p>
								</div>
								<div class="tweet-item">
									<p>
										本店新上线新菜系,价格限时优惠,<a href="#">点击了解详情</a>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
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
									<p>预约订座</p>
									<p>远离到店没有座位的尴尬</p>
								</div>
							</div>
						</div>
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-truck"></i>
								</div>
								<div class="feature-info">
									<p>美食外送</p>
									<p>当地专用人员送达</p>
								</div>
							</div>
						</div>
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-commenting"></i>
								</div>
								<div class="feature-info">
									<p>论坛吐嘈</p>
									<p>讨论你最喜爱的美食</p>
								</div>
							</div>
						</div>
						<div class="b-feature-holder col-sm-3">
							<div class="feature-block">
								<div class="feature-icon">
									<i class="fa fa-headphones"></i>
								</div>
								<div class="feature-info">
									<p>全天24小时服务</p>
									<p>通过客服可以获得更好的服务</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="b-footer-add" style="padding:10px;background:#2C2C2C">
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-12">
					<div class="clearfix">
						<div class="b-copy pull-left wow fadeInLeft col-sm-4">
							<p style="color:#999">版权?2016。美食猫保留所有权利。</p>
						</div>
						<div class="b-payments pull-right col-sm-4" style="float:right">
							<div class="input-group col-sm-12">
								<input type="text" class="form-control"
									placeholder="订阅最新资询,我们将推送最新活动与最新菜系到您的邮箱"
									style="background:none;color:#EEE;border-color:#555;">
								<div class="input-group-addon subscibe"
									style="background:none;color:#EEE;border-color:#555;">
									<i class="fa fa-envelope-o fa-fw"></i>订阅
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</footer>
	<!--登陆模态框  -->
	<!-- <div class="modal inmodal" id="loginModal">
		<div class="modal-dialog">
			<div class="modal-content animated fadeIn"
				style="width:300px;margin:20% auto">
				<div class="modal-header" style="padding:5px">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				<div class="modal-body"  style="background:rgba(255,255,255,0.5)">
					<div class="text-center">
						<div style="padding:0 30px;">
						<div class="b-logo">
							<a href="index"> <span>CateCat</span> <br> <span>love</span>
							</a>
						</div>
						</div>
						<h3 style="text-align:center;width:100%">欢迎使用 LHV</h3>
						<form class="m-t" role="form" action="user_login.action"
							method="post" id="loginForm">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="用户名/邮箱/手机号"
									name="username">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" placeholder="密码"
									name="password">
							</div>
							<div class="form-group">
								 <div class="checkbox i-checks">
			                        <label class="no-padding login-check">
			                            <input type="checkbox" name="autologin" value="auto_ok"><i>自动登录</i></label>
			                    </div>
			                </div>
							<button type="submit"
								class="btn btn-primary block full-width m-b" style="border:0">登
								录</button>


							<p class="text-muted text-center">
								<a href="javascript:void()" onclick="alert('暂不支持找回密码')"
									style="color:#555"><small>忘记密码了？</small></a> | <a
									href="user_regist.action" style="color:#555">注册一个新账号</a>
							</p>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	 -->
	<!--主题 设置-->
	<script src="assets/switcher/js/dmss.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/modernizr.custom.js"></script>
	<script src="js/wow.min.js"></script>

	<!--bootstrap-select -->
	<script src="assets/bootstrap-select/bootstrap-select.min.js"></script>

	<!--[if lt IE 9]>
	<script src="js/ie/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="js/ie/respond.min.js"></script>
	<![endif]-->

	<!-- Countdown Timer -->
	<script src="assets/countdown/dscountdown.min.js"></script>

	<!--Owl Carousel-->
	<script src="assets/owl-carousel/owl.carousel.min.js"></script>

	<!--bx slider-->
	<script src="assets/bxslider/jquery.bxslider.min.js"></script>

	<!-- slider-pro-master -->
	<script src="assets/slider-pro-master/js/jquery.sliderPro.min.js"></script>

	<!-- <script src="assets/prettyPhoto/js/jquery.prettyPhoto.js"></script> -->
	<script src="js/waypoints.min.js"></script>
	<script src="js/jquery.easypiechart.min.js"></script>
	<script src="js/jquery.spinner.min.js"></script>
	<script src="js/isotope.pkgd.min.js"></script>
	<script src="js/jquery.placeholder.min.js"></script>
	<script src="js/theme.js"></script>
	<!-- iCheck -->
    <script src="js/plugins/iCheck/icheck.min.js"></script>
	<script src="js/plugins/layer/layer.min.js"></script>
	<script src="js/plugins/sweetalert/sweetalert.min.js"></script>
	<!-- 省级联动的JS -->
	<script src="js/linkage/Popt.js"></script>
	<script src="js/linkage/cityJson.js"></script>
	<script src="js/linkage/citySet.js"></script>
	<script type="text/javascript">
		$(function() {
			$('.i-checks').iCheck({
				checkboxClass : 'icheckbox_square-green',
				radioClass : 'iradio_square-green',
			});
			$("#loginForm").submit(function() {
				if ($("input[name=account]").val() == '') {
					parent.layer.msg("账号不能为空");return false;
				}
				if ($("input[name=password]").val() == '') {
					parent.layer.msg("密码不能为空");return false;
				}
				//login request
				$.post("json/user_login", $("#loginForm").serialize(), function(data) {
					if (!(data instanceof Object))
						data = eval("(" + data + ")");
					if (data.result) { //login success
						window.location.reload();
					} else { //login failed
						alert(data.cause);
					}
				})
				return false;
			})
		})
	</script>
</body>
</html>
