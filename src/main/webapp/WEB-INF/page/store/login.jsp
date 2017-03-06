<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>教材吧-登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Lookz Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link href="./static/css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Custom Theme files -->
<link href="./static/css/style.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<!--webfont-->
<script type="text/javascript" src="./static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="./static/js/bootstrap.min.js"></script>
<!-- start menu -->
<link href="./static/css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="./static/js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>
</head>
<body>
<div class="wrap">	
<div class="container">
     <jsp:include page="head.jsp"></jsp:include>
<div class="content">
  <div class="content_box">
	<div class="men">
	 <div class="account-in">
							<h2>账户</h2>
							<div class="col-md-7 account-top">
								<div> 	
									<span>邮箱地址*</span>
									<input id="email" type="text"> 
								</div>
								<div> 
									<span class="pass">密码*</span>
									<input id="password" type="password">
								</div>				
									<Button id="loginBtn" class="btn-all">登录</Button>
							</div>
							<div class="col-md-5 left-account ">
								<a href="single.html"><img class="img-responsive " src="./static/img/s4.jpg" alt=""></a>
								<div class="five-in">
								<h1>25% </h1><span>discount</span>
								</div>
								<a href="./register" class="create">去注册</a>
								<div class="clearfix"> </div>
							</div>
						<div class="clearfix"> </div>
					</div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</div>
</div>
</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("#loginBtn").click(function(){
			var email = $("#email").val();
			var password = $("#password").val();
			if(email==null || email==''){
				alert("邮箱地址不能为空");
			}else if(password==null || password==''){
				alert("密码不能为空");
			}else{
				$.ajax({
					url:"do-login",
					type:"POST",
					data:{
						email:email,
						password:password
					},
					success:function(resp){
						var data = eval("("+resp+")")
						if(data.success){
							window.location.href="./"
						}else{
							alert(data.msg)
							return;
						}
					},
					error:function(resp){
						console.log(resp);
						alert("服务器出错");
					}
				});
			}
		}); 
	});
	
	
	


</script>



</body>
</html>		