<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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

<title>状态管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=4.1.0" rel="stylesheet">
<!-- jqgrid-->
<link href="css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
<!-- Sweet Alert -->
<link href="css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
/* 自动填充 */
.autoComplate{
	background:#FFF;
	position:absolute;
	z-index:100000;
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
<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated flipInY">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">资源更新</h4>
        </div>
			<div class="modal-body">
					<form id="InforForm" method="post" autocomplete="off" class="animated">
					<table class="table">
						<input type="hidden" class="id" name="status.id"/>
					<tr>
						<td>
							<div class="input-group">
								<div class="input-group-addon">类型</div>
								<input class="form-control type auto-type" type="text" name="status.type" placeholder="类型请用全英文大写">
								<div class="list-group autoComplate">
								<p>查询结果：总&nbsp;<strong><span class="searcRresult">0</span></strong>&nbsp;条记录</p>
							</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
						<div class="input-group" data-toggle="popover" data-placement="top" data-content="资源不能为空">
							<span class="input-group-addon">名称</span>
								<input class="form-control name" type="text" name="status.name" placeholder="名称请用全英文大写">
						</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="input-group">
								<div class="input-group-addon">描述</div>
								<input type="text" class="form-control decription" placeholder="描述(长度不超过20位)" name="status.decription" />
							</div>
						</td>
					</tr>
					<tr>
						<td style="text-align: center">
							<button class="btn btn-primary" type="submit"
								data-toggle="tooltip" data-placement="top" title="点击开始">更新到数据库</button>
							<button class="btn btn-primary" type="button" data-dismiss="modal">关闭</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
		</div>
	</div>
	</div>
</body>
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<!-- Peity -->
<script src="js/plugins/peity/jquery.peity.min.js"></script>

<!-- jqGrid -->
<script src="js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>
<!-- Sweet alert -->
<script src="js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- Bootstrap-Treeview plugin javascript -->
<!-- <script src="js/md5/spark-md5.min.js" type="text/javascript"></script> -->
<script type="text/javascript">
var AUTOURL="";
/**
 * 六画
 */
$(function() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list").jqGrid({
		url : "json/status_list",
		contentType : 'application/json',
		mtype : "post",
		datatype : "json",
		prmNames : {
			search : "search"
		},
		jsonReader : {
			sourcedata : "sourcedata"
		},
		height : "auto",
		autowidth : true,
		shrinkToFit : true,
		rownumbers : true,
		rownumWidth : 40,
		rowNum : 15,
		rowList : [ 10, 15, 20, 30 ],
		colNames : [ 'id', '类型', '名称','描述' ],
		colModel : [
				{
					name : 'id',
					index : 'id',
					hidden : true
				},{
					name : 'type',
					index : 'type',
					width : 50,
					align : "center",
					sorttype : "string",
					search : true,
					searchoptions : {
					sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
									'cn', 'nc' ]
					}
				},
				{
					name : 'name',
					index : 'name',
					width : 40,
					align : "center",
					search : true,
					searchoptions : {
					sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
									'cn', 'nc' ]
					}
				},
				 {
					name : 'decription',
					index : 'decription',
					editable : true,
					width : 100,
					search : true,
					searchoptions : {
					sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
									'cn', 'nc' ]
					}				} ],
		pager : "#pager_list",
		viewrecords : true,
		caption : "状态管理",
		add : true,
		edit : true,
		addtext : 'Add',
		edittext : 'Edit',
		multiselect : true,
		hidegrid : true
	});
	// Add selection
	$("#table_list").setSelection(4, true);

	// Setup buttons
	$("#table_list").jqGrid('navGrid', '#pager_list', {
		edit : false,
		add : false,
		del : false,
		search : true
	}, {
		height : 200,
		reloadAfterSubmit : true
	});
	// 向表格添加删除按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "删除",
		buttonicon : "glyphicon glyphicon-trash",
		position : "first",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getGridParam", "selarrrow");
			if (ids == '') {
				parent.layer.msg("请选择要删除的行");
				return;
			}
			parent.layer.confirm('确认删除已选中的数据吗？', {
				btn : [ '马上删除', '让我再想想' ], // 按钮
				shade : [ 0.2, '#000' ]
			// 不显示遮罩
			}, function() {
				deleteData(ids);
			}, function() {
				parent.layer.msg('已取消', {
					shift : 6
				});
			});
		}
	})
	// 向表格添加编辑按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "编辑",
		buttonicon : "glyphicon glyphicon-edit",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getGridParam", "selarrrow");
			if (ids == '') {
				parent.layer.msg("请选择要编辑的行");
				return;
			} else if (ids.toString().indexOf(",") != -1) {
				parent.layer.msg("一次只能选择一行进行编辑");
				return;
			} else {
				fillSourceData($("#table_list").getRowData(ids));
				$("#editModal .modal-title").html("更新展示资源");
				$("#editModal button[type=submit]").html("确认更新");
				$("#editModal").modal("show");
			}
		},
		position : "first"
	})
	// 向表格添加编辑按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "添加",
		buttonicon : "glyphicon glyphicon-plus",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getDataIDs", "none");
			$("#editModal .modal-title").html("添加状态");
			$("#editModal button[type=submit]").html("确认添加");
			$("#InforForm input").val("");
			$("#editModal").modal("show");
		},
		position : "first"
	})
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
	});
		 //表单提交 
   $("#InforForm").submit(function(){
	   var isPass = true;
	   if($("input.type").val()==''){
		   tooltipUtil($("#InforForm .type"),"类型不能为空");
		   isPass = false;
	   }else if(!/^[A-Z]{1,10}$/.test($("input.type").val())){
	   		tooltipUtil($("#InforForm .type"),"格式不正确:全大写英文并且长度不超过10位");
		   isPass = false;
	   }
	   if($("input.name").val()==''){
		   tooltipUtil($("#InforForm .name"),"名称不能为空");
		   isPass = false;
	   }else if(!/^[A-Z]{1,20}$/.test($("input.name").val())){
	   		tooltipUtil($("#InforForm .name"),"格式不正确:全大写英文并且长度不超过20位");
		   isPass = false;
	   }
	   if($("input.decription").val()==''){
		   tooltipUtil($("#InforForm .decription"),"描述不能为空");
		   isPass = false;
	   }else if($("input.decription").val().length>20){
	   	 	tooltipUtil($("#InforForm .decription"),"长度不能超过20位");
		   isPass = false;
	   }
	   if(isPass){
		   updateForm();//表单进行提交
	   }/* else{
		   tooltipUtil($(this).find("button[type=submit]"),"信息填写不完善");
	   } */
	   return false;
   })
   $(".auto-type").blur(function(){
	 	$(".autoComplate").hide();
	 	$(".autoComplate a.active").removeClass("active");
 }	).keyup(function(event){
 	if(event.which==40){
 		var aTags = $(".autoComplate a");
 		$(".autoComplate a").removeClass("active");
 		var aLength = $(".autoComplate a").length;
 		if(aLength<6&&currentCursorRow==aLength-1||currentCursorRow==5){
 			currentCursorRow=-1;
 		}
 		$(aTags[++currentCursorRow]).addClass("active")
 	}else if(event.which==38){
 		var aTags = $(".autoComplate a");
 		$(".autoComplate a").removeClass("active");
 		var aLength = $(".autoComplate a").length;
 		if(currentCursorRow<=0){
 			if(aLength<6)currentCursorRow=aLength;
 			else currentCursorRow=6;
 		}
 		$(aTags[--currentCursorRow]).addClass("active")
 	}else if(event.keyCode==13&&$(".autoComplate a").length>0){
 		var currentA = $(".autoComplate a.active");
 		if(currentA.length<1){	//如果此时没有选中项则默认选则第一项
 			currentA=$(".autoComplate a:first");
 		}
 		$(".auto-type").val(currentA[0].innerHTML);
 		$(".autoComplate").hide();	//隐藏自动提示
 		$(".autoComplate a.active").removeClass("active");
 		$(this).attr("source-data","");
 		return false;
 	}else{
 		if(this.value.trim()==''){
 			$(".autoComplate a").remove();
 			$(".autoComplate").hide();
 			$(".autoComplate a.active").removeClass("active");
 		}else if(this.value.trim()!=$(this).attr("source-data")){
 			currentCursorRow=-1;
 			autoComplate(this);
 		}
 		$(this).attr("source-data",this.value.trim());
 		return false;
 	}
 	})
})
 /*自动填充*/
 //自动完成
var currentCursorRow=-1;
function autoComplate(el){
	$.post("json/status_findType","key="+el.value.trim(),function(data) {
		$(".autoComplate a").remove();
		$(".autoComplate").show();
		if(!(data instanceof Object))data = eval("("+data+")");
		$(".searcRresult").html(data.length);
			$(".autoComplate").css("width",$(".auto-type")[0].offsetWidth);
			for(var i=0;i<7&&i<data.length;i++){
				fillAutoComplateData(data[i]);
			}
			$(".autoComplate a").unbind();
			$(".autoComplate a").mouseover(function(){
				$(".autoComplate a").removeClass("active");
				$(this).addClass("active");
				currentCursorRow = $(".autoComplate a").index(this);//获取当前下标
			}).mousedown(function(){
				el.value=$(this).attr("data-name");
				$(".autoComplate").hide();
				return false;
			}).click(function(){
				el.value=$(this).attr("data-name");
				$(".autoComplate").hide();
				return false;
			})
			//sysnchronized = false;
		},"json"
	)
}
function fillAutoComplateData(data){
	$(".autoComplate p").before($("<a class='list-group-item' data-name='"+data+"'>"+(data)+"</a>"));
}
//提示工具
function tooltipUtil(el,title){
	$(el).attr("data-toggle","tooltip").attr("data-placement","top").attr("title",title);
	 $(el).tooltip("show");
	 setTimeout(function(){
		 $(el).tooltip("destroy");
	 },3000)
}
function deleteData(ids) {
	$.post("json/status_delete", "status.id=" + ids, function(data) {
		data = eval("(" + data + ")");
		if (data.result) {
			if (ids.toString().indexOf(",") != -1) {
				var idArr = ids.toString().split(",");
				for (var i = 0; i < idArr.length; i++)
					$("#table_list").delRowData(idArr[i]);
			} else {
				$("#table_list").delRowData(ids);
			}
			parent.layer.msg('已删除' + data.num + '个记录', {
				icon : 1
			});
			$("#table_list").trigger("reloadGrid");
		} else {
			parent.layer.msg('删除失败:'+data.cause, {
				icon : 2
			});
		}
	})
}
// 填充更新的资源数据
function fillSourceData(obj) {
	$("#InforForm input").val("");
	$("#InforForm input.id").val(obj.id);
	$("#InforForm input.type").val(obj.type);
	$("#InforForm input.name").val(obj.name);
	$("#InforForm input.decription").val(obj.decription);
}
//开始当前资源ID的上传任务 
function updateForm(){
	$.post("json/status_update",$('#InforForm').serialize(),function(data) {
		if (!(data instanceof Object)) data = eval("(" + data + ")");
			if(data.result){
				$("#editModal").modal("hide");
				parent.layer.msg('操作成功', {icon : 1});
				$("#table_list").trigger("reloadGrid");
			}else{
				parent.layer.msg('操作失败：'+data.cause, {icon : 6});
			}
		})
}
</script>
</html>
