/**
 * 钟思平
 * 订单管理
 */
$(function() {
	$.jgrid.defaults.styleUI = 'Bootstrap';
	$("#table_list").jqGrid({
		url : "json/ordersManageList",
		contentType : 'application/json',
		mtype : "post",
		datatype : "json",
		prmNames : {
			page : "page", //表示请求页码的参数名称 
			rows : "limit",// 表示请求行数的参数名称
		},
		jsonReader : {
			root: "list",   // json中代表实际模型数据的入口  
			page: "page",   // json中代表当前页码的数据  
			total: "totalPage", // json中代表页码总数的数据  
			records: "totalCount", // json中代表数据行总数的数据  
		},
		height : "auto",
		autowidth : true,
		shrinkToFit : true,
		rownumbers : true,
		rownumWidth : 40,
		rowNum : 15,
		colNames : [ 'id', '订单创建时间','美食数量','订单价格','订单状态', '送货地址'],
		colModel : [
				{
					name : 'id',
					index : 'id',
					hidden : true
				},
				{
					name : 'createOrdersTime',
					index : 'createOrdersTime',
					editable : true,
					width : 25,
					sorttype : "string",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
								'cn', 'nc' ]
					}
				},
				{
					name : 'cartItemCount',
					index : 'cartItemCount',
					width : 15,
					align : "center",
					sorttype : "int",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne' ]
					}
				},
				{
					name : 'total',
					index : 'total',
					width : 15,
					sorttype : "float",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'lt', 'le', 'gt', 'ge']
					}
				},
				{
					name : 'status.decription',
					index : 'status.decription',
					editable : true,
					width : 15,
					sorttype : "string",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
								'cn', 'nc' ]
					}
				},
				{
					name : 'address',
					index : 'address',
					editable : true,
					width : 50,
					sorttype : "string",
					search : true,
					searchoptions : {
						sopt : [ 'eq', 'ne', 'bw', 'bn', 'ew', 'en',
								'cn', 'nc' ]
					}
				} ],
		pager : "#pager_list",
		viewrecords : true,
		caption : "订单管理",
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
	// 向表格添加编辑按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "修改配送地址",
		buttonicon : "glyphicon glyphicon-edit",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getGridParam", "selarrrow");
			if (ids == '') {
				parent.layer.msg("请选择要修改 的行");
				return;
			} else if (ids.toString().indexOf(",") != -1) {
				parent.layer.msg("一次只能选择一行进行修改");
				return;
			} else {
				//iframe窗
				parent.layer.open({
					type: 2,
		            title: '更改配送地址',
		            shadeClose: true,
		            shade: false,
		            maxmin: true, //开启最大化最小化按钮
		            area: ['50%', '50%'],
		            content: 'admin_orders_Orders_updateOrdersAddress?ordersId='+ids
				});


			}
		},
		position : "first"
	})
	
	
	//向表格中添加发货按钮
	$("#table_list").navButtonAdd('#pager_list', {
		caption : "",
		title : "发货",
		buttonicon : "glyphicon glyphicon-plane",
		position : "first",
		onClickButton : function() {
			// 多行选取
			var ids = $("#table_list").jqGrid("getGridParam", "selarrrow");
			if (ids == '') {
				parent.layer.msg("请选择要发货的订单");
				return;
			}else if(ids.toString().indexOf(",") != -1){
				parent.layer.msg("一次只能选择单个订单进行发货");
				return;
			}else{
				parent.layer.confirm('确认发货已选中的订单吗？', {
					btn : [ '发货', '让我再想想' ], // 按钮
					shade : [ 0.2, '#000' ]
				// 不显示遮罩
				}, function(index) {
					updateOrdersStatus(ids);
					layer.close(index);
				}, function() {
					parent.layer.msg('已取消', {
						shift : 6
					});
				});
			}
		}
	})
	
	// Add responsive to jqGrid
	$(window).bind('resize', function() {
		var width = $('.jqGrid_wrapper').width();
		$('#table_list').setGridWidth(width);
	});
})
//提示工具
function tooltipUtil(el,title){
	$(el).attr("title",title);
	 $(el).tooltip("show");
	 setTimeout(function(){
		 $(el).tooltip("destroy");
	 },3000)
}
function updateOrdersStatus(ids) {
	$.post("json/orders_backstageDeliverOrders", "ordersId=" + ids, function(data) {
		if(!(data instanceof Object))data = eval("(" + data + ")");
		if(data.result == true){
			parent.layer.msg('发货成功', {
				icon : 1
			});
		}else if(data.result == false){
			parent.layer.msg('发货失败:'+data.cause, {
				icon : 2
			});
		}else if (data.result == 'NOPAID') {
			parent.layer.msg('发货失败:'+data.cause, {
				icon : 2
			});
		}else if(data.result == 'NORECEIVE'){
			parent.layer.msg('发货失败:'+data.cause, {
				icon : 2
			});
		}else if(data.result == 'RECEIVE'){
			parent.layer.msg('发货失败:'+data.cause, {
				icon : 2
			});
		}
	},"json")
}
