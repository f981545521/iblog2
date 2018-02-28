//登录的JS
$(function(){
	//使用JQuery注册事件不加括号是事件绑定，加括号是执行方法
	$('#login').click(loginAction);
	$('#username').blur(checkName);
	$('#password').blur(checkPassword);
});
//验证账号
function checkName(){
	var name=$('#username').val();
///	console.log(name);
	var reg=/^\w{3,10}$/;
	if(!reg.test(name)){//Java中是string.match();
		$('#username').next().html('请输入3~10个字符');
		return false;
	}
	$('#username').next().empty();
	return true;
}
//验证密码
function checkPassword(){
	var name=$('#password').val();
	var reg=/^\w{4,10}$/;
	if(!reg.test(name)){//Java中是string.match();
		$('#password').next().html('请输入4~10个字符');
		return false;
	}
	$('#password').next().empty();
	return true;
}

function loginAction(){
///	console.log('loginAction');
	//获取用户输入的用户名和密码
	var name=$('#username').val();
	var password=$('#password').val();
	
	var n=checkName()+checkPassword();
	if(n!=2){
		return;
	}
	
	//data对象中的两个key名必须和服务器控制器的参数名一样！！！login(name,password);
	var data={'username':name,'password':password};
	$.ajax({
		url:'../main/login.do',
		data:data,
		type:'post',
		dataType:'json',
		success:function(result){
			console.log(result);
			if(result.state==0){
				//登录成功
				var uid=result.data.uid;
				//console.log(uid);
				//跳转到首页
				//登录成功后将userId保存到cookie中；(scripts/cookie-util.js文件中)
				addCookie("uid",uid);
				location.href='home.html';
			}else{
				var message=result.message;
				if(result.state==2){
					$('#username').next().html(message);
				}else if(result.state==3){
					$('#password').next().html(message);
				}else{
					alert(message);
				}

			}
			
		},
		error:function(){
			alert('通讯失败');
		}
	});
}