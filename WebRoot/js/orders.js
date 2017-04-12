$(function(){
	linkage();
	cancelOrders();
	confirmOrders();
})

/*省级联动*/
function linkage(){
	$("#city").click(function (e) {
		SelCity(this,e);
	});
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
					swal("操作成功!", "已成功取消订单,准备跳转到购物车！", "success");
					setTimeout(function(){
						window.location="cart_cartPage.action";
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
	if($("#city").val()==''){
		swal("订单提示！", "地址不能为空，请用户填写地址！", "warning");
	}
	$(".confirm-orders").unbind();
	$(".confirm-orders").bind("click",function(){
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
			$.post("json/orders_confirmPayPassword","payPassword="+inputValue+"&ordersId="+$(_this).attr("data-id")+"&userAddress="+$(".address-city").val(),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "付款成功,准备跳转到用户订单列表！", "success");
					setTimeout(function(){
						gotoOrdersList();
					},3000)
				}else if(!data.result){
					swal("OMG", "密码错误或者地址为空，清注意查看！", "error");
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