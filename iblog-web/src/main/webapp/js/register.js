//注册页面表单验证
window.onload=function(){
	var fm=document.forms["fm"];
	//验证邮箱
	fm.email_account.onblur=function(){
		var nm=fm.email_account.value;
		var reg=/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
		if(reg.test(nm)){
			$("#email_account").parent().siblings().remove("p");
			$("#email_account").parent().removeClass("has-error").addClass("has-success");
		}else{
			console.log($("#email_account").parent());
			$("#email_account").parent().addClass("has-error");
			if(!($("#email_accounterror").length>0)){
				$("#email_account").parent().after("<p style='color:#A94442' id='email_accounterror'>请输入正确的邮箱地址!</p>")
			}
		}
	}
	//验证用户名
/*	fm.username.onblur=function(){
		var nm=fm.username.value;
		var reg=/^\w{3,15}$/;
		if(reg.test(nm)){
			$("#username").parent().siblings().remove("p");
			$("#username").parent().removeClass("has-error").addClass("has-success");
		}else{
			$("#username").parent().addClass("has-error");
			if(!($("#usernameerror").length>0)){
				$("#username").parent().after("<p style='color:#A94442' id='usernameerror'>请输入正确的用户名!</p>")
			}
		}
	}*/
	//验证密码
	fm.password.onblur=function(){
		var nm=fm.password.value;
		var reg=/^\w{3,15}$/;
		if(reg.test(nm)){
			$("#password").parent().siblings().remove("p");
			$("#password").parent().removeClass("has-error").addClass("has-success");
		}else{
			$("#password").parent().addClass("has-error");
			if(!($("#passworderror").length>0)){
				$("#password").parent().after("<p style='color:#A94442' id='passworderror'>请输入正确的密码!</p>")
			}
		}
	}
	//验证确认密码
	fm.confirm_password.onblur=function(){
		var reg=fm.password.value;
		var nm=fm.confirm_password.value;
		if(reg===nm){
			$("#confirm_password").parent().siblings().remove("p");
			$("#confirm_password").parent().removeClass("has-error").addClass("has-success");
		}else{
			$("#confirm_password").parent().addClass("has-error");
			if(!($("#confirm_passworderror").length>0)){
				$("#confirm_password").parent().after("<p style='color:#A94442' id='confirm_passworderror'>两次输入的密码不一致!</p>")
			}
		}
	}
	

}



