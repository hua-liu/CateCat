$(function(){
	/*继续购物*/
	gotoByCate();
	/*取消订单*/
	cancelOrders();
	/*付款验证密码*/
	confirmOrders();
	/*确认收货验证密码*/
	Confirmreceipt();
})

//继续购物跳转
function gotoByCate(){
	$(".goto-Bycate").unbind();
	$(".goto-Bycate").bind("click",function(){
		window.location="cate-list.action";
	})
}

/*取消订单*/
function cancelOrders(){
	$(".cancel-orders").unbind();
	$(".cancel-orders").bind("click",function(){
		var _this = this;
		swal({ 
	        title: "您确定要取消吗？",  
	        text: "您确定要取消该订单吗？",  
	        type: "warning", 
	        showCancelButton: true, 
	        closeOnConfirm: false, 
	        confirmButtonText: "是的，我要取消", 
	        confirmButtonColor: "#ec6c62" 
	    }, function() { 
	    	$.post("json/orders_ordersToCart","ordersId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "已成功取消订单！", "success");
					setTimeout(function(){
						gotoOrdersList();
						},3000)
				}else{
					swal("OMG", "未登录用户，请去登录!", "error");
				}
			},"json");
	    }); 
	});
}

/*确认订单,验证密码：正确跳转到ordersList页面否者提示密码错误*/
function confirmOrders(){
	$(".payMent-orders").unbind();
	$(".payMent-orders").bind("click",function(){
		var _this = this;
		swal({   
			title: "支付密码",   
			text: "输入支付密码并确认:",   
			type: "input",
			showCancelButton: true,   
			closeOnConfirm: false,   
			animation: "slide-from-top",   
			inputPlaceholder: "填写支付密码" 
		}, function(inputValue){ 
			if (inputValue === false) return false;      
			if (inputValue === "") {     
				swal.showInputError("支付密码不能为空!");     
				return false   
			}  
			$.post("json/orders_myOrdersConfirmPayPassword","payPassword="+inputValue+"&ordersId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "付款成功!", "success");
					setTimeout(function(){
						gotoOrdersList();
					},3000)
				}else if(!data.result){
					swal("OMG", "支付密码错误，请重新输入！", "error");
				}else{
					swal("OMG", "未登录用户，请去登录!", "error");
				}
			},"json");
		});
		$(".showSweetAlert fieldset input").attr("type","password")
	});
}

/*支付密码输入正确跳转到ordersList*/
function gotoOrdersList(){
	$.post("json/ordersList_myOrdersPage","page=1",function(data){
		console.log(data);
		if(!(data instanceof Object))data = eval("("+data+")");
		if(data.result){
			window.location="ordersList_myOrdersToPage.action?page=1";
		}else{
			swal("OMG", "未登录用户，请去登录!", "error");
		}
	},"json");
}

/*确认收货*/
function Confirmreceipt(){
	$(".Confirm-receipt").unbind();
	$(".Confirm-receipt").bind("click",function(){
		var _this = this;
		swal({   
			title: "支付密码",   
			text: "输入支付密码并确认:",   
			type: "input",
			showCancelButton: true,   
			closeOnConfirm: false,   
			animation: "slide-from-top",   
			inputPlaceholder: "填写支付密码" 
		}, function(inputValue){ 
			if (inputValue === false) return false;      
			if (inputValue === "") {     
				swal.showInputError("支付密码不能为空!");     
				return false   
			}  
			$.post("json/ordersList_Confirmreceipt","payPassword="+inputValue+"&ordersId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "收货成功！", "success");
					setTimeout(function(){
						gotoOrdersList();
					},3000)
				}else if(!data.result){
					swal("OMG", "支付密码错误，请重新输入！", "error");
				}else{
					swal("OMG", "未登录用户，请去登录!", "error");
				}
			},"json");
		});
	});
}
