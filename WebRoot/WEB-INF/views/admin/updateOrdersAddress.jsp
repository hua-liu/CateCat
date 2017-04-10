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
<title>更新订单配送地址</title>
<link href="css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="css/plugins/treeview/bootstrap-treeview.min.css" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/style.css?v=4.1.0" rel="stylesheet">
<!-- Sweet Alert -->
<link href="css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<!-- 省级联动 -->
<link href="css/linkage.css" rel="stylesheet">
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
.imgUl{
	padding:0;
}
.imgUl li{
	float:left;
	position:relative;
	margin-right:5px;
}
.cateImg{
	width:50px;
	cursor:pointer;
}
.closeImg{
	position:absolute;
	right:0;top:0;
	color:#E00;
	z-index:10;
	display:none;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="sourceInfor move col-sm-8">
			<div class="ibox float-e-margins categoryList">
                <div class="ibox-content">
                    <div id="treeview">
                    </div>
                </div>
            </div>
			<form id="sourceInforForm" method="post" autocomplete="off">
				<table class="table col-sm-8">
					<caption>
						<h2>订单信息</h2>
					</caption>
					<tr>
						<td colspan="2">
							<div class="input-group col-sm-12">
								<div class="input-group-addon">配送地址</div>
								<input type="text" style="width:500px;" id="city" class="form-control address-city" title="填写地址错误"
									placeholder="填写订单配送地址(不能为空)" name="city" data-id="${session.ordersManage.id}" value="${session.ordersManage.address}"/>
							</div>
						</td>
					</tr>
					<s:hidden name="session.ordersManage.id"/>
					<s:hidden name="session.ordersManage.address"/>
					<tr>
						<td style="text-align: center" colspan="2">
							<button class="btn btn-primary edit-ordersAddress" type="button" title="修改">修改</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/content.js"></script>
<script src="js/plugins/treeview/bootstrap-treeview.min.js"></script>
<!-- Sweet alert -->
<script src="js/plugins/sweetalert/sweetalert.min.js"></script>
<%-- <script>
	var webSocketUri="<%=serverPath%>webSocketServer";
</script> --%>
<!--流动布局-->
<script src="js/plugins/layout/pinterest_grid.js"></script>
<script src="js/move.js" type="text/javascript"></script>
<!-- 省级联动 -->
<script src="js/linkage/Popt.js"></script>
<script src="js/linkage/cityJson.js"></script>
<script src="js/linkage/citySet.js"></script>
<script type="text/javascript">
$(function(){
	linkage();
	editOrdersAddress();
})
/*省级联动*/
function linkage(){
	$(".address-city").unbind();
	$(".address-city").bind("click",function(e){
		SelCity(this,e);
	})
}
/*修改订单配送地址*/
function editOrdersAddress(){
	$(".edit-ordersAddress").unbind();
	$(".edit-ordersAddress").bind("click",function(){
		$.post("json/orders_updateOrdersAddressUi",
				"ordersId="+$(".address-city").attr("data-id")+
				"&userAddress="+$(".address-city").val(),function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				parent.layer.msg('已成功修改操作',{icon: 1});
			}else{
				parent.layer.msg('失败的修改操作',{icon: 2});
			}
		},"json");
	})
}

//提示工具
function tooltipUtil(el,title){
	$(el).attr("title",title);
	 $(el).tooltip("show");
	 setTimeout(function(){
		 $(el).tooltip("destroy");
	 },3000)
}
</script>

</html>