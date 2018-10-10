$(function(){
		//当输入框发生改变
		$(".priceInput").change(function(){
			$(this).attr("data-num",this.value)
			calcSubPrice(this,this.value);//计算每项价
		});
		//当点击数量加减
		/* $(".spinner-btn-mod").click(function(){
			calcSubPrice($(this).parent().find("input"));//计算每项价
		}) */
		$(".spinner-btn-mod[data-spin=down]").click(function(){
			var t=$(this).parent().find('input');
			var i=parseInt($(t).attr("data-num"))-1;
			calcSubPrice(t,i);//计算每项价
			changeCartItem(t);
		});
		$(".spinner-btn-mod[data-spin=up]").click(function(){ 
			var t=$(this).parent().find('input'); 
			var i=parseInt($(t).attr("data-num"))+1;
			calcSubPrice(t,i);//计算每项价
			changeCartItem(t);
		});
		gotoByCate();
		removeCartItem();
		removeAllCartItem();
		gotoByOrder();
		addFavItem();
});
/* 计算每项价格 */
function calcSubPrice(el,num){
	var price = $(el).attr("data-price");
	console.log(num)
	console.log(price)
	if(!/[0-9]+/.test(num) || num < 1){
		num=1;
	}
	var subPrice = parseInt(num)*parseFloat(price);
	$(el).attr("data-num",num).html(num);
	$(el).parents("tr").find(".cart-subtotal-price").attr("data-price",subPrice).html("$"+subPrice);
	calcTotalPrice();//计算总价 
	
}
/* 计算总价 */
function calcTotalPrice(){
	var subtotal = $(".cart-subtotal-price");
	var totalPrice=0;
	for(var i=0;i<subtotal.length;i++){
		totalPrice += parseFloat($(subtotal[i]).attr("data-price"));
	}
	console.log(totalPrice);
	$(".cart-total").html("$"+totalPrice);
	
}
/*改变session中购物项*/
function changeCartItem(el){
	var CartItemId = $(el).attr("data-id");
	var CartItemCount = $(el).attr("data-num");
	$.post("json/cart_changeCarItem","cartItemId="+CartItemId+"&count="+CartItemCount,function(data){
		console.log(data);
		if(!(data instanceof Object))data = eval("("+data+")");
		if(!data.result){
			swal("OMG!", "未登录的用户，请去登录", "error");
		}
	},"json");
}
//继续购物跳转
function gotoByCate(){
	$(".goto-Bycate").unbind();
	$(".goto-Bycate").bind("click",function(){
		window.location="cate-list.action";
	})
}

//跳转到订单
function gotoByOrder(){
	$(".goto-ByOrders").unbind();
	$(".goto-ByOrders").bind("click",function(){
		$.post("json/orders_ordersPage","ordersId="+$(this).attr("data-id"),function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				window.location="orders_ordersToPage.action";
			}else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
	});
}

//删除单个购物项
function removeCartItem(){
	$(".remove-cartItem").unbind();
	$(".remove-cartItem").bind("click",function(){
		var _this = this;
		swal({ 
	        title: "您确定要删除吗？",  
	        text: "您确定要删除这件商品吗？",  
	        type: "warning", 
	        showCancelButton: true, 
	        closeOnConfirm: false, 
	        confirmButtonText: "是的，我要删除", 
	        confirmButtonColor: "#ec6c62" 
	    }, function() { 
	    	$.post("json/cart_removeCartItem","cartItemId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "已成功删除商品！", "success");
					setTimeout(function(){
						location.reload();
						},1000)
				}else{
					swal("OMG", "删除操作失败!", "error");
				}
			},"json");
	    }); 
	});
}

//清空购物车
function removeAllCartItem(){
	$(".remove-allcartItem").unbind();
	$(".remove-allcartItem").bind("click",function(){
		var _this=this;
		swal({ 
	        title: "您确定要清空吗？",  
	        text: "您确定要清空全部商品吗？",  
	        type: "warning", 
	        showCancelButton: true, 
	        closeOnConfirm: false, 
	        confirmButtonText: "是的，我要清空", 
	        confirmButtonColor: "#ec6c62" 
	    }, function() { 
	    	$.post("json/cart_removeAllCartItem","cartId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "已成功清空购物车！", "success");
					setTimeout(function(){
						location.reload();
						},1000)
				}else{
					swal("OMG", "清空操作失败!", "error");
				}
			},"json");
	    }); 
	});
}

//添加到我的收藏
function addFavItem(){
	$(".add-fav").unbind();
	$(".add-fav").bind("click",function(){
		$.post("json/fav_addFav","cateId="+$(this).attr("data-id")+"&count=1",function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result == true){
				swal("Good!", "成功添加到我的收藏！", "success");
			}else if(data.result == 'warning'){
				swal("OMG","请勿重复添加","warning");
			}	
			else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
	})
}
