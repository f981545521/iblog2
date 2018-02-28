
$(function(){
	/**
	 * 加载页面时显示所有信息
	 */
	//获取cookie中存储的用户ID
	var uid=getCookie("uid");
	//console.log(uid);
	//初始化中获取用户名下的分类列表
	var url='/iblog/sort/list.do';
	var data={uid:uid};
	$.getJSON(url,data,function(result){
		var data=result.data;
		showSorts(data);
	});

	//删除
	$('tbody').on('click','.action-delete',delSortWithPrompt);
	//去编辑页
	$('tbody').on('click','.action-edit',editSort);

	
	
});



/**
 * 去编辑页
 */
var k;//不得已定义一个全局的变量；方便编辑保存后页面的修改！
function editSort(){
	k = $(this).parent().siblings();
	
	var sid = $(this).parent().siblings().eq(0).html();
	var sortName = $(this).parent().siblings().eq(1).html();
	var description = $(this).parent().siblings().eq(2).html();
	var temp = editSortTemplate.replace('[sortName]',sortName);
	temp = temp.replace('[description]',description);
	temp = temp.replace('[sid]',sid);
	
    var index = jeBox.open({
        title:"编辑",
        maxBtn:true,
        padding:"10px 20px",
        boxSize:["50%","60%"],
        content: [temp],
        maskLock : true
    });
    
    
	//打开编辑页的时候绑定事件好了！！
	//编辑页  - 取消
	$('#editPage .btn-default').click(cancleEdit);
	//编辑页  - 保存
	$('#editPage .btn-primary').click(saveEdit);
    
}

/**
 * 保存修改
 * http://localhost:8080/iblog/sort/updateSort.do?sid=44&sortName=asd&description=asd
 */
function saveEdit(){
	//获取输入框中的值
	var sid = $('#editPage input').eq(0).val();
	var sortName = $('#editPage input').eq(1).val();
	var description = $('#editPage input').eq(2).val();
	//利用ajax发送给服务端
	$.getJSON("/iblog/sort/updateSort.do", {
		sid:sid,sortName:sortName,description:description
	},function(result){
		console.log(result.data);
		console.log(k);
		if(result.state == 0){
			jeBox.msg('修改成功！', {icon: 2,time:1,boxSize: ["250px", "90px"]});
		}
		//不刷新加载页面
		k.eq(1).html(result.data.sortName);
		k.eq(2).html(result.data.description);
		
	});
	
}

function cancleEdit(){
	//关闭所有层
	jeBox.closeAll();
}





/**
 * 删除分类
 * 访问：http://localhost:8080/iblog/sort/delSort.do?sid=23
 */
function delSortWithPrompt(){
	var k = $(this);
	var index = jeBox.open({
        cell:"jbx",// 独立ID,用于控制弹层唯一标识
        title:["提示",{color:"#ff0000"}],
        padding:"5px 0px",
        boxSize: ["250px", "150px"], // 参数一：弹层宽度，参数二： 弹层高度
        icon: 4, // 图标,信息框和加载层的私有参数
        closeBtn: true, // 标题上的关闭按钮；默认：true
        content:'确认删除吗？',
        maskLock : true ,
        btnAlign:"right",//按钮的位置
        button:[{
            name: '确认',
                callback: function(index){
                    //jeBox.msg('删除成功', {icon: 2,time:1});
                    jeBox.close(index);
                    //调用删除函数，如何传入this呢？
                    console.log(k);
                    delSort(k);
                }
            },
            {
                name: '取消'
            }
        ]
   });
}

/**
 * 删除分类
 * 访问：http://localhost:8080/iblog/sort/delSort.do?sid=23
 */
function delSort(t){
/*	var k = $(this);
	console.log(t);
	console.log(k);*/
	var sid = t.parent().siblings().first().html();
	//页面上同时不刷新删除
	t.parent().parent().remove();
	//处理：
	$.post("/iblog/sort/delSort.do",{sid:sid}, function(data){
		//回调函数：
		if(data.state == 0){
			jeBox.msg('删除成功', {icon: 2,time:1,boxSize: ["250px", "90px"]});
			//此处偷懒刷新网页
			window.location.reload(true);
		}
	});
}



/**
 * 展示分类
 * @param data	数据
 */
function showSorts(data){
	//console.log(data[0].sortname);
	//找到表格位置
	var tbody=$('.panel-body tbody');
	for(var i=0;i<data.length;i++){
		//获取数据
		var sid=data[i].sid;
		var sortname=data[i].sortname;
		var description=data[i].description;
		var createtime=new Date(data[i].createtime).format("yyyy-MM-dd hh:mm:ss");
		//console.log(sortname);
		var td=sortTemplate.replace('[sid]',sid);
		td=td.replace('[sortname]',sortname);
		td=td.replace('[description]',description);
		td=td.replace('[createtime]',createtime);
		
		td=$(td);
		tbody.append(td);
	}
}

/**
 * 分类的编辑模板
 */
var editSortTemplate =
		'<form id="editPage">'+
		'<div class="form-group">'+
			'<label for="sid">ID</label>'+
			'<input type="text" class="form-control" id="sid" value="[sid]" disabled>'+
		'</div>'+
		'<div class="form-group">'+
			'<label for="sortName">分类名称</label>'+
			'<input type="text" class="form-control" id="sortName" value="[sortName]">'+
		'</div>'+
		'<div class="form-group">'+
			'<label for="description">描述</label>'+
			'<input type="text" class="form-control" id="description" value="[description]">'+
		'</div>'+
			'<button type="button" class="btn btn-primary">保存</button>'+
			'<button type="button" class="btn btn-default">取消</button>'+
		'</form>';

/**
 * 分类的模板
 */
var sortTemplate='<tr>'+
	'<td>[sid]</td>'+
	'<td>[sortname]</td>'+
	'<td>[description]</td>'+
	'<td>[createtime]</td>'+
	'<td>'+
		'<a class="tdaction action-edit"><span class="glyphicon glyphicon-edit"></span> 编辑</a>'+
		'<a class="tdaction action-delete"><span class="glyphicon glyphicon-trash"></span> 删除</a>'+
	'</td>'+
'</tr>';