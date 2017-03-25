/**
 * 布尔型数据意义转换
 * @param value 0或者1
 * @param row
 * @param index
 * @returns
 */
function formatterBoolean(value,row,index){
	if(value=='0'){
		return '否';
	}else{
		return '是';
	}
}

/**
 * 状态转换
 * @param value
 * @param row
 * @param index
 * @returns
 */
function formatterStatus(value,row,index){
	if(value=='1'){
		return "可用";
	}else if(value=='2'){
		return '失效';
	}
}

function loadData(){
	menuService.listMemu(function(data){
		$('#dg').datagrid('load',data);
	});
}


