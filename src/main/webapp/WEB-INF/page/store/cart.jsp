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

<style type="text/css">

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

#buy-num{
	margin-top:9px;
	height: 40px;
	width: 50px;
	padding: 0px;
	float: left;
}

tfoot span{
	color: red;
	font-weight: 700;
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
   			<c:choose>
   				<c:when test="${empty books}">
   					<div class="clo-md-12" style="min-height: 200px">
   						<h3 style="margin-top:100px; text-align: center;">空的购书车T_T <a href="./" style="color: red;">去挑几本书啦</a></h3>
   					</div>
   				</c:when>
   				<c:otherwise>
		   			<table class="table" style="margin-top: 20px;">
						<caption>我的购书车<small>（只保留当天）</small></caption>
						<thead style="background: rgba(0,0,0,0.04);">
							<tr>
								<th><input name="checkAll" type="checkbox" checked="checked" onclick="checkAll()"><small>全选</small></th>
								<th>教材</th>
								<th>单价</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="book" varStatus="status" items="${books}">
								<c:set var="bookPO" value="${cart.books[status.index]}"></c:set>
								<tr id="tr-${book.id}">
									<td>
										<c:choose>
											<c:when test="${bookPO.check}">
												<input name="checkbox" type="checkbox" checked="checked" onclick="checkBox('tr-${book.id}',${book.id})">						
											</c:when>
											<c:otherwise>
												<input name="checkbox" type="checkbox" onclick="checkBox('tr-${book.id}',${book.id})">						
											</c:otherwise>
										</c:choose>
									<td title="${book.name}" style="max-width: 400px;"><a href=""><img src="${book.mianPicUrl}" style="max-height: 100px;">
										<div style="display:inline-block; white-space: nowrap;overflow:hidden; text-overflow:ellipsis;max-width:250px;">${book.name}</div></a></td>
									<td>&yen;<fmt:formatNumber pattern="0.00" value="${bookPO.price}"></fmt:formatNumber> <input type="hidden" value="${book.newPrice}"></td>
									<td>
										<button disabled="disabled" class="btn-reduce" onclick="reduceF('tr-${book.id}',${book.id})">-</button>
										<input id="buy-num" value="${bookPO.num}" onkeyup="">
										<button class="btn-add" onclick="addF(${book.remainder},'tr-${book.id}',${book.id})">+</button>
									</td>
									<td>&yen; <span><fmt:formatNumber pattern="0.00" value="${bookPO.num*bookPO.price}"></fmt:formatNumber></span></td>
									<td><a href="#" onclick="removeCart('tr-${book.id}',${book.id})">移除</a></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr><td></td><td></td><td></td>						
								<td>已选中<span>${cart.count}</span>本教材</td>
								<td>共<span>&yen;<fmt:formatNumber pattern="0.00" value="${cart.sum}"></fmt:formatNumber></span></td>
								<td><button class="btn-all" onclick="reserveBook()">确认预定</button></td>
							</tr>
							<tr><td></td>
								<td><button class="btn-all" onclick="clearCart()">清空购书车</button></td>
							</tr>
						</tfoot>
					</table>
   				</c:otherwise>
   			</c:choose>
   		</div>
	
   		<jsp:include page="footer.jsp"></jsp:include>
</div>	

</div>
</div>	

</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			</div>
			<div class="modal-body">
				是否将该教材移出购书车？
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">保留
				</button>
				<button id="removeBtn" type="button" class="btn btn-primary">
					决定移除
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>
	
<div class="modal fade" id="clearCartModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
			</div>
			<div class="modal-body">
				是否清空购书车？
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">再想想吧
				</button>
				<button id="clearCartBtn" type="button" class="btn btn-primary">
					我要清空
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>

<link href="./static/css/flexslider.css" rel='stylesheet' type='text/css' />
<script defer src="./static/js/jquery.flexslider.js"></script>
							
							  
<script type="text/javascript">
function reduceF(itemID,bid){
	var buyNum =  parseInt($("#"+itemID+" #buy-num").val());
	var price = parseInt($("#"+itemID+" input[type='hidden']").val());
	if(buyNum<=1){
		$("#"+itemID+" .btn-reduce").attr("disabled","disabled");
		return;
	}
	
	var cart = $.cookie("cart");
	var i=0;
	for(;i<cart.books.length;i++){
		if(cart.books[i].id == bid){
			break;
		}
	}
	cart.books[i].num = cart.books[i].num-1;
	var sprice = cart.books[i].num*cart.books[i].price;
	if(cart.books[i].check){
		cart.count = cart.count-1;
		cart.sum = cart.sum-price;
	}
	$("#"+itemID+" .btn-add").removeAttr("disabled");
	buyNum = buyNum-1;
	$("#"+itemID+ " #buy-num").val(cart.books[i].num);
	$("#"+itemID+" td span").text(sprice.toFixed(2));
	if($("#"+itemID+" input[name='checkbox']").is(':checked')){
		$("tfoot span:first").text(cart.count);
		$("tfoot span:last").text("&yen;"+cart.sum.toFixed(2));
	}
	$.cookie("cart",cart);
}

function addF(remainder,itemID,bid){
	var price = parseInt($("#"+itemID+" input[type='hidden']").val());
	var buyNum =  parseInt($("#"+itemID+" #buy-num").val());
	if(buyNum>=remainder){
		$("#"+itemID+" .btn-add").attr("disabled","disabled");
		return;
	}
	
	var cart = $.cookie("cart");
	var i=0;
	for(;i<cart.books.length;i++){
		if(cart.books[i].id == bid){
			break;
		}
	}
	cart.books[i].num = cart.books[i].num+1;
	var sprice = cart.books[i].num*cart.books[i].price;
	if(cart.books[i].check){
		cart.count = cart.count+1;
		cart.sum = cart.sum+price;
	}
	$("#"+itemID+" .btn-reduce").removeAttr("disabled");
	buyNum = buyNum+1;
	$("#"+itemID+ " #buy-num").val(cart.books[i].num);
	$("#"+itemID+" td span").text(sprice.toFixed(2));
	if($("#"+itemID+" input[name='checkbox']").is(':checked')){
		$("tfoot span:first").text(cart.count);
		$("tfoot span:last").text("&yen;"+cart.sum.toFixed(2));
	}
	$.cookie("cart",cart);
}

function checkAll(){
	var cart = $.cookie("cart");
	if($("input[name='checkAll']").is(':checked')){
		for(var i =0;i<cart.books.length;i++){
			if(!cart.books[i].check){
				cart.count=cart.count+cart.books[i].num;
				cart.sum=cart.sum+cart.books[i].num*cart.books[i].price;
			}
		}
		$("input[name='checkbox']").prop('checked',true);
	}else{
		cart.count=0;
		cart.sum=0.0;
		for(var i =0;i<cart.books.length;i++){
			cart.books[i].check=false;
		}
		$("input[name='checkbox']").prop("checked",false); 
	}
	$("tfoot span:first").text(cart.count);
	$("tfoot span:last").text("&yen;"+cart.sum.toFixed(2));
	console.log($.cookie("cart")); 
}

function checkBox(itemID,bid){
	
	var cart = $.cookie("cart");
	var i = 0
	for(;i<cart.books.length;i++){
		if(cart.books[i].id == bid){
			break;
		}
	}
	
	if($("#"+itemID+" input[name='checkbox']").is(':checked')){
		cart.count = cart.count+cart.books[i].num;
		cart.sum = cart.sum+cart.books[i].num*cart.books[i].price;
		cart.books[i].check=true;
		var chsub = $("input[name='checkbox']").length;  
	    var checkedsub = $("input[name='checkbox']:checked").length; //获取选中的subcheck的个数  
	    if (checkedsub == chsub) {  
	    	$("input[name='checkAll']").prop("checked",true);
	    } 
	}else{
		cart.count = cart.count-cart.books[i].num;
		cart.sum = cart.sum-cart.books[i].num*cart.books[i].price;
		cart.books[i].check=false;
		$("input[name='checkAll']").prop("checked",false);
	}
	
	$("tfoot span:first").text(cart.count);
	$("tfoot span:last").text("&yen;"+cart.sum.toFixed(2));
	$.cookie("cart",cart);
}


function removeCart(itemId,bid){
	$('#myModal').modal('show');
	
	$("#removeBtn").click(function(){
		var cart = $.cookie("cart");
		var i = 0;
		for(;i<cart.books.length;i++){
			if(cart.books[i].id == bid){
				break;
			}
		}
		cart.count = cart.count-cart.books[i].num;
		cart.sum = cart.sum-cart.books[i].num*cart.books[i].price;
		for(;i<cart.books.length;i++){
			cart.books[i]=cart.books[i+1];
		}
		cart.books.pop();
		$.cookie("cart",cart);
		window.location.href="./cart";
	});
}

function clearCart(){
	$("#clearCartModal").modal('show');
	
	$("#clearCartBtn").click(function(){
		$.removeCookie("cart");
		window.location.href="./cart";
	});
}

function reserveBook(){
	$.ajax({
		url:'reserve-book',
		success:function(resp){
			var data = eval('('+resp+')');
			if(data.success){
				window.location.href="./confirm-order?oids="+data.msg;
			}else{
				alert(data.msg);
				window.location.href="./login"
			}
		},
		error:function(resp){
			console.log(resp);
			alert("服务出错");
		}
	});
}




</script>
							  
</body>
</html>		