<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:if test="${not empty specialist}">
	<title>${specialist.name}</title>
</c:if>

<c:if test="${empty specialist}">
	<title>搜索结果</title>
</c:if>

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
<script>$(document).ready(function(){
		$(".megamenu").megamenu();
		var curr = "${pager.currPage}";
		var countPage = "${pager.countPage}";
		var keyword = "${param.keyword}";
		var sort = "${param.sort}";
		if("sale:1" == sort){
			console.log(sort);
			$("#saleBtn").addClass("curr");
		}else if("createTime:1" == sort){
			console.log(sort);
			$("#dateBtn").addClass("curr");
		}else if("newPrice:0" == sort){
			console.log(sort);
			$("#priceBtnDown").addClass("curr");
		}else if("newPrice:1" == sort){
			console.log(sort);
			$("#priceBtnUp").addClass("curr");
		}
		
		if(curr == '1'){
			$("#preBtn").attr("disabled","disabled");
		}
		
		if(curr == countPage){
			$("#nextBtn").attr("disabled","disabled");
		}
		
		
	});


</script>

<style type="text/css">

.search-head{
	padding: 10px 0px;
	background-color: rgba(0,0,0,0.04);
}

.sort-btn-group button{
	background-color: white;
	float: left;
	padding: 5px 10px;
	border:1px rgba(0,0,0,0.04) solid;
	margin: 0px 0.5px;
}

.sort-btn-group button:hover{
	/* color: #e05b5b; */
	border: 1px #e05b5b solid;
}

button.curr{
	background-color: #e05b5b;
	color: white;
}


.pager-head{
}

.pager-head span{
	float: right;
	margin-right: 10px;
	height: 34px;
	line-height:34px;
	text-align: center;
	color: gray;
}

.pager-head button{
	height: 34px;
	float: right;
	padding: 5px 20px;
	border:1px rgba(0,0,0,0.04) solid;
	margin: 0px 0.5px;
	background-color: white;
}

.search-content div{
	min-height: 400px;
	margin-top: 20px;
}

.search-content a{
	list-style: none;
	color: black;
}


.search-content div:hover{
	border:2px rgba(0,0,0,0.1) solid;
}

.search-content div img{
	min-height:100px;
	max-height:200px;
	min-width:100px;
	max-width: 220px;
	margin: 5px auto;
	display: block;
}

.search-content div h5{
	line-height: 20px;
	white-space: nowrap;
	overflow:hidden; 
	text-overflow:ellipsis;
	max-width:200px;
}

.search-content p{
	position: absolute;
	bottom: 15px;
}

</style>



</head>
<body>
<div class="wrap">	
   <div class="container">
   <jsp:include page="head.jsp"></jsp:include>

<div class="content">
  <div class="content_box">
	<div class="col-md-12">
		<c:if test="${not empty specialist}">
			<h4 style="margin-top: 10px;">${specialist.name} &gt; </h4>
		</c:if>
		<div class="row search-head">
			<div class="col-md-6 sort-btn-group">
				<button id="saleBtn" onclick="sort('${param.keyword}','sale:1',1)">
					销量<i class="glyphicon glyphicon-arrow-down" style="font-size: 14px;"></i>
				</button>
				<button id="dateBtn" onclick="sort('${param.keyword}','createTime:1',1)">
					上架时间<i class="glyphicon glyphicon-arrow-down" style="font-size: 14px;"></i>
				</button>
				<button id="priceBtnDown" onclick="sort('${param.keyword}','newPrice:0',1)">
					价格<i class="glyphicon glyphicon-arrow-down" style="font-size: 14px;"></i>
				</button>
				<button id="priceBtnUp" onclick="sort('${param.keyword}','newPrice:1',1)">
					价格<i class="glyphicon glyphicon-arrow-up" style="font-size: 14px;"></i>
				</button>

			</div>
			<div class="col-md-6 pager-head">
				<button id="nextBtn" onclick="sort('${param.keyword}','${param.sort}',${pager.currPage+1})">&gt;</button>
				<button id="preBtn" onclick="sort('${param.keyword}','${param.sort}',${pager.currPage-1})">&lt; </button>
				<span><strong style="color: red;">${pager.currPage}</strong>/${pager.countPage}</span>
				<span>共${pager.count}本教材</span>
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div class="row search-content">
			<c:if test="${empty books}">
				<div class="col-md-12" style="text-align: center;">
					没有查询到结果
				</div>
			</c:if>
			
			<c:forEach items="${books}" var="book">
				<div class="col-md-3" title="${book.name} &#13;${book.author} &#13;${book.press}">
					<a href="single?id=${book.id}" style="text-decoration: none;">
						<img src="${book.mianPicUrl}">
						<h3><span style="color: red;">&yen;${book.newPrice} </span> <small>[<span style="text-decoration: line-through;">&yen;${book.oldPrice}</span>]</small></h3>
						<h5 title="${book.name}">${book.name}</h5>
						<h5><span style="color: #005ea7;">${book.author}</span> | <span title="${book.press}">${book.press}</span></h5>
						<h5>上架时间：<fmt:formatDate pattern="yyyy-MM-dd" value="${book.createTime}"/></h5>
						<h5>销量：<span style="color: #005ea7;">${book.sale}</span> <span style="float: right;color: red;">剩余：${book.remainder}本</span></h5>
					</a>
					<p class="col-md-12">
						<button class="btn-all" onclick="addCart(${book.id},${book.remainder},${book.newPrice},1)">加入购物车</button>
					</p>
				</div>
			</c:forEach>
		</div>
	</div>
	
	
	
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
							  
							  
<script type="text/javascript">
	function sort(keyword,sort,page){
		window.location.href="search?spid=${param.spid}&keyword="+keyword+"&sort="+sort+"&page="+page;
	}
	


</script>
</body>
</html>		