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

.more-ignore{
	white-space: nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
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
   		<jsp:include page="head.jsp"></jsp:include>
   		<div class="col-sm-3 content-left">
				<div>
					<a href="personal" class="list-item">个人信息</a> 
					<a href="message" class="list-item">我的消息</a> 
					<a href="order" class="list-item">我的书单</a> 
					<a href="my-post" class="list-item myactive">我的帖子</a>
					<a href="modify-pass" class="list-item">修改密码</a>
				</div>
			</div>
			<div class="col-sm-9 content-right register">
				<table class="table" style="margin-top: 20px">
					<caption>我的帖子</caption>
					<thead style="background: rgba(0, 0, 0, 0.04);">
						<tr>
							<th>标题</th>
							<th>评论数</th>
							<th>点赞数</th>
							<th>发布时间</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="post" items="${posts}">
							<tr>
								<td title="" style="max-width: 100px;" class="more-ignore">${post.title}</td>
								<td>${post.like}</td>
								<td>${post.commentNum}</td>
								<td><fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${statusMap[post.status]}</td>
								<td>
									<c:choose>
										<c:when test="${post.status==1}">
											<a href="javascript:showModal(3,${post.id})">撤回</a> <a href="detail?id=${post.id }">查看</a> <a href="javascript:showModal(0,${post.id})">删除</a>
										</c:when>
										<c:when test="${post.status==2}">
											<a href="javascript:showModal(1,${post.id})">发布</a> <a href="detail?id=${post.id }">查看</a> <a href="javascript:showModal(0,${post.id})">删除</a>
										</c:when>
										<c:when test="${post.status==3}">
											<a href="javascript:showModal(1,${post.id})">重新发布</a> <a href="detail?id=${post.id }">查看</a> <a href="javascript:showModal(0,${post.id})">删除</a>
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
			</div>
   		
   		
	<div class="content">
  	<div class="content_box">
		<jsp:include page="footer.jsp"></jsp:include>
  	</div>

</div>
</div>
</div>	

<div class="modal fade" id="operationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<input id="operationState" type="hidden">
				<input id="postId" type="hidden">
				<button type="button"  class="btn btn-default" data-dismiss="modal">取消
				</button>
				<button id="saveBtn" onclick="operationPost()" type="button" class="btn btn-primary">
					确认
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>


<script type="text/javascript">

	function showModal(state,pid){
		var content = "";
		if(state==1){
			content = "是否发布此帖子？";
		}else if(state==0){
			content = "是否删除此帖子？";
		}else if(state==3){
			content = "是否撤回此帖子？";
		}
		$(".modal-body").text(content);
		$("#operationState").val(state);
		$("#postId").val(pid);
		$("#operationModal").modal('show');
	}

	function operationPost(){
		$.ajax({
			url:"operation-post",
			type:'post',
			data:{
				pid:$("#postId").val(),
				state:$("#operationState").val()
			},
			success:function(resp){
				var data = eval('('+resp+')');
				if(data.success){
					alert(data.msg);
					window.location.reload();
				}else{
					alert(data.msg);
				}
			},
			error:function(resp){
				alert("服务出错");
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
		window.location.href="my-post?page="+page;
	}

</script>




</body>
</html>		

</head>
<body>