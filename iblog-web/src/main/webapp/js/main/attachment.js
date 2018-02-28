$(function(){
	/**上传附件*/
	$('#upload').click(ajaxUpload);
	/**附件列表*/
	//ajaxList();
	//$(document).data('pageId',1);
	
	//分页
	loadData();//加载数据
	loadpage();
});
	

/**
 * http://localhost:8080/iblog/attachment/page.do?uid=1&pageId=5
 * 分页
 */
function exeData(num, type) {
	
	console.log(num);
	//loadData(num);
	//loadpage();
	$.ajax({
		"url":"/iblog/attachment/page.do",
		"type":"post",
		"data":{"uid":getCookie("uid"),"pageId":num},
		"dataType":"json",
		"success":function(result){
			console.log(result.data);
			var data = result.data;
			//获取到了本页的数据；下面展示到页面上
			$('#body-content').empty();
			for(var i=0;i<data.length;i++){
				console.log(data[i].fileSize);
				var temp = attachementTemplate.replace('[aid]',data[i].aid==undefined?'--':data[i].aid);
				temp = temp.replace('[fileName]',data[i].fileName==undefined?'--':data[i].fileName);
				temp = temp.replace('[fileSize]',data[i].fileSize==undefined?'--':data[i].fileSize);
				temp = temp.replace('[description]',data[i].description==undefined?'--':data[i].description);
				temp = temp.replace('[createTime]',data[i].createTime==undefined?'--':data[i].createTime);
				temp = temp.replace('[downloadTime]',data[i].downloadTime==undefined?'--':data[i].downloadTime);
				var td = $(temp);
				$('#body-content').append(td);
				console.log(temp);
			}
			
		}
		
	});
}

function loadData() {
	var totalPages ;
	//获取总共有多少条记录
	var url='../../attachment/pageTotal.do';
	var uid=getCookie("uid");
	var data={uid:uid};
	
	$.ajax({
		"url":url,
		"type":"post",
		"data":data,
		"dataType":"json",
		async:false,
		"success":function(result){
			totalPages = result;
			//显示总数：console.log(totalPages);
		}
		
	});
	
	
	//一共有89条数据
	$("#PageCount").val(totalPages);
	
	//执行分页将第一页显示出来！！
	exeData(1);
}


function loadpage() {
	var myPageCount = parseInt($("#PageCount").val());
	var myPageSize = parseInt($("#PageSize").val());
	//	89%18>0?
	
	var countindex = myPageCount % myPageSize > 0 ? (myPageCount / myPageSize) + 1 : (myPageCount / myPageSize);
	$("#countindex").val(countindex);

	$.jqPaginator('#pagination', {
		totalPages: parseInt($("#countindex").val()),
		visiblePages: parseInt($("#visiblePages").val()),
		currentPage: 1,
		first: '<li class="first"><a href="javascript:;">首页</a></li>',
		prev: '<li class="prev"><a href="javascript:;"><i class="arrow arrow2"></i>上一页</a></li>',
		next: '<li class="next"><a href="javascript:;">下一页<i class="arrow arrow3"></i></a></li>',
		last: '<li class="last"><a href="javascript:;">末页</a></li>',
		page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		onPageChange: function(num, type) {
			if(type == "change") {
				exeData(num, type);
			}
		}
	});
}





function ajaxList(){
	
	var url='../../attachment/pageTotal.do';
	var uid=getCookie("uid");
	var data={uid:uid};
	$.getJSON(url,data,function(result){
		var num=Math.ceil(result/5);//5:每页显示条数
		$('#currentPageId').html(' '+$(document).data('pageId')+' ');
		$('#currentTotal').html(' '+result+' ');
		var nextPage=$('#nextPage');
		for(var i=1;i<=num;i++){
			var li=pageTemplate.replace('[num]',i);
			nextPage.before(li);
		};
		//为第一页增加激活状态
		$('#beforePage').next().addClass('active');
		$('#beforePage').addClass('disabled');
		if(num==1){
			$('#nextPage').addClass('disabled');
		}
	});
}



var pageTemplate = '<li><a href="#">[num]</a></li>';	
	
var attachementTemplate =	
	'<tr>'+
		'<td>[aid]</td>'+
		'<td>[fileName]</td>'+
		'<td>[fileSize]</td>'+
		'<td>[description]</td>'+
		'<td>[createTime]</td>'+
		'<td>[downloadTime]</td>'+
		'<td>'+
			'<a class="tdaction action-download"><span class="glyphicon glyphicon-cloud-download"></span> 下载</a>'+
		'</td>'+
	'</tr>';
	
	
	
	
/**附件上传*/
function ajaxUpload(){
	var file=$('#fileupload')[0].files[0];
	var desc=$('#filedesc').val();
	//创建内存中的表单对象
	var form=new FormData();
	//向表单对象中添加向服务器传输的数据；
	form.append('userfile',file);
	form.append('description',desc);
	$.ajax({
		url:'../../attachment/upload.do',
		data:form,
		type:'post',
		dataType:'json',
		contentType:false,
		processData:false,
		success:function(obj){
			if(obj.state==0){
				$('#uploadResult').html('上传成功');
			}else{
				$('#uploadResult').html('上传失败');
			}
		}
		
	});
}
