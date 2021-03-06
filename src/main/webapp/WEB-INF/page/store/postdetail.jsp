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
	
.post-content{
	padding: 15px 0px;
}

.content-left img{
	border-radius: 30px;
	width: 60px;
	height: 60px;
}

.content-right p{
	margin-top:0px;
}

.content-right img{
	max-height: 100px;
	margin: 0px 20px;
}

.content-left button{
	color: gray;
	border: none;
	background-color: rgba(0,0,0,0);
	
}

.comment-content{
	padding: 15px;
	border-bottom: 1px rgba(0,0,0,0.04) solid;
}

.comment-content a{
	color: blue;
}

.write-post{
	border:1px rgba(0,0,0,0.1) dashed;
	border-bottom:none;
	margin: 15px 0px;
}

.file-option{
	margin: 20px 0px;
}

.file-option input[type='button']{
	margin-right: 100px;
}

	
</style>


</head>
<body>
<div class="wrap">	
	<div class="container">
   		<jsp:include page="posthead.jsp"></jsp:include>
	<div class="content">
  	<div class="content_box">
  		<div class="col-md-12 post-content">
  				<c:set var="puser" value="${userMap[post.uid]}"></c:set>
				<div class="col-md-1 content-left">
					<img src="${puser.avatar}">
					<span>${puser.name }</span>
					<button id="likeBtn-${post.id}" onclick="likePost(${post.id})">赞(<span>${post.like}</span>)</button>
				</div>
				<div class="col-md-11 content-right">
				  <div class="col-md-12">
					<div class="col-md-12" style="border-left:1px solid rgba(0,0,0,0.1);border-bottom:1px solid rgba(0,0,0,0.1);padding-bottom: 15px;">
						<h4><a href="#" style="color: blue;">${post.title}</a><small style="float: right;">
						发布时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${post.createDate}"/> </small> </h4>
						<p style="padding-left: 10px;">${post.content}</p>
						<p>	
							<c:forEach var="url" items="${urls}">
								<img src="${url}">
							</c:forEach>
						</p>
						<div class="form-group" style="margin-top: 30px;">
						    <div class="col-sm-12">
						   		<textarea name="comment" placeholder="说点什么..."  class="form-control" rows="3"></textarea>
						   		<button class="btn-all" style="margin-top:5px;font-size: 10px;" onclick="comment()">评论</button>
						    </div>
						 </div>
					</div>
					<div class="col-md-12" style="min-height: 300px;border-left:1px solid rgba(0,0,0,0.1);">
					<p>已有${post.commentNum}条评论：</p>
					<c:forEach var="comment" items="${comments}">
						<div class="col-md-12 comment-content">
						<p><small style="color: gray; float: right;"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${comment.createDate}"/></small></p>
						<p>
						<c:set var="cuser" value="${userMap[comment.cuid]}"></c:set>
							<c:choose>
								<c:when test="${comment.suid==0}">
									<a href="#">${cuser.name }</a>
								</c:when>
								<c:otherwise>
									<c:set var="suser" value="${userMap[comment.suid]}"></c:set>
									<a href="#">${cuser.name }</a>
									<span>回复</span>
									<a href="#">${suser.name }</a>
								</c:otherwise>
							</c:choose>
							:
								<span>${comment.content}
									<button title="回复" class="glyphicon glyphicon-comment" onclick="showResponse(${comment.id})" style="color: #e05b5b;border:none;background: none;"></button>
								</span>
							</p>
							<div class="form-group" style="margin-top: 10px;">
						    <div id="responseAera-${comment.id}" class="col-sm-12" style="display: none;">
						   		<textarea id="response-${comment.id}" placeholder="说点什么..."  class="form-control" rows="3"></textarea>
						   		<button class="btn-all" style="margin-top:5px;;font-size: 10px;" onclick="comment(${cuser.id},${comment.id})">回复</button>
						    </div>
						 </div>
						</div>
					</c:forEach>
					</div>
					</div>
				</div>
			</div>
			
		    		<div id="addPost" class="col-md-12 write-post">
		<h3 style="margin-top: 20px;">发布区  <small><button class="btn-all" style="float: right;" onclick="publicPost()">发布</button></small> </h3>
		<form class="form-horizontal">
			<div class="form-group">
			 	 <label class="col-sm-1 control-label">类型</label>
			 	 <div class="col-sm-11">
			 	 	 <c:forEach var="tag" items="${postTags}">
				 	 	 <label class="checkbox-inline">
						    <input type="radio" name="postTag" value="${tag.id}" checked>${tag.name}
						  </label>
					</c:forEach>
				  </div>
				</div>
		  <div class="form-group">
		    <label class="col-sm-1 control-label">标题</label>
		    <div class="col-sm-11">
		      <input id="postTitle" type="text" class="form-control" id="firstname">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="lastname" class="col-sm-1 control-label">内容</label>
		    <div class="col-sm-11">
		   		<textarea id="postContent" class="form-control" rows="10"></textarea>
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <div class="col-sm-12">
		    		<h3><small>最多可上传3张图片</small></h3>
					<div id="fileList" class="uploader-list"></div>
					<div id="filePicker">图片选择</div>
					<div class="col-sm-12 file-option" style="display: none;">
						<input id="uploadBtn" class="btn-all" onclick="upload()" type="button" value="上传"/>
						<input class="btn-all" onclick="cancel()" type="button" value="取消"/>
					</div>
					<input id="fids" type="hidden">
		    </div>
		  </div>
		  
		</form>
		</div>	
		
		<jsp:include page="footer.jsp"></jsp:include>
  	</div>

</div>
</div>
</div>	

<script type="text/javascript">
function showResponse(pid){
	console.log($("#responseAera-"+pid).css("display"));
	if($("#responseAera-"+pid).css("display")=="none"){
		$("#responseAera-"+pid).css("display","");
	}else{
		$("#responseAera-"+pid).css("display","none");
	}
}



function comment(suid,cid){
	var pid="${post.id}";
	var content="";
	if(suid==undefined||suid==null){
		suid="";
		content = $("textarea[name='comment']").val();
	}else{
		content = $("#response-"+cid).val();
	}
	alert(content);
	$.ajax({
		url:"post-comment",
		type:"post",
		data:{
			pid:pid,
			suid:suid,
			content:content
		},
		success:function(resp){
			var data =eval("("+resp+")");
			if(data.success){
				window.location.reload();
			}else{
				alert(data.msg);
			}
		},
		error:function(resp){
			
		}
		
	});
}

var fids ='';
var flag = false;

var uploader = WebUploader.create({
    server: 'common/single-file-upload',
    pick: '#filePicker',
    fileNumLimit:3,
    // 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});

uploader.on( 'fileQueued', function( file ) {
    var $li = $('<div id="' + file.id + '" class="col-sm-4"><img></div>'),
        $img = $li.find('img');
    // $list为容器jQuery实例
   $("#fileList").append( $li );
   
    // 创建缩略图
    uploader.makeThumb( file, function( error, src ) {
        if ( error ) {
            $img.replaceWith('<span>不能预览</span>');
            return;
        }
        $img.attr( 'src', src );
    }, 200);
});

uploader.on('filesQueued',function(files){
	$("#filePicker").css('display','none');
	$(".file-option").css('display','');
});


uploader.on('uploadSuccess', function(file,response) {
	var data = eval("("+response._raw+")");
	 if(data.success){
		 flag = true;
		 fids=fids+data.fid+":";
	}else{
		 flag = false;
	} 
});

uploader.on('uploadFinished', function() {
	if(flag){
		fids = fids.substring(0,fids.length-1);
		$("#fids").val(fids);
		$("#uploadBtn").css('display','none');
		alert("图片上传成功");
	}else{
		alert("文件上传失败");
	} 
});



uploader.on('uploadError', function( file,reason) {
   flag = false;
});


function upload(){
	uploader.upload();
}

function cancel(){
    $("#fileList").text("");
	$("#filePicker").css('display','');
	$(".file-option").css('display','none');
	uploader.reset(); 
}

function publicPost(){
	var tag = $("input[name='postTag']:checked").val();
	var title = $("#postTitle").val();
	var content = $("#postContent").val();
	var fids = $("#fids").val();
	
	if(tag==''){
		alert("没有选择新帖类型");
		return;
	}else if(title==''){
		alert("标题不能为空");
		return;
	}
	
	$.ajax({
		url:'public-post',
		type:'post',
		data:{
			tag:tag,
			title:title,
			content:content,
			fids:fids
		},
		success:function(resp){
			var data = eval("("+resp+")");
			if(data.success){
				window.location.reload();
			}else{
				alert(data.msg);
			}
		},
		error:function(resp){
			alert("发布失败");
			console.log(resp);
		}
	});
}

</script>


</body>
</html>		