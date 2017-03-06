<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Single</title>
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
<link rel="stylesheet" href="./static/css/etalage.css">
<link href="./static/css/flexslider.css" rel='stylesheet' type='text/css'/>
<script src="./static/js/jquery.etalage.min.js"></script>
<script>
			jQuery(document).ready(function($){

				$('#etalage').etalage({
					thumb_image_width: 300,
					thumb_image_height: 400,
					source_image_width: 900,
					source_image_height: 1200,
					show_hint: true,
					click_callback: function(image_anchor, instance_id){
						alert('Callback example:\nYou clicked on an image with the anchor: "'+image_anchor+'"\n(in Etalage instance: "'+instance_id+'")');
					}
				});
			});
		</script>
<!-- start menu -->
<link href="./static/css/megamenu.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="./static/js/megamenu.js"></script>
<script>$(document).ready(function(){$(".megamenu").megamenu();});</script>

<style type="text/css">
.price-info{
	min-height:80px;
	min-width:400px;
	background-color: rgba(0,0,0,0.04);
}

.price-info div{
	line-height: 40px;
}

.price-info h1,.price-info h4{
	display:inline-block;
}

.shop-address{
	min-width:350px;
	margin: 10px 0px;
}
.shop-address a{
	color: #005ea7;
}

.schedule-info{
	min-width:400px;
	margin: 10px auto;
	line-height: 50px;
}
.btn-reduce{
	border: 1px solid #A9A9A9;
	border-right:none;
	width: 40px;
	height: 40px;
	display:inline-block;
	background-color:rgba(0,0,0,0.04);
	margin-top:9px;
	padding: 0px;
	float: left;
	font-size: 1.5em;
}

.btn-add{
	border: 1px solid #A9A9A9;	
	border-left:none;
	background-color:rgba(0,0,0,0.04);
	width: 40px;
	height: 40px;
	display:inline-block;
	margin-top:9px;
	padding: 0px;
	float: left;
	font-size: 1.5em;
}

.wrap-input input{
	margin-top:9px;
	height: 40px;
	width: 50px;
	padding: 0px;
	float: left;
}

.wrap-input{
	line-height: 40px;
}

.shop-info{
	text-align: right;
	line-height: 30px;
}

.slider_flex{
	border: none;
}

</style>
</head>
<body>
<div class="wrap">	
<div class="container">
      <jsp:include page="head.jsp"></jsp:include>
<div class="content">
  <div class="content_box">
	<div class="men">
	  <div class="single_top">
	       <div class="col-md-9 single_right">
	   	       <div class="grid images_3_of_2">
						<ul id="etalage">
							<c:forEach var="image" items="${images}">
								<li>
									<a href="optionallink.html">
										<img class="etalage_thumb_image" src="${image}" class="img-responsive"/>
										<img class="etalage_source_image" src="${image}" class="img-responsive"title="" />
									</a>
								</li>
							</c:forEach>
						</ul>
						 <div class="clearfix"></div>		
				  </div> 
				  <div class="desc1 span_3_of_2">
				    <h1 style="line-height: 80px;">${book.name}</h1>
				    <h4 style="margin-bottom: 20px;"><span style="color: #005ea7">${book.author}</span> <small style="color: #005ea7;float: right;">${book.press}</small></h4>
				   	<div class="price-info">
				   			<div>
				   				<span>现价：</span><h1 style="color: red;font-weight: 600">&yen;${book.newPrice}</h1>
				   				<small style="color: gray;"><span>[原价：</span><span style="text-decoration: line-through;">&yen;${book.oldPrice}</span><span>]</span></small>
				   			</div>
				   			<div>
				   				<span>累计销量：</span><h1 style="color: #005ea7">${book.sale}</h1>
				   			</div>
				 	</div>
				 	<div class="shop-address">
				 		<span>商家地址：</span><span>广州市大学城外环西路100号广工教学1-2号楼<a href="#">[查看位置]</a></span>
				 	</div>
				 	<div class="schedule-info">
				 		<h4><span>库存：</span><span>${book.remainder}本</span></h4>
				 		<input id="remainder" value="${book.remainder}" type="hidden">
				 		<div>
				 			<div class="wrap-input">
                                    <button disabled="disabled" class="btn-reduce" onclick="reduceF()">-</button>
                                    <input id="buy-num" value="1" onkeyup="">
                                    <button class="btn-add" onclick="addF()">+</button>
                            	 <button class="btn-all" style="margin-left: 20px;" onclick="addCart2(${book.id},${book.remainder},${book.newPrice})">加入购物车</button>
                            </div>
				 		</div>
				 	</div>
				 	
				  </div>
				  <div class="clearfix"></div>	
       </div>
       <div class="col-md-3">
      	<!-- FlexSlider -->
             <!--  <section class="slider_flex"> -->
				  <div class="shop-info">
					 <h3>店铺信息</h3>	
					 <img height="200px;" src="./static/img/m6.jpg">
					 <h4 style="margin-top: 10px;">南亭二手书店</h4>
					 <p><span>联系电话：</span><span>12622743538</span></p>
					 <button class="btn-all">进入店铺</button>
				  </div>
	         <!--  </section> -->
<!-- FlexSlider -->
      </div>
      <div class="clearfix"> </div>
     <h4 class="head_single">相似教材</h4>
             <div class="single_span_3">
	          	 <div class="col-sm-3 span_4">
	          	     <img src="./static/img/m6.jpg" class="img-responsive" alt=""/>
				     <h3>Java基础入门</h3>
				   	 <p><span>李刚 著</span><span>电子工业出版社</span></p>
				   	 <h4 style="color: red;font-weight: 600">￥19.00</h4>
			     </div> 
				 <div class="col-sm-3 span_4">
	          	     <img src="./static/img/m1.jpg" class="img-responsive" alt=""/>
				     <h3>Java基础入门</h3>
				   	 <p><span>李刚 著</span><span>电子工业出版社</span></p>
				   	 <h4 style="color: red;font-weight: 600">￥19.00</h4>
			      </div> 
				  <div class="col-sm-3 span_4">
	          	     <img src="./static/img/pic7.jpg" class="img-responsive" alt=""/>
				     <h3>Java基础入门</h3>
				   	 <p><span>李刚 著</span><span>电子工业出版社</span></p>
				   	 <h4 style="color: red;font-weight: 600">￥19.00</h4>
			      </div> 
				  <div class="col-sm-3 span_4">
	          	     <img src="./static/img/pic8.jpg" class="img-responsive" alt=""/>
				     <h3>Java基础入门</h3>
				   	 <p><span>李刚 著</span><span>电子工业出版社</span></p>
				   	 <h4 style="color: red;font-weight: 600">￥19.00</h4>
			      </div> 
				  <div class="clearfix"></div>
	     </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
   </div>
   </div>
   </div>
   </div>
</div>	

<script type="text/javascript">
function reduceF(){
	var remainder = parseInt($("#remainder").val());
	var buyNum =  parseInt($("#buy-num").val());
	if(buyNum<=1){
		$(".btn-reduce").attr("disabled","disabled");
		return;
	}
	
	$(".btn-add").removeAttr("disabled");
	buyNum = buyNum-1;
	$("#buy-num").val(buyNum);
}

function addF(){
	var remainder = parseInt($("#remainder").val());
	var buyNum =  parseInt($("#buy-num").val());
	if(buyNum>=remainder){
		$(".btn-add").attr("disabled","disabled");
		return;
	}
	$(".btn-reduce").removeAttr("disabled");
	buyNum = buyNum+1;
	$("#buy-num").val(buyNum);
}

function addCart2(id,max,price){
	var num = $("#buy-num").val();
	addCart(id,max,price,num);
	
}


</script>


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