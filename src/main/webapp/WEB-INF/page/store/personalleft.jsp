<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
.list-item{
	display:block;
	width: 100%;
	height: 50px;
	border: 1px solid rgba(0,0,0,0.1);
	line-height: 50px;
	padding-left: 20%;
	color: black;
	text-decoration: none;
}

.list-item:link{
	text-decoration: none;
}


.list-item:hover,.list-item:ACTIVE,.myactive{
	text-decoration:none;
	background-color: #e05b5b;
	color: white;
}
</style>

</head>
<body>
	<div>
		<a href="personal" class="list-item myactive">个人信息</a>
		<a href="#" class="list-item">我的消息</a>
		<a href="#" class="list-item">我的书单</a>
		<a href="#" class="list-item">我的帖子</a>
		<a href="#" class="list-item">修改密码</a>
	</div>
</body>

<script type="text/javascript">
$(document).ready(function(){
	
	$(".list-item").click(function(){
		$(".list-item").css("color","black");
		$(".list-item").siblings().removeClass("myactive");
		$(this).addClass("myactive");
		$(this).css("color","white");
	});
});
	
</script>


</html>