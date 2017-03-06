<%@page import="javafx.scene.control.Tab"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="./static/js/jquery.cookie.js"></script>
<style type="text/css">
.user-info{
	height: 100%;
	float: right;
}

.user-info li{
	float: right;
	line-height: 70px;
	list-style: none;
}

.dropdown-menu li{
	width: 100%;
	text-align: center;
	
}
.user-info img{
	border-radius: 30px;
	width: 60px;
	height: 60px;
	margin-right: 10px;
}


.login-btn{
    border: 2px solid #e05b5b;
    color: #000;
    padding: 5px 12px;
    text-decoration: none;
    font-size: 1em;
    font-weight: 500;
    margin-right: 20px;
}

.login-btn:hover{
    background-color:#e05b5b;
    color: #000;
    text-decoration: none;
    font-size: 1em;
}

.h_nav li{
	float: left;
	width: 200px;
    margin-right: 40px;
}

</style>
</head>
<body>
	<div class="header_top">
		  <div class="col-sm-9 h_menu4">
				<ul class="megamenu skyblue">
					  <li class="active grid"><a class="color8" href="index.html">最新教材</a></li>	
				      <li><a class="color1" href="#">教材分类</a><div class="megapanel">
						<div class="row">
							<div class="col-md-12">
								<div class="h_nav">
									<h4>热门专业教材分类</h4>
									<ul>
										<c:forEach var="specialist" items="${specialists}">
											<li><a href="search?spid=${specialist.id}&keyword=&sort=sale:1&page=1">${specialist.name}</a></li>
										</c:forEach>
									</ul>	
								</div>							
							</div>
						  </div>
						</div>
					</li>
				    <li class="grid"><a class="color2" href="post?tag=&keyword=&sort=createDate:0&page=1"">教材论坛</a>
					  <div class="megapanel">
						<div class="row">
							<div class="col1">
								<div class="h_nav">
									<ul>
										<c:forEach var="tag" items="${postTags}">
											<li><a href="post?tag=${tag.id}&keyword=&sort=createDate:0&page=1">${tag.name}</a></li>
										</c:forEach>
									</ul>	
								</div>							
							</div>
							
							
						  </div>
						</div>
			    </li>
				<li><a class="color6" href="contact.html">联系我们</a></li>
			  </ul> 
			</div>
			<div class="user-info">
			     <ul>
						<c:choose>
							<c:when test="${not empty user}">
								<li><a href="#"><img src="${user.avatar}"></a></li>
								 <li class="user-center" style="margin: 0px 10px;">
			  	   					<div class="btn-group">
										<button type="button" class="btn btn-link dropdown-toggle" style="font-size: 1em;" data-toggle="dropdown">${user.name}<span class="caret"></span></button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">个人中心</a></li>
											<li><a href="#">修改密码</a></li>
											<li><a href="#">退出登录</a></li>
										</ul>
									</div>
								</li>
							</c:when>
							<c:otherwise>
								<li><a class="login-btn" href="./login">登录</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
			     
			     
			     <!-- <div class="drop_buttons">
   			       <div class="clearfix"></div>
   			    </div>
   			    <div class="register-info"></div> -->
   			 </div>
   			 <div class="clearfix"> </div>
	</div>
    <div class="header_bootm">
		<div class="col-sm-4 span_1">
		  <div class="logo">
			<a href="index.html"><img src="./static/img/logo.png" alt=""/></a>
		  </div>
		</div>
		<div class="col-sm-8 row_2">
		  <div class="header_bottom_right">
		  	<form action="post">
				<div class="search">
			  		<input name="keyword" type="text" value="输入关键字" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = '输入关键字';}">
			  		<input name="sort" type="hidden" value="createDate:0">
			  		<input name="page" type="hidden" value="1">
			  		<input name="tag" type="hidden" value="${param.tag}">
			  		<input type="submit" value="">
	  			</div>
	  		</form>
	  		<button class="btn-all glyphicon glyphicon-plus" onclick="addPost()">&nbsp;新帖</button>
	  		<div class="clearfix"> </div>
		   </div>
		</div>
		 <div class="clearfix"></div>
	</div>
	
	<script type="text/javascript">
		function addPost(){
			window.location.href="#addPost"
		}
	
	</script>
	
</body>

</html>