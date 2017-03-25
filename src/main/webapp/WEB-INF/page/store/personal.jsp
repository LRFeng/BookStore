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

</style>

</head>
<body>
<div class="wrap">	
	<div class="container">
   		<jsp:include page="head.jsp"></jsp:include>
   		<div class="col-sm-3 content-left">
				<div>
					<a href="personal" class="list-item myactive">个人信息</a> 
					<a href="message" class="list-item">我的消息</a> 
					<a href="order" class="list-item">我的书单</a> 
					<a href="personal-post" class="list-item">我的帖子</a>
					<a href="modify-pass" class="list-item">修改密码</a>
				</div>
			</div>
			<div class="col-sm-8 content-right register">
		  	  <form> 
				 <div class="register-top-grid">
					<h1 align="center">个人信息</h1>
					<div align="center" style="min-width: 150px; min-height: 150px;">
						<c:choose>
							<c:when test="${not empty user.avatar}">
								<img src="${user.avatar}" width="100px" height="100px" style="border-radius: 50px;">
							</c:when>
							<c:otherwise>
								<img src="./static/img/head-default.png" width="100px" height="100px" style="border-radius: 50px;">
							</c:otherwise>
						</c:choose>
						<p style="margin-top: 5px;"><input type="button" title="修改头像" value="修改头像" class="btn btn-default btn-sm glyphicon glyphicon-edit" onclick="showImageView()"/></p>
					</div>
					 <div>
						<span>昵称<label>*</label></span>
						<input id="name" type="text" disabled="disabled" value="${user.name}"> 
					 </div>
					 
					 <div>
						 <span>邮箱地址<label>*</label></span>
						 <input id="email" name="email" type="text" value="${user.email}"> 
					 </div>
					 
					 <div>
						 <span>联系电话</span>
						 <input id="telephone" type="text" value="${userInfo.telephone}"> 
					 </div>
					 <div>
						 <span>学校</span>
						 <input id="school" type="text" value="${userInfo.school}"> 
					 </div>
					 <div>
						 <span>专业</span>
						 <input id="specialist" type="text" value="${userInfo.specialist}"> 
					 </div>
					 <div>
						 <span>入学年份</span>
						 <input id="collegeYear" type="text" value="${userInfo.collegeYear}"> 
					 </div>
					 <div>
						 <span>现居住地</span>
						 <input id="address" type="text" value="${userInfo.address}"> 
					 </div>
				</div>
			</form>
				<div class="clearfix"> </div>
				<div class="register-but">
					   <Button id="editBtn" class="btn-all glyphicon glyphicon-edit"> 修改</Button>
					   <Button id="saveBtn" class="btn-all 	glyphicon glyphicon-ok" style="display: none;"> 保存</Button>
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
	
	<script type="text/javascript">
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
				'<input id="saveImageBtn" class="btn-all" type="button" style="margin: 20px; display:none;" onclick="saveImage()" value="保存">'+
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
			$("#saveImageBtn").css('display','');
		}else{
			alert(data.msg);
		} 
	});

	uploader.on('uploadError', function( file,reason) {
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
	
	function showImageView(){
		$('#updateImage').modal('show');
	}
	
	function saveImage(){
		$.ajax({
			url:"save-user-image",
			type:"POST",
			data:{
				avatar:$("#avatar").val()
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
	}
	
	
	</script>
	
	
	
	</div>
  	
  	

</div>
</div>
</div>	

<script type="text/javascript">

	$(document).ready(function(){
		$("input[type='text']").attr("disabled",true);
		
		
		$("#editBtn").click(function(){
			$("input[type='text']").attr("disabled",false);
			$("input[name='email']").attr("disabled",true);
			$(this).css("display","none");
			$("#saveBtn").css("display","");
		});
		
		$("#saveBtn").click(function(){
			var name = $("#name").val()
			if(name==null ||name==''){
				alert("昵称不能为空");
				return;
			}
			
			$.ajax({
				url:"save-user",
				type:"POST",
				data:{
					name:name,
					telephone:$("#telephone").val(),
					school:$("#school").val(),
					specialist:$("#specialist").val(),
					collegeYear:$("#collegeYear").val(),
					address:$("#address").val(),
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