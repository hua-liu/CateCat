<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>美食管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=4.1.0" rel="stylesheet">
<link href="css/plugins/treeview/bootstrap-treeview.min.css" rel="stylesheet">
<!-- jqgrid-->
<link href="css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<!-- Sweet Alert -->
<link href="css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
/* 自动填充 */
/* .selectUserList{
	height:30px;
	cursor:pointer;
}
.selectUserList:hover{
	background:rgba(0,0,0,0.2);
}
.selectUserListChecked{
	height:30px;
	cursor:pointer;
	background:rgba(0,150,0,0.5);
}
.selectUserSearchInput{
	width:100%;
	float:left;
} */
/* .autoComplate{
	background:#FFF;
	position:absolute;
	z-index:10;
	top:32px;
	width:80%;
	display:none;
}
.autoComplate a{
	height:40px;
	line-height:20px;
	vertical-align: middle;
}
.autoComplate a:hover{
	/* background:#337AB7;
	color:#FFF; */
	cursor:pointer;
}
.autoComplate p{
	border:1px solid #CCCCCC;
	height:25px;
	line-height:25px;
	text-align:right;
	font-size:15px;
	padding:0 10px;margin:0;
}
/* 自动填充 */
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
.categoryUl{
    height:70px;
color:#555;
cursor:pointer;
}
.categoryUl li{
    background:rgba(0,0,0,0.5);
    color:#FFF;
    width:50px;
    border-radius:5px;
    padding:2px;margin:2px;
    text-align:center;
    position: relative;
    float:left;
    overflow:hidden;
    height:25px;
}
.categoryUl li i{
    position: absolute;
    right:0;top:0;
    font-size:12px;
}
.categoryUl li i:hover{
    color:#F00;
}
.fileBox,.missionRecord{
	display:none;
}
.missionRecord li{
	list-style:none;
}
.sign.success{
	color:#5CB85C;
}
.sign.failed{
	color:#D9534F;
}
.selectFile{
	overflow:hidden;
}
.cancalPlan{
	text-align:center;
	display:none;
} */
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox ">
					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list"></table>
							<div id="pager_list"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- <div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated flipInY">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">资源更新</h4>
        </div>
			<div class="modal-body">
				<iframe frameBorder=0 scrolling=no width="100%" height="100%" src="admin_uploadCate"></iframe>
			</div>
		</div>
	</div>
</div> -->
</body>
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<!-- Peity -->
<script src="js/plugins/peity/jquery.peity.min.js"></script>

<!-- jqGrid -->
<script src="js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<!-- 自定义js -->
<!-- Sweet alert -->
<script src="js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- Bootstrap-Treeview plugin javascript -->
<script src="js/admin/ordersManage.js" type="text/javascript"></script>
<script src="js/plugins/md5/spark-md5.min.js" type="text/javascript"></script>
</html>
