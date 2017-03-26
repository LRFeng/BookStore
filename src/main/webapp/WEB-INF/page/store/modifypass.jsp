<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link href="./static/css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Custom Theme files -->
<link href="./static/css/style.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<script type="text/javascript" src="./static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="./static/js/bootstrap.min.js"></script>
<!-- start menu -->
<link href="./static/css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="./static/js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>

<link rel="stylesheet" type="text/css" href="./static/plugins/webupload/css/webuploader.css">
<script type="text/javascript" src="./static/plugins/webupload/js/webuploader.min.js"></script>


<link href="./static/css/flexslider.css" rel='stylesheet' type='text/css' />
<script defer src="./static/js/jquery.flexslider.js"></script>

<style type="text/css">

.content-left{
	padding: 0px;
}

.content-right{
	min-height: 300px;
	padding-top: 20px;
	border-left: 1px solid rgba(0,0,0,0.04);
	margin-bottom: 50px;
}

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

.content-right p{
	min-width: 60%;
	margin: 20px 10%;
}

</style>

</head>
<body>
<div class="wrap">	
	<div class="container">
   		<jsp:include page="head.jsp"></jsp:include>
   		<div class="col-sm-3 content-left">
				<div>
					<a href="personal" class="list-item">个人信息</a> 
					<a href="message" class="list-item">我的消息</a> 
					<a href="order" class="list-item">我的书单</a> 
					<a href="my-post" class="list-item">我的帖子</a>
					<a href="modify-pass" class="list-item myactive">修改密码</a>
				</div>
			</div>
			<div class="col-sm-8 content-right register">
		  	  <form>
					<div class="register-top-grid">
						<h2>修改密码</h2>
						<p>
							<span>旧密码<label>*</label></span> <input id="oldPass"
								type="password">
						</p>
						<p>
							<span>新密码<label>*</label></span> <input id="newPass"
								type="password">
						</p>
						<p>
							<span>确认密码<label>*</label></span> <input id="newPass2"
								type="password">
						</p>
						<div class="clearfix"></div>
					</div>
				</form>
				<div class="clearfix"> </div>
				<div class="register-but">
					   <Button id="saveBtn" class="btn-all 	glyphicon glyphicon-ok"> 保存</Button>
					   <div class="clearfix"> </div>
				</div>
			</div>
   		
   		
	<div class="content">
  	<div class="content_box">
		<jsp:include page="footer.jsp"></jsp:include>
  	</div>
  	
 <div class="modal fade" id="updateImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				修改头像
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			</div>
			<div class="modal-body">
				<div id="fileList" class="uploader-list" style="width: 100%;"></div>
					<input id="avatar" type="hidden">
					<div id="filePicker" style="margin: 20% 40%;">头像上传</div>
					<input id="avatar" type="hidden">
				</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
  	
  	

</div>
</div>
</div>	

<script type="text/javascript">

	$(document).ready(function(){
		$("#saveBtn").click(function(){
			var oldPass = $("#oldPass").val();
			var newPass = $("#newPass").val()
			var newPass2 = $("#newPass2").val()
			if(oldPass==null ||oldPass==''||newPass==null ||newPass==''||newPass2==null ||newPass2==''){
				alert("密码不能为空");
				return;
			}
			
			$.ajax({
				url:"do-modify-pass",
				type:"POST",
				data:{
					oldPass:oldPass,
					newPass:newPass,
					newPass2:newPass2
				},
				success:function(resp){
					var data = eval("("+resp+")");
					if(data.success){
						alert(data.msg);
						window.location.reload();
					}else{
						alert(data.msg);
					}
				},
				error:function(resp){
					alert(resp);
				}
			});
		});
		
		
	});
	

</script>




</body>
</html>		