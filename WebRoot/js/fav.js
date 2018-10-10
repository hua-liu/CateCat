$(function(){
	$(".image-add-mod").unbind("click");
	$(".image-add-mod").bind("click",function(){
		console.log(this)
		$("#img").attr("src","img_requestCateImg?type=max&id="+$(this).attr("data-id"));
		$('#showPic').modal('show');
	})
	addCartItem();
	addFavItem();
})
//添加到购物车
function addCartItem(){
	$(".add-cart").unbind();
	$(".add-cart").bind("click",function(){
		$.post("json/cart_addCart","cateId="+$(this).attr("data-id")+"&count=1",function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				swal("Good!", "成功添加到购物车！", "success");
			}
			else{
				swal("OMG!", "未登录的用户，请去登录", "error");
			}
		},"json");
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
}//删除单个购物项
function removeCartItem(){
	$(".remove-favItem").unbind();
	$(".remove-favItem").bind("click",function(){
		var _this = this;
		swal({ 
	        title: "您确定要移除收藏吗？",  
	        text: "您确定要移除这件商品吗？",  
	        type: "warning", 
	        showCancelButton: true, 
	        closeOnConfirm: false, 
	        confirmButtonText: "是的，我要移除", 
	        confirmButtonColor: "#ec6c62" 
	    }, function() { 
	    	$.post("json/fav_removeFavtItem","favItemId="+$(_this).attr("data-id"),function(data){
				console.log(data);
				if(!(data instanceof Object))data = eval("("+data+")");
				if(data.result){
					swal("操作成功!", "已成功移除商品！", "success");
					setTimeout(function(){
						location.reload();
						},1000)
				}else{
					swal("OMG", "移除操作失败!", "error");
				}
			},"json");
	    }); 
	});
}
