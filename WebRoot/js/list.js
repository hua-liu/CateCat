$(function(){
	/*获取分类下美食数量*/
	var item = $(".con-item");
	$.post("json/cate_categoryOfCateNum",function(data){
		if(data==null)return;
		if(!(data instanceof Object))data = eval("("+data+")");
		for(var i=0;i<item.length;i++){
			var num = data[$(item[i]).attr("data-id")];
			if(num==undefined)num=0;
			$(item[i]).find(".num").html(num);
		}
	},"json");
	/*/获取分类下美食数量*/
	$(".number").keyup(function(e){
		if(/^[0-9.]{0,3}$/.test(this.value)){
			$(this).attr("source-data",this.value);
		}else{
			this.value=$(this).attr("source-data");
		}
	})
	//判断是否带有分类信息
	var category = $("input.category").val();
	if(category!=''){
		var categorys = category.split(",");
		for(var i=0;i<categorys.length;i++){
			var $con_item = $(".con-item[data-id="+categorys[i]+"]").addClass("active");
			var con_item_name = $con_item.attr("data-text");
			var parent_id = $con_item.parents(".parent").attr("parent-id");
			if(con_item_name!=null&&con_item_name!=undefined&&con_item_name!=''){
				addConditionType(categorys[i],con_item_name,"category",parent_id);
			}
		}
	}
	//判断价格区间是否存在
	if($(".priceMin").val()>=0&&$(".priceMax").val()<999){
		addConditionType($(".priceMin").val(),$(".priceMax").val(),"price",null);
	}
	//显示行数更改事件
	$("#show-items").change(function(){
		if($("input.rows").val()!=this.value){
			$("input.rows").val(this.value);
			reSearch(true);
		}
	})
	if($("input.searchString").val()!=''){
		addConditionType($("input.searchString").val(),null,"key",null);
	}
	//排序更改事件
	$("#sort-product").change(function(){
		var value = this.value;
		var sort = $(this).find("option[value="+value+"]").attr("data-sort");
		if(!($("input.sidx").val()==value&&$("input.sort").val()==sort)){
			$("input.sidx").val(value.replace("-", "."));
			$("input.sort").val(sort);
			reSearch(true);
		}
	})
	reSearch(false);
})
//init为true时重置为第一页
function reSearch(init){
	if(init)$("input.page").val(1);
	var item_lis = $(".owl-item li");
	var category = "";
	for(var i=0;i<item_lis.length;i++){
		var type = $(item_lis[i]).attr("data-type");
		if(type=='category'){
			if(i==item_lis.length-1) category+=$(item_lis[i]).attr("data-id");
			else category+=$(item_lis[i]).attr("data-id")+",";
		}else if(type=='price'){
			$("input.priceMin").val($(item_lis[i]).attr("min"));
			$("input.priceMax").val($(item_lis[i]).attr("max"));
		}else if(type=='key'){
			$("input.searchString").val($(item_lis[i]).attr("data-text"));
		}
	}
	if($(".owl-item li[data-type=price]").length<1){
		$("input.priceMin").val(0);
		$("input.priceMax").val(999);
	}
	if($(".owl-item li[data-type=key]").length<1){
		$("input.searchString").val("");
	}
	$("input.rows").val($("#show-items").val());
	$("input.sidx").val($("#sort-product").val().replace("-", "."));
	$("input.sort").val($("option[value="+$("#sort-product").val()+"]").attr("data-sort"));
	if(category!=="")category = category.substr(0,category.length);
	$("input.category").val(category);
	$.post("json/list_cate_Cate_frontSearchList",$("form").serialize(),function(data){
		if(!(data instanceof Object))data = eval("("+data+")");
		$(".total").html(data.records);
		/*分页菜单start*/
		$(".pagination-num").html("");
		var start = data.page-3;
		if(start<1)start=1;
		if(start>1){
			$(".pagination-num").append('<li data-page="1"><a href="javascript:void(0)">1</a></li>');
			$(".pagination-num").append('<li class="disabled"><a href="javascript:void(0)">...</a></li>');
		}
		for(var i=start;i<=data.total;i++){
			if(i>start+5){
				$(".pagination-num").append('<li class="disabled"><a href="javascript:void(0)">...</a></li>');
				$(".pagination-num").append('<li data-page="'+data.total+'"><a href="javascript:void(0)">'+data.total+'</a></li>');
				break;
			}else $(".pagination-num").append('<li data-page="'+i+'" class="'+(i==data.page?"disabled":"")+'"><a href="javascript:void(0)">'+i+'</a></li>');
		}
		$(".pagination-add .prev").removeClass("disabled");
		$(".pagination-add .next").removeClass("disabled");
		if(data.page>1)$(".pagination-add .prev").attr("data-page",data.page-1);
		else $(".pagination-add .prev").attr("data-page",1).addClass("disabled");
		if(data.page<data.total)$(".pagination-add .next").attr("data-page",data.page+1);
		else $(".pagination-add .next").attr("data-page",data.total).addClass("disabled");
		$(".pagination li[class!=disabled]").unbind();
		$(".pagination li[class!=disabled]").bind("click",function(){
			$("input.page").val($(this).attr("data-page"));
			reSearch();
		})
		/*分页菜单end*/
		$(".show-catebox").html("");
		if(data.rows.length<1){
			$(".show-catebox").append("<h3 style='text-align:center'>没有匹配的美食，请更改条件重新筛选或点击<a class='initSearch' href='javascript:void(0)'>初始化条件</a></h3>")
			$(".initSearch").click(function(){
				$(".condition-result li").remove();reSearch();
				$(".condition li").removeClass("active");
			})
		}
		for(var i=0;i<data.rows.length;i++){
			var $cate = $(".cate-box>div").clone();
			$cate.find("a.cate-attr").attr("title",data.rows[i].name);
			$cate.find("img.cate-attr").attr("alt",data.rows[i].name);
			$cate.find(".product-name").attr("href",$cate.find(".product-name").attr("href")+"?id="+data.rows[i].id).html(data.rows[i].name);
			$cate.find(".watch-count").html(data.rows[i].log.viewCount);
			$cate.find(".sell-count").html(data.rows[i].log.buyCount);
			$cate.find(".cate-shopPrice").html(data.rows[i].shopPrice);
			$cate.find(".cate-price").html(data.rows[i].marketPrice);
			$cate.find(".description").html(data.rows[i].introduce);
			$cate.find(".image img").attr("src","img_requestCateImg?id="+data.rows[i].id+"&type=cate");
			$cate.find(".image-add-mod").attr("data-id",data.rows[i].id);
			$cate.find(".add-cart").attr("data-id",data.rows[i].id);
			$cate.find(".add-fav").attr("data-id",data.rows[i].id);
			$cate.find(".sign").html((new Date().getTime()-7*24*60*60*1000)<new Date(data.rows[i].onLineTime).getTime()?'新品':data.rows[i].log.buyCount>100?'热销':data.rows[i].log.viewCount>1000?'热门':'');
			if($cate.find(".sign").html()=='')$cate.find(".sign").parent().hide();
			$(".show-catebox").append($cate);
			bindEvent();
			addCartItem();
			addFavItem();
		}
	},"json")
	return false;
}
function bindEvent(){
	$(".image-add-mod").unbind("click");
	$(".image-add-mod").bind("click",function(){
		$("#img").attr("src","img_requestCateImg?type=max&id="+$(this).attr("data-id"));
		$('#showPic').modal('show');
	})
}
//添加我的购物车
function addCartItem(){
	$(".add-cart").unbind();
	$(".add-cart").bind("click",function(){
		$.post("json/cart_addCart","cateId="+$(this).attr("data-id")+"&count=1",function(data){
			console.log(data);
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				swal("Good!", "成功添加到购物车！", "success");
			}else{
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