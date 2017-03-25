<#import "page.ftl" as Page>

<!DOCTYPE html>
<html>
	<@Page.head title="首页">
		<script type="text/javascript" src="../dwr/interface/menuService.js"></script>
	</@Page.head>
	
	<body>
		<@Page.navTop/>
		<div class="easyui-layout" style="width: 100%; height:550px;">
			<@Page.menu id="menu"/>
			<@Page.tabs id="menuTabs">
				<div title="首页" style="padding: 20px; display: none;"></div>
			</@Page.tabs>
		<div>
	</body>


<script type="text/javascript">
	
	$(document).ready(function(){
		//加载菜单
		menuService.queryMenus({
			callback:function(data){
				$("#menu").tree({
					data:data,
					animate:true,
					onClick: function(node){
						if(node.children!=null){//目录
							$("#menu").tree('toggle',node.target);
						}else{//向tab添加选项卡
							if($('#menuTabs').tabs('exists',node.text)){
								$('#menuTabs').tabs('select',node.text);
							}else{
								$('#menuTabs').tabs('add',{
									title:node.text,
									id:node.id,
									href:node.attributes.url,
									closable: true
					            });
							}
						}
					}
				});
			},
			timeout:5000,
			errorHandler:function(message) { 
				alert("菜单加载失败");
				consol.log(message);
			}
		});
	});
</script>

	
</html>