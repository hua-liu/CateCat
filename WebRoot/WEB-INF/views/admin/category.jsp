<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="css/bootstrap.min.css">
 <link rel="stylesheet" href="css/admin/easyTree.css">
 <link href="css/animate.css" rel="stylesheet">
 <style type="text/css">
 	body{
 		background:#ecf0f5;
 	}
 	.easy-tree>ul>li{
 	float:left;
 	}
 </style>
</head>
<body>
<div class="wrapper wrapper-content  animated fadeInLeft">
	<div class="col-md-12">
    <div class="easy-tree">
    	<ul>
    		<s:iterator value="#request.categorys">
    			<li data-id="${id}">${name}
					<ul>
					<s:iterator value="childs">
						<li data-id="${id}">${name}</li>
					</s:iterator>
					</ul>
    			</li>
    		</s:iterator>
    	</ul>
    </div>
</div>
</div>
<script src="js/jquery.min.js?v=2.2.2"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<!-- Peity -->
<script src="js/plugins/peity/jquery.peity.min.js"></script>
<script src="js/admin/easyTree.js"></script>
<script>
    (function ($) {
        function init() {
            $('.easy-tree').EasyTree({
                addable: true,
                editable: true,
                deletable: true
            });
        }

        window.onload = init();
    })(jQuery)
</script>
</body>
</html>