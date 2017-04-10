<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serverPath = "ws://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加美食</title>
<link href="css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="css/admin/addCate.css" rel="stylesheet" type="text/css">
<link href="css/plugins/treeview/bootstrap-treeview.min.css" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/style.css?v=4.1.0" rel="stylesheet">
 <link rel="stylesheet" type="text/css" href="css/plugins/webuploader/webuploader.css">
 <link rel="stylesheet" type="text/css" href="css/plugins/webuploader/webuploader-demo.css">
<!-- Sweet Alert -->
<link href="css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<style type="text/css">
body {
	background: #ecf0f5;
}
.categoryUl{
	overflow:hidden;
}
.categoryUl.error{
	border-color:#F00;
}
.categoryList{
	position:absolute;
	right:-180px;top:0;
	height:100%;
	display:none;
}
#treeview li{
	padding-left:2px;
	text-align:left;
}
.categoryList >.ibox-content{
	overflow:auto;
	height:90%;
}
.unedit{
	position:absolute;
	width:100%;height:100%;
	background:rgba(0,0,0,0.1);
	z-index:100;
}
.unedit .spiner-example{
	padding-top:35%;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="sourceInfor move col-sm-8">
			<div class="ibox float-e-margins categoryList">
                <div class="ibox-title">
                    <h5>分类列表</h5>
                    <div class="ibox-tools">
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div id="treeview">
                    </div>
                </div>
            </div>
			<form id="sourceInforForm" method="post" autocomplete="off">
				<table class="table col-sm-8">
					<caption>
						<h2>美食信息</h2>
					</caption>
					<tr>
						<td colspan="2">
							<div class="input-group col-sm-12">
								<div class="input-group-addon">美食名称</div>
								<input type="text" class="form-control" title="名称格式错误"
									placeholder="填写美食名称(长度不超过60)" name="name"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="col-sm-6">
							<div class="input-group">
								<div class="input-group-addon">市场价格</div>
								<input type="text" class="form-control price" source-data="0" title="价格格式错误"
									placeholder="填写美食价格" name="shopPrice"/>
								<div class="input-group-addon"><i class="glyphicon glyphicon-yen"></i></div>
							</div>
						</td>
						<td class="col-sm-6">
							<div class="input-group">
								<div class="input-group-addon">优惠价格</div>
								<input type="text" class="form-control price" source-data="0" title="价格格式错误"
									placeholder="填写美食价格" name="marketPrice"/>
								<div class="input-group-addon"><i class="glyphicon glyphicon-yen"></i></div>
							</div>
						</td>
					</tr>
					<s:hidden name="category"/>
					<s:hidden name="id"/>
					<tr>
						<td>
							<div class="input-group">
								<div class="input-group-addon">美食分类</div>
								<ul class="form-control categoryUl" title="分类格式错误" data-type="类别"
									tabindex="0"></ul>
							</div>
						</td>
						<td>
							<div class="input-group">
								<div class="input-group-addon">主要成分</div>
								<ul class="form-control categoryUl" title="成分格式错误" data-type="主成"
									tabindex="0"></ul>
							</div>
						</td>
					</tr>
					
					<tr>
						<td>
							<div class="input-group">
								<div class="input-group-addon">烹饪工艺</div>
								<ul class="form-control categoryUl" title="工艺格式错误" data-type="工艺"
									tabindex="0"></ul>
							</div>
						</td>
						<td>
							<div class="input-group">
								<div class="input-group-addon">美食口味</div>
								<ul class="form-control categoryUl" title="口味格式错误" data-type="口味"
									tabindex="0"></ul>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="input-group">
								<div class="input-group-addon">食用场景</div>
								<ul class="form-control categoryUl" title="场景格式错误" data-type="场景"
									tabindex="0"></ul>
							</div>
						</td>
						<td>
							<div class="input-group">
								<div class="input-group-addon">有何功效</div>
								<ul class="form-control categoryUl" title="功效格式错误" data-type="功效"
									tabindex="0"></ul>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="input-group">
								<div class="input-group-addon">美食简介</div>
								<textarea class="form-control" placeholder="填写资源描述文字" title="简介格式错误"
									name="about"></textarea>
							</div>
						</td>
						<td>
							<div class="input-group">
								<div class="input-group-addon">详细描述</div>
								<textarea class="form-control" placeholder="填写资源描述文字" title="描述格式错误"
									name="introduce"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						 <div id="uploader" class="wu-example">
                                <div class="queueList">
                                    <div id="dndArea" class="placeholder">
                                        <div id="filePicker"></div>
                                        <p>或将照片拖到这里，单次最多可选10张</p>
                                    </div>
                                </div>
                                <div class="statusBar" style="display:none;">
                                    <div class="progress">
                                        <span class="text">0%</span>
                                        <span class="percentage"></span>
                                    </div>
                                    <div class="info"></div>
                                    <div class="btns">
                                        <div id="filePicker2"></div>
                                        <div class="uploadBtn" style="display:none;">开始上传</div>
                                    </div>
                                </div>
                            </div>
						</td>
					</tr>
					<tr>
						<td style="text-align: center" colspan="2">
							<button class="btn btn-primary" type="submit" title="提交">开始提交</button>
							<button class="btn btn-default" type="reset" style="display:none">重构新资源</button>
						</td>
				
					</tr>
				</table>
			</form>
		</div>
		<!--记录盒子  -->
		<!-- <div class="recordBox move">
			missioning
			<div class="missionRecord">
				<h2>任务进度</h2>
				<div>
					<p>项目</p>
					<p>进度</p>
					<p>状态</p>
				</div>
				<ul>
				</ul>
			</div>
			missioning
			<div class="logRecord">
				<h2>执行日志</h2>
				<span class="clearLog">清除日志<i
					class="glyphicon glyphicon-trash"></i></span>
				<textarea readonly="readonly" class="logBoard" placeholder="还没有日志记录"></textarea>
			</div>
		</div> -->
		<!--记录盒子  -->
	</div>
</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/content.js"></script>
<script src="js/plugins/treeview/bootstrap-treeview.min.js"></script>
<!-- Sweet alert -->
<script src="js/plugins/sweetalert/sweetalert.min.js"></script>
 <!-- Web Uploader -->
 <script type="text/javascript">
      // 添加全局站点信息
      var BASE_URL = 'js/plugins/webuploader';
      var IMG_SERVICE="json/img_uploadCateImg";
  </script>
 <script src="js/plugins/webuploader/webuploader.min.js"></script>
 <script src="js/plugins/webuploader/webuploader-demo.js"></script>
<%-- <script>
	var webSocketUri="<%=serverPath%>webSocketServer";
</script> --%>
<!--流动布局-->
<script src="js/plugins/layout/pinterest_grid.js"></script>
<script src="js/admin/addCate.js" type="text/javascript"></script>
<script src="js/move.js" type="text/javascript"></script>
</html>
<!-- 完成日期：2017-02-27 15:30 -->