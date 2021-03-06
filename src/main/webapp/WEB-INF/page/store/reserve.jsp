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

<link href="./static/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="./static/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>


<link href="./static/css/flexslider.css" rel='stylesheet' type='text/css' />
<script defer src="./static/js/jquery.flexslider.js"></script>

<style type="text/css">
.order-content{
	margin-top: 20px;
	border: 3px rgba(0,0,0,0.1) solid;
}

.store-info{
	background-color: rgba(0,0,0,0.1);
}

.store-info span{
	line-height: 40px;
	margin: 0px 20px;
}

.book-info{
	background-color: rgba(0,0,0,0.07);
	font-size: 16px;
}

.book-info div span{
	line-height: 50px;
	text-align: center;
}

.book-info p{
	line-height: 40px;
}

.book-info div{
	padding-top:5px;
	border-top:2px rgba(0,0,0,0.08) solid;
	margin-bottom: 10px;
}

.book-info img{
	max-height: 60px;
}

.book-info div span{
	margin:0px 20px;
}


.write-info{
	background-color: rgba(0,0,0,0.04);
	padding-bottom: 5px;
}

.write-info p{
	margin-top: 10px;
}

.more-ignore{
	white-space: nowrap;
	overflow:hidden; 
	text-overflow:ellipsis;
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
			 <h3 align="center">完善订单信息</h3>
			 <c:forEach var="order" items="${orders}">
			     <c:set var="oid" value="${order.id}"></c:set>
			 	 <c:set var="store" value="${mapStore[order.id]}"></c:set>
			 	 <c:set var="bookPOs" value="${mapBookPos[order.id]}"></c:set>
				 <div class="row order-content">
				 	<div class="col-md-12 store-info">
				 		<strong>商家信息</strong>
				 		<span><a href="#">${store.name}</a></span>
				 		<span><i class="glyphicon glyphicon-map-marker" style="color:#15be74"></i>${store.address}[<a href="#" style="color: #005ea7">地图查看</a>]</span>
				 		<span><i class="glyphicon glyphicon-earphone" style="color:#e05b5b"></i>${store.telephone}</span>
				 		<label style="float: right; line-height:40px;">订单号：${order.id}</label>
				 	</div>
				 	<div class="col-md-12 book-info">
				 		<p><strong>书单信息</strong><span style="float: right;">
				 				总计：共${order.count}本  
				 				<fmt:formatNumber pattern="0.00" value="${order.sum}"></fmt:formatNumber>元</span> </p>
				 		<c:forEach var="bookPO" items="${bookPOs}">
				 			<c:set var="bid" value="${bookPO.id}"></c:set>
				 			<c:set var="book" value="${mapBook[bookPO.bid]}"></c:set>
				 			<div class="col-md-12"><a title="${book.name}" href="#" class="col-md-6 more-ignore"><img src="${book.mianPicUrl}">
				 				<span>${book.name}</span></a><span>${bookPO.number}本</span>
				 				<span><fmt:formatNumber pattern="0.00" value="${bookPO.price}"></fmt:formatNumber>元/本</span>
				 				<span>&yen;<fmt:formatNumber pattern="0.00" value="${bookPO.sum}"></fmt:formatNumber></span></div>
				 		</c:forEach>
				 	</div>
				 	
				 	<div class="col-md-12 write-info">
				 		<p><strong>信息填写</strong></p>
				 		<p>确定自提时间段:
				 			<input name="oid" type="hidden" value="${order.id}">
				 			<input name="startTime" size="16" type="text"  readonly class="form_datetime"> - 
				 			<input name="endTime" size="16" type="text"  readonly class="form_datetime">
				 		</p>
				 		<p>
				 			<h5>给商家留言：</h5>
				 			<textarea  name="userMessage" class="col-md-10" rows="2"></textarea>
				 		</p>
				 	</div>
				 	
				 </div>
			 </c:forEach>
			 <div class="col-md-10"></div>
			 <div class="col-md-2">
			 	<button class="btn-all" style="margin:10px 0px;" onclick="sumbitOrder()">提交订单</button>
			 </div>
			</div>
			 
   		</div>
   		<jsp:include page="footer.jsp"></jsp:include>
</div>	

</div>
</div>	

<script type="text/javascript">
	$(".form_datetime").datetimepicker({
		format: 'yyyy-mm-dd hh:ii',
		startDate:new Date(),
		todayBtn:true
	});
	
	function sumbitOrder(){
		var data = new Array();
		var falg = false;
		$(".write-info").each(function(i){
			var oid = $(this).find("input[name='oid']").val();
			var startIN = $(this).find("input[name='startTime']");
			var endIN = $(this).find("input[name='endTime']");
			var startTime = startIN.val();
			var endTime = endIN.val();
			if(startTime=="" || startTime==null || endTime == undefined ){
				startIN.css("border-color","red");
				alert("自提时间段不能为空");
				return false;
			}
			
			if(endTime=="" || endTime==null || endTime == undefined ){
				endIN.css("border-color","red");
				alert("自提时间段不能为空");
				return false;
			}
			var today = new Date();
			var start=new Date(startTime);  
		    var end=new Date(endTime);
		    if(end<start){ 
		    	alert("时间段有误");
				return false;
		    }
			
			var userMessage = $(this).find("textarea[name='userMessage']").val();
			data[i]={
				oid:oid,
				start:startTime,
				end:endTime,
				message:userMessage
			}
			falg = true;
		});
		
		if(!falg) return;
		$.ajax({
			url:'do-confirm-order',
			data:{
				orders:JSON.stringify(data)
			},
			type:'POST',
			success:function(resp){
				var data = eval("("+resp+")");
				if(data.success){
					//清空购书车cookie
					$.removeCookie("cart");
					alert("你已经成功预订，记得到书店取书");
					window.location.href="./";
				}else{
					alert(data.msg);
				}
			},
			error:function(resp){
				alert("服务异常");
			}
		});
		
		
		
		
	}
	

</script>


</body>
</html>		