/**
 * 六画
 * 资源管理
 */
$(function() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list").jqGrid({
		url : "json/list_cate_Cate_bgm",
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
		colNames : [ 'id', '美食名称', '原价格','优惠价格','是否上线', '简述'],
		colModel : [
				{
					name : 'id',
					index : 'id',
					hidden : true
				},
				{
					name : 'name',
					index : 'name',
					editable : true,
					width : 50,
					sorttype : "string",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
								'cn', 'nc' ]
					}
				},
				{
					name : 'shopPrice',
					index : 'shopPrice',
					width : 30,
					sorttype : "float",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'lt', 'le', 'gt', 'ge']
					}
				},
				{
					name : 'marketPrice',
					index : 'marketPrice',
					width : 30,
					sorttype : "float",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'lt', 'le', 'gt', 'ge']
					}
				},
				{
					name : 'isOnline',
					index : 'isOnline',
					width : 30,
					align : "center",
					sorttype : "int",
					formatter : passFmatter,
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne' ]
					}
				},
				{
					name : 'about',
					index : 'about',
					editable : true,
					width : 100,
					search : true,
					searchoptions : {
						sopt : [ 'cn', 'nc' ]
					}
				} ],
		pager : "#pager_list",
		viewrecords : true,
		caption : "美食管理",
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
			}, function(index) {
				deleteData(ids);
				layer.close(index);
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
				//iframe窗
				parent.layer.open({
					type: 2,
		            title: '更新美食信息',
		            shadeClose: true,
		            shade: false,
		            maxmin: true, //开启最大化最小化按钮
		            area: ['100%', '100%'],
		            content: 'admin_cate_Cate_updateCateUi?id='+ids
				});


			}
		},
		position : "first"
	})
	// 向表格添加下线按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "强制下线",
		buttonicon : "glyphicon glyphicon-cloud-download",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getGridParam", "selarrrow");
			if (ids == '') {
				parent.layer.msg("请选择要强制下线的的美食");
				return;
			}
			parent.layer.confirm('确认将所选美食[强制下线]吗？', {
				btn : [ '马上下线', '让我再想想' ], // 按钮
				shade : [ 0.2, '#000' ]
			// 不显示遮罩
			}, function() {
				onOrOffData(ids,0);
			}, function() {
				parent.layer.msg('已取消', {
					shift : 6
				});
			});
		},
		position : "first"
	})
	// 向表格添加上线按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "强制上线",
		buttonicon : "glyphicon glyphicon-cloud-upload",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getGridParam", "selarrrow");
			if (ids == '') {
				parent.layer.msg("请选择要强制上线的的美食");
				return;
			}
			parent.layer.confirm('确认将所选美食[强制上线]售卖吗？', {
				btn : [ '马上上线', '让我再想想' ], // 按钮
				shade : [ 0.2, '#000' ]
			// 不显示遮罩
			}, function() {
				onOrOffData(ids,1);
			}, function() {
				parent.layer.msg('已取消', {
					shift : 6
				});
			});
		},
		position : "first"
	})
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
	});
})
function passFmatter(cellvalue, options, rowObject) {
	if (cellvalue == 0)
		return '0(未上线)';
	else
		return '1(已上线)';
}
function categoryFmatter(cellvalue, options, rowObject) {
	console.log(categoryData)
	var context = "";
	for(var i=0;i<cellvalue.length;i++){
		context += "<span data-id="+cellvalue[i].id+" style='margin-right:5px;'>"+cellvalue[i].name+"</span>";
	}
	return context;
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
	$.post("json/cate_delete", "id=" + ids, function(data) {
		if(data==''){
			parent.layer.msg('删除失败,该美食在其它地方被引用', {
				icon : 2
			});
			return;
		}
		if(!(data instanceof Object))data = eval("(" + data + ")");
		if (data.result && data.num > 0) {
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
	},"json")
}
function onOrOffData(ids,type) {
	$.post("json/cate_onlineOrOffline", "type="+type+"&id=" + ids, function(data) {
		if(!(data instanceof Object))data = eval("(" + data + ")");
		if (data.result && data.num > 0) {
			if (ids.toString().indexOf(",") != -1) {
				var idArr = ids.toString().split(",");
				for (var i = 0; i < idArr.length; i++)
					$("#table_list").delRowData(idArr[i]);
			} else {
				$("#table_list").delRowData(ids);
			}
				parent.layer.msg('已成功操作' + data.num + '个记录', {
				icon : 1
			});
			$("#table_list").trigger("reloadGrid");
		} else {
			if(data.num==0){
				parent.layer.msg('没有记录更改', {
					icon : 2
				});
			}else{
				parent.layer.msg('操作失败:'+data.cause, {
					icon : 2
				});
			}
		}
	},"json")
}
