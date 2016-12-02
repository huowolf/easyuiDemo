<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">

<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript">


</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:true" href="" style="height:110px;">
		<div align="left">
			<h1>权限管理系统</h1>
		</div>
	</div>

	<div data-options="region:'west',title:'导航菜单',split:true" style="width:150px">
		<div id="aa" class="easyui-accordion" fit="true">   
		    <div title="用户管理" data-options="iconCls:'icon-save'" style="overflow:auto;padding:10px;">   
		       <ul id="tree"></ul>       
		    </div>   
		    <div title="岗位管理" data-options="selected:true" style="padding:10px;">   
		        content2    
		    </div>   
		    <div title="权限管理">   
		        content3    
		    </div>   
		</div>  
	
	</div>
	
	<div data-options="region:'center',title:'首页' "
		style="padding:5px; background:#eee; overflow:hidden;" >
		 
		<div class="easyui-tabs" fit="true" border="false" id="tt"  style="width:150px">
				<div align="center" data-options="title:'首页'">
					<h1>欢迎使用该系统</h1>
				</div>				
		</div>

	</div>
	
	<div region="east" style="width:100px">右侧内容XXXXXXX</div>
</body>
</html>