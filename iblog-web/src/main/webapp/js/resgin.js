/*
本代码由素材家园收集并编辑整理;
尊重他人劳动成果;
转载请保留素材家园链接 - www.sucaijiayuan.com
*/
/*-------注册验证-----------*/
$().ready(function() {
	$("#signupForm").validate({
		rules: {
			telphone: {
				required: true,
				rangelength: [11, 11],
				digits: "只能输入整数"
			},
			password: {
				required: true,
				rangelength: [8, 20]
			},
			confirm_password: {
				required: true,
				equalTo: "#password",
				rangelength: [8, 20]
			},
			agent:{
				required: true,
				rangelength: [1, 3]
			}
		},
		messages: {
			telphone: {
				required: "请输入手机号",
				rangelength: jQuery.format("请输入正确的手机号"),
			},
			password: {
				required: "请输入密码",
				rangelength: jQuery.format("密码在8~20个字符之间"),
			},
			confirm_password: {
				required: "请输入确认密码",
				rangelength: jQuery.format("密码在8~20个字符之间"),
				equalTo: "两次输入密码不一致"
			},
			agent: {
				required: "我是agent",
				rangelength: jQuery.format("位数1-3"),
				 
			}
		}
	});
});