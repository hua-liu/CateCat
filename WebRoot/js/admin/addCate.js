/**
 * Created by 甜橙六画 on 2016/12/7.
 */
$(function () {
	getKey();
	$("input[name=name]").bind("change",function(){
		$(this).attr("error",false);
	})
	$("input[name=name]").bind("blur",function(){
		if(this.value.trim()=='')return;
		if($(this).attr("error")=='yes'){
			tooltipUtil(this,"美食名称已经存在");$(name).addClass("error");
			$(this).focus();
			return;
		}
		if($(this).attr("data-source")==this.value)return;
		var _this = this;
		var data = "name="+this.value;
		var id = $("#cate_id").val();	//兼容更新页面
		if(id!=undefined&&id!=''&&id!=null){
			data += "&id="+id;
		}
		$(this).attr("data-source",this.value);
		$.post("json/cate_checkname",data,function(data){
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				$(_this).attr("error","yes");
				tooltipUtil(_this,"美食名称已经存在");$(name).addClass("error");
				$(_this).focus();
			}else{
				$(_this).attr("error",false);
			}
		},"json")
	})
	$(".price").keyup(function(e){
		if(/^[0-9.]{0,6}$/.test(this.value)){
			$(this).attr("source-data",this.value);
		}else{
			this.value=$(this).attr("source-data");
		}
	})
	$(".price").change(function(){
		if(/^[0-9]{1,3}$/.test(this.value)){
			this.value = this.value+".00";
		}else if(/^[0-9]{1,3}[.]{1}$/.test(this.value)){
			this.value = this.value+"00";
		}else if(/^[0-9]{1,3}[.]{1}[0-9]{1}$/.test(this.value)){
			this.value = this.value+"0";
		}else if(!(/^[0-9]{1,3}[.]{1}[0-9]{2}$/.test(this.value))){
			this.value=null;
			$(this).attr("placeholder","无效价格(0<=价格<1000)");
		}
	})
    //分类选择
    var beforType = "";
    $(".categoryUl").click(function(){
    	var type = $(this).attr("data-type");
    	if(beforType==type){
    		$(".categoryList").toggle();return;
    	}
    	beforType=type;
    	for(var i=0;i<categoryData.length;i++){
			if(categoryData[i].text==type){
				// 节点树设置
				$('#treeview').treeview({
					data : eval("("+("["+JSON.stringify(categoryData[i])+"]")+")"),
					levels : 5,
					multiSelect : true
				});
				break;
			}
		}
    	var lis = $(this).find("li");
    	try{
	    	for(var i=0;i<lis.length;i++){
	    		$('#treeview').treeview('selectNode',[parseInt($(lis[i]).attr("node-id")), {silent : true}]);
	    	}
    	}catch(e){}
    	// 点击事件
		$('#treeview').on('nodeSelected', function(event, data) {
			var parentNode = $(this).treeview('getNode', data.parentId);//获取到父节点
			if(parentNode==undefined||parentNode.id==undefined){//没有父节点，说明本身为第一个节点，设为不选中，返回
				$('#treeview').treeview('unselectNode',[data.nodeId, {silent : true}]);return;
			}
			if($(".categoryUl[data-type="+parentNode.text+"] li").length>=5){	//当选中数大于5时弹出消息，只能添加5个
				$('#treeview').treeview('unselectNode',[data.nodeId, {silent : true}]);
				parent.layer.msg("最多添加5个"+parentNode.text);
			}else{
				fillCategory(parentNode.text,data.id, data.text, data.nodeId);	//向分类UL里添加选中的数据
			}
		});
		//当取消节点选中时删除Ul里的数据
		$('#treeview').on('nodeUnselected', function(event, data) {
			var parentNode = $(this).treeview('getNode', data.parentId);
			delCategory(parentNode.text,data.id)
		});
    	//更新分类表出来的位置
		var modal = $(".sourceInfor")[0];
		$(".categoryList").css("left", modal.offsetWidth);
		$(".close-link").unbind();
		$(".close-link").bind("click", function() {
			$(".categoryList").hide();
		})
		$(".categoryList").show();
    	return false;
    })
    //关闭事件
    $(".closeMySelf").click(function(){
    	$(this).parent().hide();
    })
    //表单提交 
   $("#sourceInforForm").submit(function(){
	   if(submiting())return;
	   var isPass = true;
	   //重复提交检测
	   var id = $("#sourceInforForm").attr("data-id");
	   if(id!=null){
		   parent.layer.confirm('您已经提交过当前美食(请不要重复提交)', {
			   btn: ['重置','取消'], //按钮
			   shade: false //不显示遮罩
		   }, function(){
			   submitDone("reset");
		   }, function(){
		   });
		   return false;
	   }
	   //名称校验
	   var name = $(".sourceInfor input[name=name]")[0];
	   if(name.value==''){
		   tooltipUtil(name,"名称不能为空");isPass = false;$(name).addClass("error");
	   }else if(name.length>60){
		   tooltipUtil(name,"长度不能超过60");isPass = false;$(name).addClass("error");
	   }else{
		   $(name).removeClass("error");
	   }
	   //价格校验
	   if(!(/^[0-9]{1,3}([.]{1}[0-9]{1,2}|[.]{0})$/.test($(".price[name=shopPrice]").val()))){
		   tooltipUtil($(".price[name=shopPrice]"),"价格不能这样填(***.**)");isPass = false;$(".price[name=shopPrice]").addClass("error");
	   }else  $(".price[name=shopPrice]").removeClass("error");
	   if(!(/^[0-9]{1,3}([.]{1}[0-9]{1,2}|[.]{0})$/.test($(".price[name=marketPrice]").val()))){
		   tooltipUtil($(".price[name=marketPrice]"),"价格不能这样填(***.**)");isPass = false;$(".price[name=marketPrice]").addClass("error");
	   }else $(".price[name=marketPrice]").removeClass("error");
		   
	   //分类选择情况
	   var cates = $(".categoryUl");
	   for(var i=0;i<cates.length;i++){
		   var temp = $(cates[i]).find("li");
		   if(temp.length<1){
			   tooltipUtil(cates[i],$(cates[i]).attr("data-type")+"至少选择1项");
			   isPass = false;  $(cates[i]).addClass("error");
		   }else if(temp.length>5){
			   tooltipUtil(cates[i],$(cates[i]).attr("data-type")+"最多选择5项");
			   isPass = false;  $(cates[i]).addClass("error");
		   }else{
			   $(cates[i]).removeClass("error");
		   }
	   }
	   //简介校验
	   var about = $("textarea[name=about]")[0];
	   if(about.value==''){
		   tooltipUtil(about,"简介不能为空"); isPass = false; $(about).addClass("error");
	   }else if(about.length>40){
		   tooltipUtil(about,"简介长度不能超过40"); isPass = false; $(about).addClass("error");
	   }else{
		   $(about).removeClass("error");
	   }
	   //描述校验
	   var introduce = $("textarea[name=introduce]")[0];
	   if(introduce.value==''){
		   tooltipUtil(introduce,"描述不能为空"); isPass = false; $(introduce).addClass("error");
	   }else if(introduce.length>250){
		   tooltipUtil(introduce,"描述长度不能超过250"); isPass = false; $(introduce).addClass("error");
	   }else{
		   $(introduce).removeClass("error");
	   }

	   if($(".filelist li").length<1){
		   if($(".imgUl li").length<1){
			   tooltipUtil($("#uploader"),"图片至少选择一张"); isPass = false; 
		   }//兼容更新页面
	   }
	   if(isPass){
		  $(".categoryList").hide();
		  uploadCate();
	   }else{
		   tooltipUtil( $("button[type=submit]"),"检验失败,提交驳回");
		   submitDone("break");
		   return false;
	   }
	   return false;
   })
  /* $("#sourceInforForm").change(function(){
	   alert("change")
   })*/
   	window.onresize=function(){
    	var modal = $(".sourceInfor")[0];
		$(".categoryList").css("left", modal.offsetWidth);
    }
	 //初始化分类
	 requestCategory();
});
//防止表单重复提交
var isSubmiting=false;
//移除分类
function delCategory(type,id) {
	$(".categoryUl[data-type="+type+"] li[data-id=" + id + "]").remove();
	categoryValueSet();
}
// 添加分类
function fillCategory(type,id, text, nid) {
	var li = $(".categoryUl[data-type="+type+"] li[data-id=" + id + "]");
	if (li.length > 0)return;
	$(".categoryUl[data-type="+type+"]").append($('<li data-id="'+ id+ '" node-id="'
							+ nid+ '"><span>'+ text+ '</span><i class="glyphicon glyphicon-remove"></i></li>'));
	bindCategoryRmove();
	categoryValueSet();
}
//分类请求
var hasCategory = false;
var categoryData;
function requestCategory() {
	$.post("json/category_all", null, function(data) {
		if(data instanceof Object){
			data = JSON.stringify(data);
		}
		// 为了配合节点树，把名称替换，节点替换，原文件添加data-id属性
		data = data.replace(/name/g, "text").replace(/childs/g, "nodes");
		data = eval("(" + data + ")");
		if (data.length > 0){
			hasCategory = true;
			categoryData = data;
		}
		$('#treeview').treeview({
			data : data,
			levels : 5,
			multiSelect : true
		});
	})
}
//绑定分类移除事件
function bindCategoryRmove(id) {
	$(".categoryUl li i").unbind();
	$(".categoryUl li i").bind("click",function() {
		// 这里要把获得的字符串转为整数
		try{
			$('#treeview').treeview('unselectNode',
				[ parseInt($(this).parent().attr("node-id")), {
					silent : true
				} ]);
		}catch(e){}
		$(this).parent().remove();
		categoryValueSet();
		return false;
	})
}
//取消已选择分类
function cancelUlSelectLi(id){
	$(".categoryUl li[data-id="+id+"]").remove();
	categoryValueSet();
}
//设置分类form value
function categoryValueSet(){
	var lis = $(".categoryUl li");
	var value='';
	for(var i=0;i<lis.length;i++){
		if(i==lis.length-1)
			value += $(lis[i]).attr("data-id");
		else
			value += $(lis[i]).attr("data-id")+",";
	}
	$("input[name=category]").val(value);	//上传美食页
	$("#cate_category").val(value);	//兼容更新美食页
}
//提示工具
function tooltipUtil(el,title){
	if(title=='')return;
	$(el).attr("data-content",title).attr("data-toggle","popover").attr("data-placement","top");
	$(el).popover("show");
	$(el).addClass("error")
	 setTimeout(function(){
		$(el).popover("destroy");
	},3000) 
}
//开始当前资源ID的上传任务 
function uploadCate(){
	$.post("json/cate_addOrUpdate",$('#sourceInforForm').serialize(),function(data) {
		if (data != null) {
			if(!(data instanceof Object)) data = eval("(" + data + ")");
			if(data.result){
				$("#sourceInforForm").attr("data-id",data.id);
				if($(".filelist li").length>0)
					$(".uploadBtn").trigger("click");
				else submitDone("ok");
			}else if(data.type=='key'){
				parent.layer.confirm(data.cause, {
				    btn: ['重新获取Key','放弃添加当前美食'], //按钮
				    shade: false //不显示遮罩
				}, function(){
				    submitDone("break");
					getKey();
					parent.layer.msg('已重新获取Key,请重新提交', {icon: 1});
				}, function(){
					submitDone("error");
				});


				   return false;
			}else{
				 submitDone("break");
				 parent.layer.msg('提交失败:'+data.cause, {icon: 1});
			}
		}
	})
}
function submiting(){
	if($(".unedit").length>0)return true;
	else{
		$(".sourceInfor").append($('<div class="unedit"><div class="spiner-example"><div class="sk-spinner sk-spinner-three-bounce"><div class="sk-bounce1"></div><div class="sk-bounce2"></div><div class="sk-bounce3"></div></div></div></div>'))
		return false;
	}
}
function clearForm(){
	$("#sourceInforForm button[type=reset").click();
	$(".categoryUl li").remove();
	$("#category").val("");
}
function submitDone(type){
	if(type=='ok'){
		parent.layer.msg("提交完成");
		/*$(".unedit").remove();
		clearForm();
		getKey();*/
		window.location.reload();
	}else if(type=='img'){
		$(".statusBar").css("z-index",120);
	}else if(type=='error'){
		parent.layer.msg("已重置填写");
		/*$(".unedit").remove();
		clearForm();
		getKey();*/
		window.location.reload();
	}else if(type=='reset'){
		 parent.layer.msg('表单已重置', {icon: 1});
		 window.location.reload();
	}else if(type=='check'){
		 parent.layer.msg('信息填写有误，请检查后再提交', {icon: 6});
	}else{
		$(".unedit").remove();
	}
}
//自动填充分类
function fillAutoCategory(key,value){
	$(".categoryUl").html("");
	var arr = value.split(",");
	for(var i=0;i<arr.length;i++){
		$('#treeview').treeview('expandAll', {silent: true });
		var lis = $('#treeview').treeview('getExpanded');
		$('#treeview').treeview('collapseAll', { silent: true });
		for(var j=0;j<lis.length;j++){
			if(arr[i]==lis[j].id){
				$('#treeview').treeview('selectNode', [lis[j].nodeId, { silent: true } ]);
				fillCategory(arr[i], lis[j].text, lis[j].nodeId);
				break;
			}
		}
	} 
}
function getKey(){
	$.post("json/cate_getKey",null,function(data){
		if(data!=null){
			if(!(data instanceof Object))data = eval("("+data+")");
			if(data.result){
				$("#key").val(data.id);
				return;
			}
		}
		parent.layer.alert('获取Key失败,请检查网络连接', {
		    skin: 'layui-layer-molv', //样式类名
		    shift: 4 //动画类型
		});
		
	},"json")
}