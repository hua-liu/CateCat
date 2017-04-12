<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>登陆中心</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="favicon.ico"> 
	<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	 <link href="css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css?v=4.1.0" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/plugins/login/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/plugins/login/htmleaf-demo.css">
	<link rel="stylesheet" href="css/plugins/login/style.css">
    <style type="text/css">
    	.error{
    		border-color:#F00;
    	}
    </style>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>
<body class="gray-bg">
	<canvas id="c1"></canvas>
	<canvas id="c2"></canvas>
    <div class="middle-box text-center loginscreen  animated fadeInDown" style="margin-top:10%;background:rgba(0,0,0,0.2);padding:10px;border-radius:10px">
        <div>
            <div style="padding:30px;">
               <!--  <h1 class="logo-name"><img alt="logo" src="img/logo3.png"></h1> -->
               <div class="b-logo">
							<a href="index"> <span>CateCat</span> <br> <span>love</span>
							</a>
						</div>
            </div>
            <h3>欢迎使用 CateCat</h3>

            <form class="m-t" role="form" action="admin_loginAdmin.action" method="post" autocomplete="off">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="用户名/邮箱/手机号" name="username" style="background:none;color:#FFF">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="密码" name="password" style="background:none;color:#FFF">
                </div>
                <div class="form-group">
                 <div class="checkbox i-checks">
                        <label class="no-padding" style="float:left">
                            <input type="checkbox" name="autologin" value="auto_ok"><i>自动登录</i></label>
                    </div>
                </div>
                <button type="submit" class="btn btn-success block full-width m-b" style="margin-top:10px">登 录</button>
                <p class="text-muted text-center"> <a href="javascript:void()" onclick="alert('暂不支持找回密码')"><small>忘记密码了？</small></a> | <a href="user_registPage.action">注册一个新账号</a>
                </p>

            </form>
        </div>
    </div>
    <!-- 全局js -->
    <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>
    <script src="js/plugins/layer/layer.min.js"></script>
    <script src="js/login.js"></script>
    <!-- iCheck -->
    <script src="js/plugins/iCheck/icheck.min.js"></script>
    <c:if test="${not empty requestScope.error}">
		<script type="text/javascript">
			parent.layer.msg("${requestScope.error}");
		</script>
    </c:if>
	<script type="text/javascript">
		$(function(){
		 	$('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
			$("form").submit(function(){
				if($("input[name=account]").val()==''){
					parent.layer.msg("账号不能为空");return false;
				}
				if($("input[name=password]").val()==''){
					parent.layer.msg("密码不能为空");return false;
				}
				var ur = "${request.error}"==""?$(".username-error").html():"${request.error}";
				if(ur!=undefined){
					tooltip(ur,$("input[name=username]"));
				}
			})
		})
		//工具提示
        function tooltip(text,el){
        	if(text=='')return;
        	$(el).attr("data-content",text).attr("data-toggle","popover").attr("data-placement","top");
        	$(el).popover("show");
        		$(el).addClass("error")
        	/* setTimeout(function(){
        		$(el).popover("destroy");
        	},3000) */
        }
	</script>
  </body>
</html>
