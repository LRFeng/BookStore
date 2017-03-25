<#assign labels = ["销售概括","店铺管理","教材管理","我的消息","用户管理","角色管理","系统管理"]
		urls=["#1","#2","#3","#4","#5","#6","#7"]>

<#macro head title>
	<head>
		<meta charset="UTF-8">
		<title>${title}</title>
		<script type="text/javascript" src="../static/js/jquery-3.1.1.min.js"></script>
		<script type="text/javascript" src="../static/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="../dwr/engine.js"></script>
		<script type="text/javascript" src="../static/plugins/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../static/js/book.js"></script>
		<link rel="stylesheet" type="text/css" href="../static/plugins/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../static/plugins/easyui/themes/icon.css">
		<link href="../static/css/bootstrap.css" rel='stylesheet' type='text/css'/>
		<link href="../static/css/book.css" rel='stylesheet' type='text/css'/>
		<#nested>
	</head>
</#macro>

<#macro navTop>
	<div class="col-sm-12 nav-flat" style="background: #2D3E50;">
		<img alt="" src="../static/img/logo.png" style="margin: 10px 10%">
		<ul class="nav nav-top navbar-right">
			<li><a href="#">教材吧入口</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> 新消息<span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a href="#">Swing</a></li>
					<li><a href="#">jMeter</a></li>
					<li><a href="#">EJB</a></li>
					<li class="divider"></li>
					<li><a href="#">分离的链接</a></li>
				</ul></li>
			<li><a href="#">退出登录</a></li>
		</ul>
	</div>
</#macro>
<#--active 指定左侧导航栏的item为选中url-->
<#--urls 左侧导航栏item url-->
<#--labels 左侧导航栏的item的显示-->
<#macro menu id,title="我的菜单",width="10%">
	<div data-options="region:'west',title:'${title}',split:true,boder:false" style="width: ${width};padding:0px;">
		<ul id="${id}" class="easyui-tree"></ul>
	</div>
</#macro>

<#macro tabs id>
<div data-options="region:'center',split:true" style="width: 100%; height:100%;padding:0px; border: none;">
	<div id="${id}" class="easyui-tabs" style="width: 100%; height: 100%;">
		<#nested>
	</div>
</div>
</#macro>

<#macro tab>
	<div style="padding: 20px;">
		<#nested>
	<div>
</#macro>

<#-- columnMethod 定义列表栏目的js方法，返回数组对象，参考easyui的column定义-->
<#macro table id columnMethod>
	<style>
		.panel-body{
			padding: 0px;
		}
	</style>
	<table id="${id}" style="min-height: 300px;"></table>
	<script type="text/javascript">
		$(document).ready(function(){
			var col = ${columnMethod}();
			$('#${id}').datagrid({
				loadMsg:"正在装载数据，请稍后...",
				emptyMsg:"暂无数据",
				fitColumns:true, //自动适应宽
				striped:true, //行条纹
				pagination:true,//分页
				rownumbers:true,//显示行码
				singleSelect:true,//单选
				ctrlSelect:true,//ctrl+单击 可多选
				pageSize:10,
				toolbar:'#tb',
			    columns:col
			});
		});
	</script>
	
</#macro>


