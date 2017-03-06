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
	border-bottom: 1px rgba(0,0,0,0.04) solid;
	padding: 15px 0px;
}

.post-content:hover{
 	background-color: rgba(0,0,0,0.04);
}

.content-left img{
	border-radius: 30px;
	width: 60px;
	height: 60px;
}

.content-right p{
	margin-top:0px;
	margin-bottom: 15px;
}

.content-right img{
	max-height: 100px;
	margin: 0px 20px;
}

.content-right p button{
	margin-left: 20px;
	color: gray;
	border: none;
	float:right;
	background-color: rgba(0,0,0,0);
	
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
	
.myPager{
	margin: 30px auto; 
}

.myPager li{
	float: left;
	list-style: none;
	margin-right: 20px;
}

	
	
</style>


</head>
<body>
<div class="wrap">	
	<div class="container">
   		<jsp:include page="posthead.jsp"></jsp:include>
	<div class="content">
  	<div class="content_box">
  		<c:if test="${empty posts}">
  			<div style="height: 380px;" align="center">
  				<strong style="line-height: 380px;">暂时没有相关帖子哟！！！</strong>
  			</div>
  		</c:if>
  		<c:forEach var="post" items="${posts}">
  			<c:set var="user" value="${userMap[post.id]}"></c:set>
			<div class="col-md-12 post-content">
				<div class="col-md-1 content-left">
					<img src="${user.avatar}">
					<span>${user.name }</span>
				</div>
				<div class="col-md-11 content-right">
					<h4><a href="#" style="color: blue;">${post.title}</a><small style="float: right;">
					发布时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${post.createDate}"/> </small> </h4>
					<p style="padding-left: 10px;">${post.content}</p>
					<p>	
						<c:forEach var="url" items="${urlMap[post.id]}">
							<img src="${url}">
						</c:forEach>
					</p>
					<p><button>评论(${post.commentNum})</button><button>赞(${post.like})</button></p>
				</div>
			</div>
		</c:forEach>
		
		<div class="col-md-12 myPager">
			<ul>
				<li>
					<c:choose>
						<c:when test="${pager.currPage<2}">
							<button disabled="disabled">上一页</button>
						</c:when>
						<c:otherwise>
							<button onclick="goPage(${pager.currPage-1})">上一页</button>
						</c:otherwise>
					</c:choose>
				</li>
				<li>
					<span>${pager.currPage}/${pager.countPage}</span>  
					跳转到<input type="text" style="width: 30px;"/>页
					<button onclick="goPage()">GO</button>
				</li>
				<li>
					<c:choose>
						<c:when test="${pager.currPage==pager.countPage}">
							<button disabled="disabled" onclick="goPage(${pager.currPage+1})">下一页</button>
						</c:when>
						<c:otherwise>
							<button>下一页</button>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
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

function goPage(page){
	var max = parseInt("${pager.countPage}");
	if(page==undefined||page==null || page==0){
		page=$(".myPager input").val();
		if(page<1||page>max){
			page =1;
		}
	}
	window.location.href="post?tag=${param.tag}&keyword="
			+"${param.keyword}&sort=${param.sort}&page="+page;
}

</script>


</body>
</html>		