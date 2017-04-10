$(function(){
	initSavedLayout();
	$(".context-item[data-use=1]").click(function(){
		alert("还未开通网上直接预定,目前可以使用电话或到店预定")
	})
})
function saveLayout(name,id){
	$("#room .ele-item").remove();
	$.post("json/layout_saveLayout","name="+name+(id!=null?"&id="+id:"")+"&context="+$("#box").html().trim(),function(data){
		if(!(data instanceof Object))data = eval("("+data+")");
		if(data.result){
			parent.layer.msg("保存成功");
			initSavedLayout();
		}else{
			parent.layer.msg("保存失败:"+data.cause);
		}
	})
}
//初始化已保存布局
function initSavedLayout(){
	$.post("json/layout_getCurrentUse",function(data){
		console.log(data)
		if(data!=null){
			$("#box").append(data);
		}
	})
}
/*初始化窗口*/
function initRoom(w,h){
	$("#box").append('<div id="room"></div>');
	$("#room").css({"width":w,"height":h});
	bindEvent();
}
/*事件绑定*/
function bindEvent(){
	$(".move").unbind();
	$(".circle-item").unbind();
	$(".number").unbind();
	$(".move").mouseover(function(){
		move(this);
	})
	/*圆形事件*/
	$(".circle-item").bind("dblclick",function(){
		if($("#room .circle-menu").length>0){
			$("#room .circle-menu").remove();
			return;
		}
		var circleMenu = $(".circle-menu").clone(true)[0];
		$("#room").append(circleMenu);
		circleMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		circleMenu.style.top = this.offsetTop+10+"px";
		circleMenu.style.height = 155+"px";
		sliderEvent(this,$(circleMenu).find(".radius-slider")[0],$(circleMenu).find("input[name=radius]"),"data-radius");
		sliderEvent(this,$(circleMenu).find(".opacity-slider")[0],$(circleMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(circleMenu).find(".index-slider")[0],$(circleMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(circleMenu).find(".colorpicker-component")[0],"bg");
		$(circleMenu).show();
	});
	/*矩形事件*/
	$(".square-item").bind("dblclick",function(){
		if($("#room .square-menu").length>0){
			$("#room .square-menu").remove();
			return;
		}
		var squareMenu = $(".square-menu").clone(true)[0];
		$("#room").append(squareMenu);
		squareMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		squareMenu.style.top = this.offsetTop+10+"px";
		squareMenu.style.height = 190+"px";
		sliderEvent(this,$(squareMenu).find(".width-slider")[0],$(squareMenu).find("input[name=width]"),"data-width");
		sliderEvent(this,$(squareMenu).find(".height-slider")[0],$(squareMenu).find("input[name=height]"),"data-height");
		sliderEvent(this,$(squareMenu).find(".opacity-slider")[0],$(squareMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(squareMenu).find(".index-slider")[0],$(squareMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(squareMenu).find(".colorpicker-component")[0],"bg");
		$(squareMenu).show();
	});
	/*数字限制*/
	$(".number").keyup(function(e){
		if(/^[0-9.]{0,8}$/.test(this.value)){
			$(this).attr("data-source",this.value);
		}else{
			if($(this).attr("data-source")==undefined){
				$(this).attr("data-source","");
			}
			this.value=$(this).attr("data-source");
		}
	})
}
function bindLiEvent(){
	$(".layout-list li>p").unbind();
	$(".layout-list li>p").bind("click",function(){
		var _this = $(this).parent();
		$("#openLayoutModal").modal("hide");
		$.post("json/layout_getLayoutById","id="+$(_this).attr("data-id"),function(data){
			if(data!=null){
				$("#box").html(data);
				currentLayoutId = $(_this).attr("data-id");
				currentLayoutName = $(_this).find("p").html();
				$(".tools").show();
				bindEvent();
			}
		})
	});
	$('.layout-list input').on('change', function(event){
		$.post("json/layout_updateState","id="+this.value,function(data){
			if(!(data instanceof Object))data = eval("("+data+")");
			if(!data.result){
				parent.layer.msg("保存失败："+data.cause);
			}
		}) 
		alert(this.value);
	});
}
function moveEle(){
	$("#room .ele-item").remove();

}
function sliderEvent(_this,slider,input,type){
	if(type=='data-radius'){
		noUiSlider.create(slider, {
			start: 40,
			connect: true,
			range: {
				'min': 20,
				'max': 800
			}
		});
	}else if(type=='data-opacity'){
		noUiSlider.create( slider, {
			start: 1,
			connect: true,
			range: {
				'min': 0.1,
				'max': 1
			}
		});
	}else if(type=="data-index"){
		noUiSlider.create( slider, {
			start: 1,
			connect: true,
			range: {
				'min': 1,
				'max': 1000
			}
		});
	}else if(type=="data-width"||type=='data-height'||type=='data-width-long'||type=='data-height-long'){
		noUiSlider.create( slider, {
			start: 1,
			connect: true,
			range: {
				'min': 10,
				'max': 1000
			}
		});
	}else if(type=="data-width-short"||type=='data-height-short'){
		noUiSlider.create( slider, {
			start: 1,
			connect: true,
			range: {
				'min': 1,
				'max': 10
			}
		});
	}else if(type=="data-size"){
		noUiSlider.create( slider, {
			start: 1,
			connect: true,
			range: {
				'min': 1,
				'max': 50
			}
		});
	}
	var val = $(_this).attr(type);
	slider.noUiSlider.on('update', function ( values, handle ) {
		if ( handle == 0 ) {
			var v = values[handle];
			$(input).attr("data-source",v).val(v);
			$(_this).attr(type,v);
			if(type=='data-radius'){
				$(_this).css({"width":v*2,"height":v*2,"border-radius":v+"px"});
			}else if(type=='data-opacity'){
				$(_this).css("opacity",v);
			}else if(type=='data-index'){
				$(_this).css("z-index",Math.round(v));
			}else if(type=="data-width"||type=='data-width-long'||type=='data-width-short'){
				$(_this).css("width",v);
			}else if(type=="data-height"||type=='data-height-long'||type=='data-height-short'){
				$(_this).css("height",v);
			}else if(type=='data-size'){
				$(_this).css("font-size",v+"px");
			}
		}
	});
	$(input).unbind();
	$(input).bind('change', function ( ) {
		slider.noUiSlider.set([this.value, null]);
	});
	slider.noUiSlider.set([val, null]);
}
function colorEvent(_this,colorEl,type){
	$(colorEl).unbind();
	$(colorEl).colorpicker();
	$(colorEl).colorpicker().on('changeColor', function(ev){
		if(type=='f'){
			$(_this).css("color",ev.color.toHex());
		}else{
			$(_this).css("background",ev.color.toHex());
		}
		$(_this).attr(type,ev.color.toHex())
	});
	$(colorEl).colorpicker("setValue",$(_this).attr(type))
	
	
}function textEvent(_this,textEl){
	$(textEl).val($(_this).html());
	$(textEl).unbind();
	$(textEl).bind("change",function(){
		$(_this).html($(textEl).val());
	})
}