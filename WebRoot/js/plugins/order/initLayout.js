$(function(){
	/*创建布局确认*/
	$(".createLayout").click(function(){
		var width= $("input[name=width]").val();
		var height= $("input[name=height]").val();
		initRoom(width,height);
		$(".tools").show();
	})
	$('.newLayout').click(function (e) {
		if($("#room").length>0){
			parent.layer.confirm('当前已有正在编辑的布局，确认要重新生成新布局吗？', {
				btn: ['重新生成','继续编辑当前布局'], //按钮
				shade: false //不显示遮罩
			}, function(index){
				parent.layer.close(index);
				$("#room").remove();
				$("#newLayoutModal").modal("show");
			}, function(index){
				parent.layer.close(index);
				return false;
			});
		}else
		$("#newLayoutModal").modal("show");
	})
	/*创建圆形事件*/
	$(".create-circle").click(function(){
		$("#utilLayoutModal .a1 .title").html("半径");
		$("#utilLayoutModal .a1 input[name=size]").attr("placeholder","生成元素半径大小(单位像素)").addClass("number").val("");;
		$("#utilLayoutModal .a2").hide();
		$("#utilLayoutModal").modal("show");
		$("#utilLayoutModal").attr("type","circle");
		bindEvent();
	});
	/*创建矩形事件*/
	$(".create-square").click(function(){
		$("#utilLayoutModal .a1 .title").html("宽度");
		$("#utilLayoutModal .a1 input[name=size]").attr("placeholder","生成元素宽度(单位像素)").addClass("number").val("");;
		$("#utilLayoutModal .a2 .title").html("高度");
		$("#utilLayoutModal .a2 input[name=size]").attr("placeholder","生成元素高度(单位像素)").addClass("number").val("");;
		$("#utilLayoutModal .a2").show();
		$("#utilLayoutModal").modal("show");
		$("#utilLayoutModal").attr("type","square");
		bindEvent();
	});
	/*创建横线事件*/
	$(".create-across").click(function(){
		$("#utilLayoutModal .a1 .title").html("长度");
		$("#utilLayoutModal .a1 input[name=size]").attr("placeholder","生成元素长度(单位像素)").addClass("number").val("");;
		$("#utilLayoutModal .a2").hide();
		$("#utilLayoutModal").modal("show");
		$("#utilLayoutModal").attr("type","across");
		bindEvent();
	});
	/*创建竖线事件*/
	$(".create-vertical").click(function(){
		$("#utilLayoutModal .a1 .title").html("高度");
		$("#utilLayoutModal .a1 input[name=size]").attr("placeholder","生成元素高度(单位像素)").addClass("number").val("");;
		$("#utilLayoutModal .a2").hide();
		$("#utilLayoutModal").modal("show");
		$("#utilLayoutModal").attr("type","vertical");
		bindEvent();
	});

	/*创建竖线事件*/
	$(".create-font").click(function(){
		$("#utilLayoutModal .a1 .title").html("文本");
		$("#utilLayoutModal .a1 input[name=size]").attr("placeholder","文本内容").removeClass("number").unbind().val("");
		$("#utilLayoutModal .a2").hide();
		$("#utilLayoutModal").modal("show");
		$("#utilLayoutModal").attr("type","font");
	});
	/*创建元素确认*/
	$(".utilLayoutCreate").click(function(){
		var size =$("#utilLayoutModal .a1 input[name=size]").val();
		var size2 =$("#utilLayoutModal .a2 input[name=size]").val();
		var newDiv = null;
		if($("#utilLayoutModal").attr("type")=='circle'){
			newDiv = $('<div class="context-item circle-item move" style="background:#555;border-radius:'+size+'px" data-radius="'+size+'" data-bg="#555" data-opacity="1" data-index="10"></div>');
			$(newDiv).css({"width":size*2,"height":size*2,"z-index":10});
		}else if($("#utilLayoutModal").attr("type")=='square'){
			newDiv = $('<div class="context-item square-item move" style="background:#555;" data-width="'+size+'" data-height="'+size2+'" data-bg="#555" data-opacity="1" data-index="10"></div>');
			$(newDiv).css({"width":size,"height":size2,"z-index":10});
		}else if($("#utilLayoutModal").attr("type")=='across') {
			newDiv = $('<div class="context-item across-item move" style="background:#555;" data-width-long="' + size + '" data-height-short="5" data-bg="#555" data-opacity="1" data-index="10"></div>');
			$(newDiv).css({"width": size, "height": 5, "z-index": 10});
		}else if($("#utilLayoutModal").attr("type")=='vertical'){
			newDiv = $('<div class="context-item vertical-item move" style="background:#555;" data-width-short="5" data-height-long="'+size+'" data-bg="#555" data-opacity="1" data-index="10"></div>');
			$(newDiv).css({"width":5,"height":size,"z-index":10});
		}else if($("#utilLayoutModal").attr("type")=='font'){
			newDiv = $('<div class="context-item font-item move" style="color:#000;z-index:10;font-size:10px;" data-text="'+size+'" data-size="10" data-opacity="1" data-index="10" data-color="#000">'+size+'</div>');
		}
		$("#room").append(newDiv);
		bindEvent()
	});
	$(".openLayout").click(function(){
		$("#openLayoutModal").modal("show");
	});
	$(".saveLayout").click(function(){
		if($("#room").length<1){
			parent.layer.msg("当前没有正在编辑的布局");
			return false;
		}
		layer.prompt({
			title: '输入当前布局名',
			value:currentLayoutName==null?'':currentLayoutName,
			formType: 0 //prompt风格，支持0-2
		}, function(name,index){
			layer.close(index);
			saveLayout(name,currentLayoutId);
		});
	});
	initSavedLayout();
	bindEvent();
})
var currentLayoutId=null;
var currentLayoutName=null;
function saveLayout(name,id){
	$("#room .ele-item").remove();
	$.post("json/layout_saveLayout","name="+name+(id!=null?"&id="+id:"")+"&context="+$("#box").html().trim(),function(data){
		if(!(data instanceof Object))data = eval("("+data+")");
		if(data.result){
			parent.layer.msg("保存成功");
			currentLayoutId = data.id;
			currentLayoutName = name;
			initSavedLayout();
		}else{
			parent.layer.msg("保存失败:"+data.cause);
		}
	})
}
//初始化已保存布局
function initSavedLayout(){
	$(".layout-list").html("");
	$.post("json/layout_getLayouts",function(data){
		if(!(data instanceof Object))data = eval("("+data+")");
		for(var i=0;i<data.length;i++){
			var newNode = '<li data-id='+data[i].id+'><p>'+data[i].name+'</p>'
			+'<div class="radio radio-success"><input type="radio" name="radio2" id="radio'+data[i].id+'" value="'+data[i].id+'" '+(data[i].used==1?"checked":"")+'/>'
			 		+'<label></label></div></li>';
			$(".layout-list").append($(newNode));
		}
		bindLiEvent();
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
			$("#room .ele-item").remove();
			return;
		}
		var circleMenu = $(".circle-menu").clone(true)[0];
		$("#room").append(circleMenu);
		circleMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		circleMenu.style.top = this.offsetTop+10+"px";
		circleMenu.style.height = 195+"px";
		sliderEvent(this,$(circleMenu).find(".radius-slider")[0],$(circleMenu).find("input[name=radius]"),"data-radius");
		sliderEvent(this,$(circleMenu).find(".opacity-slider")[0],$(circleMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(circleMenu).find(".index-slider")[0],$(circleMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(circleMenu).find(".colorpicker-component")[0],"bg");
		useEvent(this,$(".onoffswitch-checkbox")[0]);
		$(circleMenu).show();
	});
	/*矩形事件*/
	$(".square-item").bind("dblclick",function(){
		if($("#room .square-menu").length>0){
			$("#room .ele-item").remove();
			return;
		}
		var squareMenu = $(".square-menu").clone(true)[0];
		$("#room").append(squareMenu);
		squareMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		squareMenu.style.top = this.offsetTop+10+"px";
		squareMenu.style.height = 230+"px";
		sliderEvent(this,$(squareMenu).find(".width-slider")[0],$(squareMenu).find("input[name=width]"),"data-width");
		sliderEvent(this,$(squareMenu).find(".height-slider")[0],$(squareMenu).find("input[name=height]"),"data-height");
		sliderEvent(this,$(squareMenu).find(".opacity-slider")[0],$(squareMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(squareMenu).find(".index-slider")[0],$(squareMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(squareMenu).find(".colorpicker-component")[0],"bg");
		useEvent(this,$(".onoffswitch-checkbox")[0]);
		$(squareMenu).show();
	});
	/*横线事件*/
	$(".across-item").bind("dblclick",function(){
		if($("#room .across-menu").length>0){
			$("#room .ele-item").remove();
			return;
		}
		var acrossMenu = $(".across-menu").clone(true)[0];
		$("#room").append(acrossMenu);
		acrossMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		acrossMenu.style.top = this.offsetTop+10+"px";
		acrossMenu.style.height = 190+"px";
		sliderEvent(this,$(acrossMenu).find(".width-long-slider")[0],$(acrossMenu).find("input[name=width-long]"),"data-width-long");
		sliderEvent(this,$(acrossMenu).find(".height-short-slider")[0],$(acrossMenu).find("input[name=height-short]"),"data-height-short");
		sliderEvent(this,$(acrossMenu).find(".opacity-slider")[0],$(acrossMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(acrossMenu).find(".index-slider")[0],$(acrossMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(acrossMenu).find(".colorpicker-component")[0],"bg");
		$(acrossMenu).show();
	});
	/*竖线事件*/
	$(".vertical-item").bind("dblclick",function(){
		if($("#room .vertical-menu").length>0){
			$("#room .ele-item").remove();
			return;
		}
		var verticalMenu = $(".vertical-menu").clone(true)[0];
		$("#room").append(verticalMenu);
		verticalMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		verticalMenu.style.top = this.offsetTop+10+"px";
		verticalMenu.style.height = 190+"px";
		sliderEvent(this,$(verticalMenu).find(".height-long-slider")[0],$(verticalMenu).find("input[name=height-long]"),"data-height-long");
		sliderEvent(this,$(verticalMenu).find(".width-short-slider")[0],$(verticalMenu).find("input[name=width-short]"),"data-width-short");
		sliderEvent(this,$(verticalMenu).find(".opacity-slider")[0],$(verticalMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(verticalMenu).find(".index-slider")[0],$(verticalMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(verticalMenu).find(".colorpicker-component")[0],"bg");
		$(verticalMenu).show();
	});
	/*文本事件*/
	$(".font-item").bind("dblclick",function(){
		if($("#room .font-menu").length>0){
			$("#room .ele-item").remove();
			return;
		}
		var fontMenu = $(".font-menu").clone(true)[0];
		$("#room").append(fontMenu);
		fontMenu.style.left = this.offsetLeft+this.offsetWidth+10+"px";
		fontMenu.style.top = this.offsetTop+10+"px";
		fontMenu.style.height = 190+"px";
		sliderEvent(this,$(fontMenu).find(".size-slider")[0],$(fontMenu).find("input[name=size]"),"data-size");
		sliderEvent(this,$(fontMenu).find(".opacity-slider")[0],$(fontMenu).find("input[name=opacity]"),"data-opacity");
		sliderEvent(this,$(fontMenu).find(".index-slider")[0],$(fontMenu).find("input[name=index]"),"data-index");
		colorEvent(this,$(fontMenu).find(".colorpicker-component")[0],"f");
		textEvent(this,$(fontMenu).find("input[name=text]")[0]);
		$(fontMenu).show();
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
function useEvent(_this,switchButton){
	var use = $(_this).attr("data-use");
	if(use==null||use==undefined){
		$(_this).attr("data-use",0);
		use=0;
	}
	$(switchButton).unbind();
	$(switchButton).bind("change",function(){
		if($(this).is(":checked")){
			$(_this).attr("data-use",1);
		}else{
			$(_this).attr("data-use",0);
		}
	})
	if(use==1)$(switchButton).attr("checked",true);
	else $(switchButton).attr("checked",false);
}