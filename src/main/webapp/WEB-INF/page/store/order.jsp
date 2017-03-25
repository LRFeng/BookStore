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

.book-info{
	background-color: rgba(0,0,0,0.1);
	margin: 10px;
}

.book-info div span{
	margin-right: 30px;
}

.more-ignore{
	white-space: nowrap;
	overflow:hidden; 
	text-overflow:ellipsis;
}

.book-info p{
	padding: 5px 0px;
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
					<a href="order" class="list-item myactive">我的书单</a> 
					<a href="personal-post" class="list-item">我的帖子</a>
					<a href="modify-pass" class="list-item">修改密码</a>
				</div>
			</div>
			<div class="col-sm-9 content-right register">
				<ul id="myTab" class="nav nav-tabs">
					<li id="tabAll"><a href="order?state=all">所有书单</a></li>
					<li id="tabWait"><a href="order?state=wait">待取书单</a></li>
					<li id="tabFinished"><a href="order?state=finished">已取书单</a></li>
				</ul>
				
				<div class="tab-pane fade in active" style="background-color: rgba(0,0,0,0.04);">
        			<c:forEach var="order" items="${orders}">
	        			<div class="col-sm-11 book-info">
					 		<p style="border-bottom: 2px solid rgba(0,0,0,0.1);">
					 			<strong>订单号：<a href="order-detail?id=${order.id}" style="color: #005ea7;">${order.id}(订单详情)</a></strong>
					 			<span style="float: right;">预定时间：<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd"/> </span>
					 		</p>
					 		<p>取书时间段：<fmt:formatDate value="${order.extractStartDate}" pattern="yyyy年MM月dd日 HH:mm"/> - <fmt:formatDate value="${order.extractEndDate}" pattern="yyyy年MM月dd日 HH:mm"/> 
					 		<span style="float: right;color: red;">总计：共${order.count}本   <fmt:formatNumber value="${order.sum}" pattern="0.00"></fmt:formatNumber> 元</span> </p>
					 		<p>我的留言：${order.userMessage}</p>
					 		<p>商家回复：${order.storeMessage}</p>
					 	</div>
					</c:forEach>
    			</div>	
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

<script type="text/javascript">

	$(document).ready(function(){
		var state ="${param.state}";
		if(state=="wait"){
			$("#tabWait").addClass("active");
		}else if(state=="finished"){
			$("#tabFinished").addClass("active");
		}else{
			$("#tabAll").addClass("active");
		}
	});
		
	function goPage(page){
		var max = parseInt("${pager.countPage}");
		if(page==undefined||page==null || page==0){
			page=$(".myPager input").val();
			if(page<1||page>max){
				page =1;
			}
		}
		window.location.href="order?state=${param.state}&page="+page;
	}

</script>




</body>
</html>		