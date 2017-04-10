<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>店铺布局</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="css/animate.css" rel="stylesheet">
<link href="css/style.css?v=4.1.0" rel="stylesheet">
<link href="css/plugins/order/layout.css" rel="stylesheet">
 <link href="css/plugins/nouslider/jquery.nouislider.css" rel="stylesheet">
 <link href="css/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<link href="css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
</head>

<body>
	<div class="menu">
		<button class="menu-item btn btn-default newLayout"><i class="glyphicon glyphicon-plus"></i>创建新布局</button>
		<!-- <select class="menu-item form-control" style="width:100px;height:34px">
			<option>方案A</option>
		</select> -->
		<button class="menu-item btn btn-default openLayout"><i class="glyphicon glyphicon-pencil"></i>加载已有布局</button>

		<button class="menu-item btn btn-default saveLayout"><i class="glyphicon glyphicon-pencil"></i>保存当前布局</button>
		<span class="clear"></span>
	</div>
	<div class="tools">
		<button class="tools-item btn btn-default create-circle"><i class="fa fa-circle"></i>圆形</button>
		<button class="tools-item btn btn-default create-square"><i class="fa fa-square"></i>矩形</button>
		<button class="tools-item btn btn-default create-across"><i class="fa fa-minus"></i>横线</button>
		<button class="tools-item btn btn-default create-vertical"><p style="width:3px;height:15px;background:#58666E;float:left;padding:0;margin:3px 2px 0 0"></p>竖线</button>
		<button class="tools-item btn btn-default create-font"><i class="fa fa-font"></i>文字</button>
	</div>
	<div id="box">
	</div>
<!--新建布局modal start -->
<div class="modal inmodal fade" id="newLayoutModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
        	<div class="modal-header">
                <h6 class="modal-title" style="margin:0;padding:0">初始化参数设置</h6>
            </div>
            <div class="modal-body">
            	<div class="input-group m-b"><span class="input-group-addon">宽度</span>
                     <input type="text" name="width" placeholder="新布局的宽度(单位像素)" class="form-control number">
                 </div>
            	<div class="input-group m-b"><span class="input-group-addon">高度</span>
                     <input type="text" name="height" placeholder="新布局的高度(单位像素)" class="form-control number">
                 </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success createLayout" data-dismiss="modal">创建</button>
            </div>
        </div>
    </div>
</div>
<!--新建布局modal end -->
<!--工具modal start -->
<div class="modal inmodal fade" id="utilLayoutModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
        	<div class="modal-header">
                <h6 class="modal-title" style="margin:0;padding:0">初始化参数设置</h6>
            </div>
            <div class="modal-body">
            	<div class="input-group m-b a1"><span class="input-group-addon title">大小</span>
                     <input type="text" name="size" placeholder="生成元素大小(单位像素)" class="form-control number">
                 </div>
            	<div class="input-group m-b a2"><span class="input-group-addon title">大小</span>
                     <input type="text" name="size" placeholder="生成元素大小(单位像素)" class="form-control number">
                 </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success utilLayoutCreate" data-dismiss="modal">创建</button>
            </div>
        </div>
    </div>
</div>
<!--工具modal end -->
<!--元素菜单 start-->
    <!--圆形-->
    <div class="ele-item circle-menu">
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">半径</span>
                <input type="text" class="form-control number" name="radius">
            </div>
            <div class="col-sm-6 radius-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">透明</span>
                <input type="text" class="form-control number" name="opacity">
            </div>
            <div class="col-sm-6 opacity-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">层次</span>
                <input type="text" class="form-control number" name="index">
            </div>
            <div class="index-slider col-sm-6" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b" style="float:left;position: relative"><span class="input-group-addon">颜色</span>
                <div class="input-group colorpicker-component">
                    <input type="text" class="form-control" name="color">
                    <span class="input-group-addon"><i></i></span>
               </div>
            </div>
             <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">允许预定</span>
                <div class="switch">
                    <div class="onoffswitch">
                        <input type="checkbox" checked class="onoffswitch-checkbox" id="example1">
                        <label class="onoffswitch-label" for="example1">
                            <span class="onoffswitch-inner"></span>
                            <span class="onoffswitch-switch"></span>
                        </label>
                    </div>
                </div>
            </div>
    </div>
    <!--矩形-->
    <div class="ele-item square-menu">
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">宽度</span>
                <input type="text" class="form-control number" name="width">
            </div>
            <div class="col-sm-6 width-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">高度</span>
                <input type="text" class="form-control number" name="height">
            </div>
            <div class="col-sm-6 height-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">透明</span>
                <input type="text" class="form-control number" name="opacity">
            </div>
            <div class="col-sm-6 opacity-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">层次</span>
                <input type="text" class="form-control number" name="index">
            </div>
            <div class="index-slider col-sm-6" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b" style="float:left;position: relative"><span class="input-group-addon">颜色</span>
                <div class="input-group colorpicker-component">
                    <input type="text" class="form-control" name="color">
                    <span class="input-group-addon"><i></i></span>
               </div>
            </div>
             <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">允许预定</span>
                <div class="switch">
                    <div class="onoffswitch">
                        <input type="checkbox" checked class="onoffswitch-checkbox" id="example1">
                        <label class="onoffswitch-label" for="example1">
                            <span class="onoffswitch-inner"></span>
                            <span class="onoffswitch-switch"></span>
                        </label>
                    </div>
                </div>
            </div>
    </div>
    <!--横线-->
    <div class="ele-item across-menu">
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">长度</span>
                <input type="text" class="form-control number" name="width-long">
            </div>
            <div class="col-sm-6 width-long-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">宽度</span>
                <input type="text" class="form-control number" name="height-short">
            </div>
            <div class="col-sm-6 height-short-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">透明</span>
                <input type="text" class="form-control number" name="opacity">
            </div>
            <div class="col-sm-6 opacity-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">层次</span>
                <input type="text" class="form-control number" name="index">
            </div>
            <div class="index-slider col-sm-6" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b" style="float:left;position: relative"><span class="input-group-addon">颜色</span>
                <div class="input-group colorpicker-component">
                    <input type="text" class="form-control" name="color">
                    <span class="input-group-addon"><i></i></span>
               </div>
            </div>
    </div>
    <!--竖线-->
    <div class="ele-item vertical-menu">
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">长度</span>
                <input type="text" class="form-control number" name="height-long">
            </div>
            <div class="col-sm-6 height-long-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">宽度</span>
                <input type="text" class="form-control number" name="width-short">
            </div>
            <div class="col-sm-6 width-short-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">透明</span>
                <input type="text" class="form-control number" name="opacity">
            </div>
            <div class="col-sm-6 opacity-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">层次</span>
                <input type="text" class="form-control number" name="index">
            </div>
            <div class="index-slider col-sm-6" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b" style="float:left;position: relative"><span class="input-group-addon">颜色</span>
                <div class="input-group colorpicker-component">
                    <input type="text" class="form-control" name="color">
                    <span class="input-group-addon"><i></i></span>
               </div>
            </div>
    </div>
    <!--文本-->
    <div class="ele-item font-menu">
            <div class="input-group m-b col-sm-12" style="float:left"><span class="input-group-addon">内容</span>
                <input type="text" class="form-control" name="text">
            </div>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">大小</span>
                <input type="text" class="form-control number" name="size">
            </div>
            <div class="col-sm-6 size-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">透明</span>
                <input type="text" class="form-control number" name="opacity">
            </div>
            <div class="col-sm-6 opacity-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b col-sm-6" style="float:left"><span class="input-group-addon">层次</span>
                <input type="text" class="form-control number" name="index">
            </div>
            <div class="index-slider col-sm-6" style="float:left;margin-top:8px;"></div><span class="clear"></span>
            <div class="input-group m-b" style="float:left;position: relative"><span class="input-group-addon">颜色</span>
                <div class="input-group colorpicker-component">
                    <input type="text" class="form-control" name="color">
                    <span class="input-group-addon"><i></i></span>
               </div>
            </div>
             <div class="col-sm-6 opacity-slider" style="float:left;margin-top:8px;"></div><span class="clear"></span>
    </div>
<!--元素菜单 end-->
<!--加载已有布局 start-->
    <div class="modal fade bs-example-modal-sm" id="openLayoutModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <div><p style="float:left;width:70%;height:30px;line-height:30px;text-align:center;font-size:15px">布局名称</p><p style="float:left;width:30%;height:30px;line-height:30px;text-align:center;font-size:15px">启用状态</p><span class="clear"></span></div>
              <ul class="layout-list"></ul>
            </div>
        </div>
    </div>
<!--加载已有布局 end-->
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.2.2"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<script src="js/admin/map.js"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="js/plugins/layer/layer.min.js"></script>
<script src="js/plugins/layer/extend/layer.ext.js"></script>
<script src="js/plugins/order/move.js"></script>
<script src="js/plugins/order/initLayout.js"></script>
<!-- NouSlider -->
<script src="js/plugins/nouslider/nouislider.js"></script>
<!-- Color picker -->
<script src="js/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<!-- iCheck -->
<script src="js/plugins/iCheck/icheck.min.js"></script>
</body>
</html>
