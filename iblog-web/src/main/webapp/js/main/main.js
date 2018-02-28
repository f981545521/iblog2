/** 菜单JS */
$(function() {
	$("#page-wrapper").css("min-height", $(window).height() + "px");

});
$(window).resize(function() {
	$("#page-wrapper").css("min-height", $(window).height() + "px");
});
// 菜单部分
$(document).ready(
		function() {
			$('.inactive').click(
					function() {
						// 判断菜单能否展开
						if ($(this).children('span').hasClass(
								'glyphicon-menu-left')) {
							$(this).children('span').removeClass(
									'glyphicon glyphicon-menu-left');
							$(this).children('span').addClass(
									'glyphicon glyphicon-menu-down');
							$(this).siblings('ul').slideDown(100)
									.children('li');
							// 将其他菜单折叠
							if ($(this).parent('li').siblings('li').children(
									'a').children('span').hasClass(
									'glyphicon-menu-down')) {
								$(this).parent('li').siblings('li').children(
										'a').children('span').removeClass(
										'glyphicon-menu-down');
								$(this).parent('li').siblings('li').children(
										'a').children('span').addClass(
										'glyphicon-menu-left');
								$(this).parent('li').siblings('li').children(
										'ul').slideUp(100);
							}
						} else {
							// 不能展开就折叠咯
							$(this).children('span').removeClass(
									'glyphicon glyphicon-menu-down');
							$(this).children('span').addClass(
									'glyphicon glyphicon-menu-left');
							$(this).siblings('ul').slideUp(100);
						}
					})
		});

/** cookie-util.js */

// 获得coolie 的值
function cookie(name) {
	var cookieArray = document.cookie.split("; "); // 得到分割的cookie名值对
	for (var i = 0; i < cookieArray.length; i++) {
		var arr = cookieArray[i].split("="); // 将名和值分开
		if (arr[0] == name)
			return unescape(arr[1]); // 如果是指定的cookie，则返回它的值
	}
	return "";
}
/*
 * function delCookie(name)//删除cookie { document.cookie = name+"=;expires="+(new
 * Date(0)).toGMTString(); }
 */

function getCookie(objName) {// 获取指定名称的cookie的值
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName)
			return unescape(temp[1]);
	}
}

function addCookie(objName, objValue, objHours) { // 添加cookie
	var str = objName + "=" + escape(objValue);
	if (objHours > 0) { // 为时不设定过期时间，浏览器关闭时cookie自动消失
		var date = new Date();
		var ms = objHours * 3600 * 1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
}

function setCookie(name, value)// 两个参数，一个是cookie的名子，一个是值
{
	var Days = 30; // 此 cookie 将被保存 30 天
	var exp = new Date(); // new Date("December 31, 9998");
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}

function getCookie(name)// 取cookies函数
{
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
}

function delCookie(name)// 删除cookie
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null) {
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
	}
}

/**
 * 时间格式化：在Date原型添加方法
 * 
 * 使用方法：
 * var time1 = new Date().format("yyyy-MM-dd hh:mm:ss");
 * console.log(time1);
 */
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}





