/**
 * 六画
 * 资源管理
 */
$(function() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list").jqGrid({
		url : "json/list_image_Image_defaultHeadPortrait",
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
		colNames : [ 'id', '预览', '图片地址'],
		colModel : [
				{
					name : 'id',
					index : 'id',
					hidden : true
				},{
					name : 'id',
					index : 'id',
					width : 40,
					align : "center",
					formatter :imgFmatter,
					search : false
				},
				{
					name : 'path',
					index : 'path',
					editable : true,
					search : false,
					formatter : "string"
				}],
		pager : "#pager_list",
		viewrecords : true,
		caption : "资源管理",
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
		search : false
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
		title : "添加",
		buttonicon : "glyphicon glyphicon-plus",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getDataIDs", "none");
			if(ids.length>=10){
				parent.layer.msg("最多只能添加10条展示");
				return;
			}
			$("#headPort-modal").modal("show");
		},
		position : "first"
	})
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
	});
   $('#headPort-modal').on('show.bs.modal', function (e) {
	   $(".image-crop > img").cropper("clear");
   })
})
function imgFmatter(cellvalue, options, rowObject) {
	if(cellvalue!=null){
		return "<img class='img' src='img_requestImg?type=max&id="+cellvalue+"' width='50px' onclick='showPic(this)'>";
	}else{
		return "";
	}
}
function showPic(_this){
	$("#img").attr("src",$(_this).attr("src"));
	$('#showPic').modal('show');
}
//提示工具
function tooltipUtil(el,title){
	$(el).attr("title",title);
	 $(el).tooltip("show");
	 setTimeout(function(){
		 $(el).tooltip("destroy");
	 },3000)
}
function deleteData(ids) {
	$.post("json/img_deleteImg", "id=" + ids, function(data) {
		if(data==''){
			parent.layer.msg('删除失败:头像正在被使用', {
				icon : 2
			});
			return;
		}
		data = eval("(" + data + ")");
		if (data.result) {
			parent.layer.msg('操作结果:成功 ' + data.success + ' 个记录,失败 '+data.failure+' 个记录', {
				icon : 1
			});
			if(data.success>0)
			$("#table_list").trigger("reloadGrid");
		} else {
			parent.layer.msg('删除失败:'+data.cause, {
				icon : 2
			});
		}
	}).error(function(){
		parent.layer.msg('删除失败:头像正在被使用', {
			icon : 2
		});
	})
}
