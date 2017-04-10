<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serverPath = "ws://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批量添加</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/admin/addCate.css" rel="stylesheet" type="text/css">
<link href="css/animate.css" rel="stylesheet">
<style type="text/css">
body {
	background: #ecf0f5;
}

.format input {
	font-weight: bold;
	font-size: 18px;
}
.norm-help{
	cursor:pointer;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="sourceInfor move col-sm-4">
			<form method="post" id="scanForm">
				<table class="table">
					<caption>
						<h2>批量美食信息扫描</h2>
					</caption>
					<tr>
						<td class="col-sm-12">
							<div class="input-group">
								<div class="input-group-addon">扫描路径</div>
								<input type="text" class="form-control" name="path"
									placeholder="填写待扫描根路径[例:D:/abc]" data-toggle="tooltip"
									data-placement="top" title="" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="col-sm-12">
							<div class="input-group">
								<div class="input-group-addon">扫描层数</div>
								<div class="input-group" style="position: relative;"
									id="scanLevel">
									<span class="input-group-addon minus" disabled><i
										class="glyphicon glyphicon-minus"></i></span> <input type="text"
										class="form-control" value="1" placeholder="填写资源扫描的层数" name="level"
										style="font-size: 25px; text-align: center;" maxlength="2"
										maxValue="20" sourceValue="1"> <span
										class="input-group-addon add"><i
										class="glyphicon glyphicon-plus"></i></span>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="position:relative" class="col-sm-12">
							<div class="input-group">
								<div class="input-group-addon">扫描规范</div>
								<div class="mc norm">
									<p class="on">自定义</p>
									<p class="control"></p>
									<p class="off">默认</p>
									<input type="hidden" value="0" name="norm" class="mcControl">
								</div>
							</div> <span class="glyphicon glyphicon-info-sign readme"
							data-toggle="tooltip" data-placement="top" title="可使用自定义规范"
							style="position:absolute;left:200px;top:20px;"></span>
						</td>
					</tr>
					<tr>
						<td class="col-sm-12">
							<div class="input-group">
								<div class="input-group-addon">
									规范定义<br />
									<br /> <span class="glyphicon glyphicon-flash readme norm-help" title="自定义规范快速入门"></span>
								</div>
								<ul class="format" style="background:none;padding:0;margin:0">
									<li>
										<div class="input-group">
											<div class="input-group-addon">打折折扣</div>
											<input class="form-control" value="9.5"
												default-data="9.5" disabled source-data="9.5" name="price_discount"/>
										</div>
									</li>
									<li>
										<div class="input-group">
											<div class="input-group-addon">信息文件(任意.后缀)</div>
											<input class="form-control" value="*.txt"
												default-data="*.txt" disabled name="norm_infor"/>
										</div>
									</li>
									<li>
										<div class="input-group">
											<div class="input-group-addon">图片格式(后缀)</div>
											<input class="form-control" value="img"
												default-data="img" disabled name="norm_img"/>
										</div>
									</li>
									<li>
										<div class="input-group">
											<div class="input-group-addon">属性分割符(K/V)</div>
											<input class="form-control" value="/"
												default-data="/" disabled name="norm_property"/>
										</div>
									</li>
									<li>
										<div class="input-group">
											<div class="input-group-addon">分类分割符</div>
											<input class="form-control" value="|"
												default-data="|" disabled name="norm_category"/>
										</div>
									</li>
								</ul>
							</div>
						</td>
					</tr>
					<tr>
						<td style="text-align: center">
							<button class="btn btn-primary" id="scan" title="">开始扫描</button>
						</td>
					</tr>
					<tr class="norm-help-box" style="display:none">
						<td><div class="alert alert-success">打折折扣：默认为9.5折，设置值应该大于0小于10</div></td>
					</tr>
					<tr class="norm-help-box" style="display:none">
						<td><div class="alert alert-success">信息文件：支持任意格式文本文件，默认为*.txt，表示任意名称的txt文本，可以使用指定名称指定后缀文本</div></td>
					</tr>
					<tr class="norm-help-box" style="display:none">
						<td><div class="alert alert-success">图片文件：默认为img，表示支持所有常用格式，可以使用指定格式图片</div></td>
					</tr>
					<tr class="norm-help-box" style="display:none">
						<td><div class="alert alert-success">属性分割符：默认为/，可以使用指定分割符号</div></td>
					</tr>
					<tr class="norm-help-box" style="display:none">
						<td><div class="alert alert-success">分类分割符：默认为|，可以使用指定分割符号</div></td>
					</tr>
				</table>
			</form>
		</div>
		<!--记录盒子  -->
		<div class="recordBox move">
			<!-- missioning -->
			<!-- <div class="missionRecord">
				<h2>任务进度</h2>
				<div>
					<p>项目</p>
					<p>进度</p>
					<p>状态</p>
				</div>
				<ul>
				</ul>
			</div> -->
			<!-- missioning -->
			<div class="logRecord">
				<h2>执行日志</h2>
				<span class="clearLog">清除日志<i
					class="glyphicon glyphicon-trash"></i></span>
				<textarea class="form-control logBoard" readonly="readonly"
					placeholder="还没有日志记录" style="height:300px;margin-left:4px;"></textarea>
			</div>
		</div>
		<!--记录盒子  -->
	</div>
</body>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/move.js" type="text/javascript"></script>
<script src="js/websocket.js" type="text/javascript"></script>
<script src="js/admin/multipleChoice.js" type="text/javascript"></script>
<script type="text/javascript">
	var webSocketUri="<%=serverPath%>commServer.server";
$(function(){
	$(".norm").click(function(){
		if($(this).find("input").val()=='0'){
			$(".format li input").attr("disabled",false);
		}else{
			var inputs = $(".format li input");
			for(var i=0;i<inputs.length;i++){
				$(inputs[i]).val($(inputs[i]).attr("default-data"));
			}
			$(".format li input").attr("disabled",true);
		}
	})
	$("input[name=price_discount]").keyup(function(e){
		if(/^[0-9.]{0,6}$/.test(this.value)){
			$(this).attr("source-data",this.value);
		}else{
			this.value=$(this).attr("source-data");
		}
	})
	$(".norm-help").click(function(){
		$(".norm-help-box").toggle();
	})
	 /*批量添加资源*/
 	$("#scanForm").submit(function(){
	 var path = $("input[name=path]")[0];
	 var level = $("#scanLevel input")[0];
	 if(path.value==''){
		 tooltipUtil(path,"路径不能为空[例：D:/abc]");
		 return false;
	 }
	 if(webSocket==null){
		 createLog("上传服务器未连接，无法进行扫描操作，请刷新网页重连");
		 tooltipUtil(this, "上传服务器未连接,无法进行操作");
		 return false;
	 }
	 var discount = $("input[name=price_discount]").val();
	 if(!/^[0-9]([.]{0}|[.]{1}[0-9])$/.test(discount)){
	 	tooltipUtil($("input[name=price_discount]"), "格式不正确(9.5)");
	 	return false;
	 }
	 if(!/^.+[.]{1}.+/.test($("input[name=norm_infor]").val())){
	 	tooltipUtil($("input[name=norm_infor]"), "格式不正确(*.txt)");
	 	return false;
	 }
	 if(level.value==''){
		 level.value = 1;
	 }else if(parseInt(level.value)<1){
		 level.value = 1;
	 }
	 $.post("json/scancate?socketid="+currentID,$('#scanForm').serialize(),function(data){
		 if(data!=null){
			 data = eval("("+data+")");
			 if(data.message){
				 createLog(data.log);
			 }else{
				 createLog("扫描添加失败："+data.cause);
			 }
		 }
	 })
	 return false;
 })
 /*层数控制*/
 //输入数量
	$("#scanLevel input").keyup(function(e){
		if(/^[0-9]{1,4}$/.test(this.value)){
			var maxValue=parseInt($(this).attr("maxValue"));
			if(this.value>maxValue){
				$(this).val(maxValue);
				$(this).attr("sourceValue",this.value);
			}else if(this.value<1){
				$(this).val(1);
				$(this).attr("sourceValue",this.value);
			}
		}else{
			$(this).val($(this).attr("sourceValue"));
		}
	})
	//数量减少
	$("#scanLevel .minus").click(function(){
		var val = parseInt($("#scanLevel input").val())-1;
		if(val<1)val=1;
		$("#scanLevel input").val(val);
	})
	//数量增加
	$("#scanLevel .add").click(function(){
		var maxValue=$("#scanLevel input").attr("maxValue");
		var val = parseInt($("#scanLevel input").val())+1;
		if(val>maxValue)val=maxValue;
		$("#scanLevel input").val(val);
	})
	$(".readme").mouseover(function(){
		$(this).tooltip("show");
	})
	$(".clearLog").click(function(){
		$(".logBoard").html("");
	})
	})
	//显示执行日志
function createLog(text){
	$(".logBoard").append("----"+text+"----\r\n");
	var content = $(".logBoard")[0];
	content.scrollTop=content.scrollHeight;	//让滚动条在底部
}
//提示工具
function tooltipUtil(el,title){
	$(el).attr("data-toggle","tooltip").attr("data-placement","top").attr("title",title);
	 $(el).tooltip("show");
	 setTimeout(function(){
		 $(el).tooltip("destroy");
	 },3000)
}
function MissionState(){}
function MissionPlan(){}
</script>
</html>