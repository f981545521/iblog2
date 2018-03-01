$(function(){
	$('#email_tdm').click(sendMail);
	
});
/*发送邮箱验证码*/
function sendMail(){
	var email=$('#email_account').val();
	var reg=/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
	if(reg.test(email)){
		console.log(email);
		//调用ajax请求将数据发送到服务器端
		var url="../user/saveActiveCode.do";
		var data={email:email};
		$.post(url,data,function(result){
			console.log(result);
		});
		
		
		
		var sleep = 60, interval = null;
		//interval不为空，说明定时器已经启动
		if (!interval){
			$('#email_tdm').off();//取消事件绑定
			this.style.cursor = "wait";
			interval=setInterval(function(){
				$('#email_tdm').html( "重新发送 (" + sleep-- + ")");
				//定时器结束条件
				if(sleep==0){
					clearInterval (interval);
					interval = null;
					sleep = 20;
					//重置按钮的状态与事件
					$('#email_tdm').html('获取邮箱验证码');
					$('#email_tdm').hover(function(){this.style.cursor='pointer';});
					$('#email_tdm').click(sendMail);
				}
				//console.log(sleep--);
			}, 1000);
		}
		//提示信息
		jeBox.msg('操作成功！邮件已发送至您的邮箱！', {
			icon: 2,
			scrollbar: false,
			boxSize: ["360px", "100px"]
		});
	}else{
		//提示信息
		jeBox.msg('请确认邮箱格式正确！', {
			icon: 3,
			scrollbar: false,
			boxSize: ["350px", "90px"]
		});
		
	}
	
}








/*————————————————————————————————————————————————————————————————————————————*/
/*模态框*/
$('#myModal').modal({
	backdrop : true,
	keyboard : true,
	show : true
});
/* <!--表单验证--> */
$.validator
		.setDefaults({
			debug : true,
			errorElement : 'div',
			// 指明错误放置的位置，默认情况是：error.appendTo(element.parent());即把错误信息放在验证的元素后面。
			errorPlacement : function(error, element) {
				error.addClass("help-block");
				element.parents(".regform").addClass("has-feedback");
				if ($(element).parent().hasClass('datetimepicker')) {
					error.insertAfter(element.parent());
				} else {
					error.insertAfter(element);
				}
				if (!element.next("span")[0]) {
					$(
							"<span class='glyphicon glyphicon-remove form-control-feedback'></span>")
							.insertAfter(element);
				}
			},
			success : function(label, element) {
				if (!$(element).next("span")[0]) {
					$(
							"<span class='glyphicon glyphicon-ok form-control-feedback'></span>")
							.insertAfter($(element));
				}
			},
			highlight : function(element, errorClass, validClass) {
				//console.log($(element).parent());
				$(element).parents(".regform").addClass("has-error")
						.removeClass("has-success");
				$(element).next("span").addClass("glyphicon-remove")
						.removeClass("glyphicon-ok");
			},
			unhighlight : function(element, errorClass, validClass) {
				$(element).parents(".regform").addClass("has-success")
						.removeClass("has-error");
				$(element).next("span").addClass("glyphicon-ok").removeClass(
						"glyphicon-remove");
			}

		});
$().ready(function() {
	$.validator.addMethod("checkEmailsExsited",function(value,element){
		var reg=/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
		return this.optional(element)||(reg.test(value));
	},'请输入正确的邮箱格式！！');
	
	$("#fm").validate({
		rules : {
			email : {
				required : true,
				checkEmailsExsited : true,
				remote:{
					url:'../user/checkMail.do',
					type:'get',
					dataType:'json',
					data:{
						email:function(){return $('#email_account').val()}
					}
				}
			},
			username : {
				required : true,
				minlength : 3
			},
			password : {
				required : true,
				minlength : 5
			},
			confirm_password : {
				required : true,
				minlength : 5,
				equalTo : "#password"
			},
			email_code : {
				required : true,
				remote:{
					url:'../user/checkActiveCode.do',
					type:'post',
					dataType:'json',
					data:{
						email:function(){return $('#email_account').val()},
						email_code:function(){return $('#email_code').val()}
					}
				}
			}
		},
		messages : {
			email : {
				required: "请输入邮箱地址！",
				checkEmailsExsited:"请输入正确的邮箱地址!",
				remote:"该用户已经被注册！"
			},
			username : {
				required : "请输入用户名!",
				minlength : "用户名必需由6个字母组成!"
			},
			password : {
				required : "请输入密码!",
				minlength : "密码必须由5个字符组成!"
			},
			confirm_password : {
				required : "请确认密码!",
				minlength : "密码必须由5个字符组成!",
				equalTo : "两次输入的密码不一致!"
			},
			email_code : {
				required : "邮箱验证码不能为空!",
				remote : "验证码错误,请重新输入！"
			}
		},
	    submitHandler: function (form) {
	    	$(form).ajaxSubmit({
	    		url:"../user/register.do",
	    		type:'post',
	    		dataType:'json',
	    		success:function(result){
	    			//提示信息
	    			if(result.state==0){
	    				jeBox.msg('注册成功！正在跳转到登录页面...', {
	    					icon: 2,
	    					scrollbar: false,
	    					boxSize: ["360px", "90px"],
	    					time:5,
	    					endfun: function(){
	    						location.href='login.html';
	    					}
	    				});
	    			}else{
	    				jeBox.msg('注册失败！', {
	    					icon: 3,
	    					scrollbar: false,
	    					boxSize: ["360px", "90px"]
	    				});
	    			}
	    		},
	    	});
	    }
	});
});
