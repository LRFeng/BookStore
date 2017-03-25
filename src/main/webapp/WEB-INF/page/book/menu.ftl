<#import 'page.ftl' as Page>

<script type="text/javascript">
	function menuCol(){
		var col = [[
	       	{field:'id',title:'编号',width:100},
	        {field:'name',title:'菜单名',width:100},
	        {field:'url',title:'资源路径',width:100},
	        {field:'isFile',title:'是否页面',width:100,formatter:formatterBoolean},
	        {field:'status',title:'状态',width:100,align:'right',formatter:formatterStatus}
		]]
		return col;
	}
</script>

<@Page.tab>
	<@Page.table id="menuTable" columnMethod="menuCol"/>
	
	
	
</@Page.tab>


<script type="text/javascript">
	function loadData(){
		menuService.listMemu(function(data){
			console.log(data);
			$('#menuTable').datagrid('loadData',data);
		});
	}
	
	$(document).ready(function(){
		loadData();
	});


</script>