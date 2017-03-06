<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<script type="text/javascript" src="./static/js/jquery-1.11.1.min.js"></script>
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
	<div class="grid_1">
		<div class="col-md-3 banner_left">
			<img src="${books[0].mianPicUrl}" class="img-responsive" alt=""/>
			<div class="banner_desc">
				<h1>${books[0].name}</h1>
				<h5>${books[0].newPrice}元
					<small style="text-decoration: line-through;color: red;">${books[0].oldPrice}元</small>
					</h5>
			    <a href="#" class="btn1 btn4 btn-1 btn1-1b" onclick="addCart(${books[0].id},${books[0].remainder},${books[0].newPrice},1)">添加购物车</a>
			</div>
		</div>
		<div class="col-md-9 banner_right">
			 <!-- FlexSlider -->
              <section class="slider">
				  <div class="flexslider">
					<ul class="slides">
						<li><img src="./static/img/banner.jpg" alt=""/></li>
						<li><img src="./static/img/banner1.jpg" alt=""/></li>
					</ul>
				  </div>
	          </section>
              <!-- FlexSlider -->
		</div>
		<div class="clearfix"></div>
	</div>

<div class="content">
  <div class="content_box">
	<ul class="grid_2">
		<c:forEach begin="1" varStatus="status" var="book" items="${books}">
			<a href="single?id=${book.id}">
				<li>
					<img src="${book.mianPicUrl}" class="img-responsive" alt=""/>
					<span class="btn5">${book.newPrice}元</span>
				<p>${book.name}</p>
				</li>
			</a>
			<c:if test="${status.count%5 == 0}">
				<div class="clearfix"> </div>
			</c:if>
		</c:forEach>
	</ul>
	<jsp:include page="footer.jsp"></jsp:include>	
  </div>
</div>

</div>	

</div>

<link href="./static/css/flexslider.css" rel='stylesheet' type='text/css' />
							  <script defer src="./static/js/jquery.flexslider.js"></script>
							  <script type="text/javascript">
								$(function(){
								  SyntaxHighlighter.all();
								});
								$(window).load(function(){
								  $('.flexslider').flexslider({
									animation: "slide",
									start: function(slider){
									  $('body').removeClass('loading');
									}
								  });
								});
							  </script>
</body>
</html>		