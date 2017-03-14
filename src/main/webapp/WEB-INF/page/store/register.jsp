<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>教材吧-注册</title>
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

<link rel="stylesheet" type="text/css" href="./static/plugins/webupload/css/webuploader.css">
<script type="text/javascript" src="./static/plugins/webupload/js/webuploader.min.js"></script>


<style type="text/css">
.head-eair{
	height: 280px;
	border:1px dotted gray;
	min-width: 96%;
}
</style>

</head>
<body>
<div class="wrap">	
<div class="container">
      <div class="header_top">
		<jsp:include page="head.jsp"></jsp:include>
		
<div class="content">
  <div class="content_box">
	<div class="men">
	 <div class="register">
		  	  <form> 
				 <div class="register-top-grid">
					<h1>个人信息</h1>
					<div>
						<div class="head-eair">
							<div id="fileList" class="uploader-list" style="width: 100%;"></div>
							<div id="filePicker" style="margin: 20% 40%;">头像上传</div>
							<input id="avatar" type="hidden">
						</div>
					</div>
					 <div>
						<span>昵称<label>*</label></span>
						<input id="name" type="text"> 
					 </div>
					 
					 <div>
						 <span>邮箱地址<label>*</label></span>
						 <input id="email" type="text"> 
					 </div>
					 
					 <div>
						 <span>联系电话</span>
						 <input id="telephone" type="text"> 
					 </div>
					 <div>
						 <span>学校</span>
						 <input id="school" type="text"> 
					 </div>
					 <div>
						 <span>专业</span>
						 <input id="specialist" type="text"> 
					 </div>
					 <div>
						 <span>入学年份</span>
						 <input id="collegeYear" type="text"> 
					 </div>
					 <div>
						 <span>现居住地</span>
						 <input id="address" type="text"> 
					 </div>
					 <div class="clearfix"> </div>
					 <a class="news-letter" href="#">
						<!--  <label class="checkbox"><input type="checkbox" name="checkbox" checked="checked"><i> </i>注册为新账户</label> -->
					   </a>
					 </div>
				     <div class="register-bottom-grid">
						    <h2>登录信息</h2>
							 <div>
								<span>密码<label>*</label></span>
								<input id="password" type="password">
							 </div>
							 <div>
								<span>确认密码<label>*</label></span>
								<input id="password2" type="password">
							 </div>
							 <div class="clearfix"> </div>
					 </div>
				</form>
				<div class="clearfix"> </div>
				<div class="register-but">
					   <Button id="registerBtn" class="btn-all">提交</Button>
					   <div class="clearfix"> </div>
				</div>
		   </div>
    </div>
		<jsp:include page="footer.jsp"></jsp:include>
     </div>
   </div>
</div>	
</div>
</div>
<script type="text/javascript">

$(document).ready(function(){
	$("#registerBtn").click(function(){
		var name = $("#name").val()
		var email = $("#email").val()
		var password = $("#password").val()
		var password2 = $("#password2").val()
		if(name==null ||name==''){
			alert("昵称不能为空");
			return;
		}else if(email==null || email ==''){
			alert("邮箱地址不能为空");
			return;
		}else if(password==null || password ==''){
			alert("密码不能为空");
			return;
		}else if(password!=password2){
			alert("密码不一致");
			return;
		}
		
		$.ajax({
			url:"do-register",
			type:"POST",
			data:{
				name:name,
				email:email,
				avatar:$("#avatar").val(),
				telephone:$("#telephone").val(),
				school:$("#school").val(),
				specialist:$("#specialist").val(),
				collegeYear:$("#collegeYear").val(),
				address:$("#address").val(),
				password:password,
				password2:password2
			},
			success:function(resp){
				var data = eval("("+resp+")");
				if(data.success){
					alert(data.msg);
					window.location.href="login";		
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



var uploader = WebUploader.create({
	server: './common/single-file-upload',
	pick:{
		id:'#filePicker',
		multiple:false
	},
	accpet:{
		title:'Images',
		extensions:'gif,jpg,jpeg,bmp,png',
		mineType:'image/*'
	}
})

uploader.on('fileQueued',function(file){
	var $li = $('<div id="'+file.id+'" class="file-item thumbail"style="width:100%;">'+
			'<img style="float: left; margin: 20px 20px;">'+
			'<div style="width: 20%;"><input id="uploadBtn" class="btn-all" style="margin: 20px;" type="button" onclick="uploadFile()" value="上传">'+
			'<input class="btn-all" type="button" style="margin: 20px;" onclick="resetImage()" value="取消"></div></div>'),
		$img = $li.find('img');
	$("#fileList").append($li);
	$("#filePicker").css('display','none');
	uploader.makeThumb(file,function(error,src){
		if(error){
			$img.replaceWith('<span>不能预览</span>');
			return;
		}
		$img.attr('src',src);
	},200,200)
});

uploader.on('uploadProgress',function(file,percetage){
	console.log("文件上传进度："+percetage);
});

uploader.on( 'uploadSuccess', function(file,response) {
	var data = eval("("+response._raw+")");
	 if(data.success){
		$("#avatar").val(data.fid);
		alert("头像上传成功");		
		$("#uploadBtn").css('display','none');
	}else{
		alert(data.msg);
	} 
});

uploader.on( 'uploadError', function( file,reason) {
   alert("文件上传出错");
   console.log(reason);
});

function uploadFile(){
	uploader.upload();
}

function resetImage(){
	$("#avatar").val("");
    $("#fileList").text("");
	$("#filePicker").css('display','');
	uploader.reset(); 
}

</script>
</body>
</html>		