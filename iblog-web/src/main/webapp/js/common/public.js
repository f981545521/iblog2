/* debug配置 */
window.GlobalConfig = {
    //"pageOfficeFeatures": 'left=30px;top=80px;width='+(window.screen.availWidth-100)+'px;height='+(window.screen.availHeight-100)+'px;frame=no'
    //"pageOfficeFeatures": 'left='+(window.screen.width>1280?(document.body.clientWidth - window.screen.width)/2:0)+
    //    'px;top=50px;width='+(window.screen.availWidth)+'px;height='+(window.screen.availHeight)+'px;frame=no'
    "pageOfficeFeatures": pageOfficeFeatures
};

function pageOfficeFeatures(clientWidth) {
    var wwidth = window.screen.availWidth - 40;
    var wleft = "20px";
    //if (window.screen.availHeight>1600){
    //    var offset = (clientWidth - window.screen.availWidth)/2;
    //    wwidth = window.screen.availWidth;
    //    wleft = offset + "px"
    //}
    var wheight = window.screen.availHeight - 60;
    return "left="+wleft+";top=50px;width="+wwidth+"px;height="+wheight+"px;frame=no";

    //return 'left='+(window.screen.width>1600?(clientWidth - window.screen.width)/2:0)+
    //'px;top=50px;width='+(window.screen.availWidth)+'px;height='+(window.screen.availHeight)+'px;frame=no';
}

/**
 * 获取 查询参数
 * @param name 参数名称
 * @returns {*} result
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return null;
}

/**
 * 从路径中获取 资源参数
 * @returns {*} result
 */
function getQueryStringFormPath() {
    var pathName = window.location.pathname;
    var index = window.location.pathname.lastIndexOf("/") + 1;
    var value = pathName.substr(index);
    if (value) {
        return value;
    }
    return null;
}

/**
 * 时间差异算法
 * @param dateStr Data: 2016/1/20 19:59:30
 * @returns {string|*|string}
 */
function getDateDiff(dateStr) {
    var dateTimeStamp = Date.parse(dateStr.replace(/-/gi, "/"));
    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var halfamonth = day * 15;
    var month = day * 30;
    var now = new Date().getTime();
    var diffValue = now - dateTimeStamp;
    if (diffValue < 0) {
        return;
    }
    var monthC = diffValue / month;
    var weekC = diffValue / (7 * day);
    var dayC = diffValue / day;
    var hourC = diffValue / hour;
    var minC = diffValue / minute;
    if (monthC >= 1) {
        result = "" + parseInt(monthC) + "月前";
    }
    else if (weekC >= 1) {
        result = "" + parseInt(weekC) + "周前";
    }
    else if (dayC >= 1) {
        result = "" + parseInt(dayC) + "天前";
    }
    else if (hourC >= 1) {
        result = "" + parseInt(hourC) + "小时前";
    }
    else if (minC >= 1) {
        result = "" + parseInt(minC) + "分钟前";
    } else
        result = "刚刚";
    return result;
}

/** Cookie 操作 */
function addCookie(objName, objValue, objHours) {
    var str = objName + "=" + escape(objValue);
    if (objHours > 0) {//设定过期时间，浏览器关闭时cookie自动消失
        var date = new Date();
        var ms = objHours * 3600 * 1000;
        date.setTime(date.getTime() + ms);
        str += "; expires=" + date.toGMTString();
    }
    document.cookie = str;
}
function setCookie(name, value) {
    var Days = 30; //此 cookie 将被保存 30 天
    var exp = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) return unescape(arr[2]);
    return null;
}
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) {
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}
/** Cookie 操作 */

/**
 * ajax post提交
 * @param ajaxdata 提交数据
 * @param ajaxurl 提交路径
 * @param successcallback 成功回调
 * @param errorcallback 失败回调
 */
function ajaxPost(ajaxdata, ajaxurl, successcallback, errorcallback) {
    $.ajax({
        cache: true,
        type: "post",
        dataType: "json",
        url: ajaxurl,
        data: ajaxdata,
        async: true,
        success: function (data) {
            if ($.isFunction(successcallback)) {
                successcallback.call(this, data);
            }
        },
        error: function (data) {
            if ($.isFunction(errorcallback)) {
                errorcallback.call(this, data);
            }
        }
    });
}
jQuery.ajaxPost = ajaxPost;

/**
 * ajax get
 * @param ajaxdata 提交数据
 * @param ajaxurl 提交路径
 * @param successcallback 成功回调
 * @param errorcallback 失败回调
 */
function ajaxGET(ajaxdata, ajaxurl, successcallback, errorcallback) {
    $.ajax({
        cache: true,
        type: "get",
        dataType: "json",
        url: ajaxurl,
        data: ajaxdata,
        async: true,
        success: function (data) {
            if ($.isFunction(successcallback)) {
                successcallback.call(this, data);
            }
        },
        error: function (data) {
            if ($.isFunction(errorcallback)) {
                errorcallback.call(this, data);
            }
        }
    });
}

/**
 * processData设置为false。因为data值是FormData对象，不需要对数据做处理。
 * <form>标签添加enctype="multipart/form-data"属性。
 * cache设置为false，上传文件不需要缓存。
 * contentType设置为false。因为是由<form>表单构造的FormData对象，且已经声明了属性enctype="multipart/form-data"，所以这里设置为false。
 *
 * ajax FormData post提交
 */
function ajaxFormDataPost(ajaxdata, ajaxurl, successcallback, errorcallback) {
    $.ajax({
        type: "post",
        url: ajaxurl,
        data: ajaxdata,
        async: true,
        dataType: "json",
        contentType: false,
        processData: false,
        success: function (data) {
            if ($.isFunction(successcallback)) {
                successcallback.call(this, data);
            }
        },
        error: function (data) {
            if ($.isFunction(errorcallback)) {
                errorcallback.call(this, data);
            }
        }
    });
}

/**
 * ajax post提交 以contentType: "application/json"方式，后端使用@RequestBody 接收参数
 * @param ajaxdata 提交数据
 * @param ajaxurl 提交路径
 * @param successcallback 成功回调
 * @param errorcallback 失败回调
 */
function ajaxPostJSON(ajaxdata, ajaxurl, successcallback, errorcallback) {
    $.ajax({
        cache: true,
        type: "post",
        contentType: "application/json",//"application/json;charset=UTF-8"
        dataType: "json",
        url: ajaxurl,
        data: ajaxdata,
        async: true,
        success: function (data) {
            if ($.isFunction(successcallback)) {
                successcallback.call(this, data);
            }
        },
        error: function (data) {
            if ($.isFunction(errorcallback)) {
                errorcallback.call(this, data);
            }
        }
    });
}

/**
 * ajax post提交 （同步）以contentType: "application/json"方式，后端使用@RequestBody 接收参数
 * @param ajaxdata 提交数据
 * @param ajaxurl 提交路径
 * @param successcallback 成功回调
 * @param errorcallback 失败回调
 */
function ajaxSyncPostJSON(ajaxdata, ajaxurl, successcallback, errorcallback) {
    $.ajax({
        cache: true,
        type: "post",
        contentType: "application/json",//"application/json;charset=UTF-8"
        dataType: "json",
        url: ajaxurl,
        data: ajaxdata,
        async: false,
        success: function (data) {
            if ($.isFunction(successcallback)) {
                successcallback.call(this, data);
            }
        },
        error: function (data) {
            if ($.isFunction(errorcallback)) {
                errorcallback.call(this, data);
            }
        }
    });
}


/**
 * ajax post提交 （同步）
 * @param ajaxdata 提交数据
 * @param ajaxurl 提交路径
 * @param successcallback 成功回调
 * @param errorcallback 失败回调
 */
function ajaxSyncPost(ajaxdata, ajaxurl, successcallback, errorcallback) {
    $.ajax({
        cache: true,
        type: "post",
        dataType: "json",
        url: ajaxurl,
        data: ajaxdata,
        async: false,
        success: function (data) {
            if ($.isFunction(successcallback)) {
                successcallback.call(this, data);
            }
        },
        error: function (data) {
            if ($.isFunction(errorcallback)) {
                errorcallback.call(this, data);
            }
        }
    });
}

$.ajaxSetup({
    //设置ajax请求结束后的执行动作
    complete: function (XMLHttpRequest, textStatus) {
        // 通过XMLHttpRequest取得响应头，REDIRECT
        var redirect = XMLHttpRequest.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向
        if (redirect == "REDIRECT") {
            var win = window;
            while (win != win.top) {
                win = win.top;
            }
            //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求
            win.location.href = XMLHttpRequest.getResponseHeader('CONTENTPATH');
        }
    }
});

/**
 * 通过正则表达式提取数字/非数字
 * @param str 字符串
 * @param NaN ture非数字，else 数字
 * @returns {XML|void|string|*}
 */
function extractDigits(str, NaN) {
    if (NaN) {
        return str.replace(/[0-9.-]/ig, "");
    }
    return parseInt(str.replace(/[^0-9.-]/ig, ""));
}

/**
 * 金额格式化
 * @param number
 * @param decimals
 * @param dec_point
 * @param thousands_sep
 * @returns {string|*}
 */
function number_format(number, decimals, dec_point, thousands_sep) {
    /*
     * 参数说明：
     * number：要格式化的数字
     * decimals：保留几位小数
     * dec_point：小数点符号
     * thousands_sep：千分位符号
     * */
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.ceil(n * k) / k;
        };

    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    var re = /(-?\d+)(\d{3})/;
    while (re.test(s[0])) {
        s[0] = s[0].replace(re, "$1" + sep + "$2");
    }

    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}

/**
 * 将json转url参数
 * @param json
 * @returns {string}
 */
function convertJsonToParams(json) {
    var params = Object.keys(json).map(function (key) {
        return encodeURIComponent(key) + "=" + encodeURIComponent(json[key]);
    }).join("&");
    return params;
}
/**
 * 将url参数转json
 * @param params
 * @returns {string}
 */
function convertParamsToJson(params) {
    var json = {};
    if (!params) {
        return json;
    }
    params.split("&").forEach(function (item) {
        var keyv = item.split("=");
        if (keyv.length === 2){
            json[keyv[0]] = keyv[1];
        }
    });
    return json;
}

/*
 * 三个参数
 * file：一个是文件(类型是图片格式)，
 * obj：一个是文件压缩的后宽度，宽度越小，字节越小
 * callback：回调
 */
function photoCompress(file, obj, callback) {
    var ready = new FileReader();
    /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
    ready.readAsDataURL(file);
    ready.onload = function () {
        var re = this.result;
        canvasDataURL(re, obj, callback)
    }
}

function canvasDataURL(path, obj, callback) {
    var img = new Image();
    img.src = path;
    img.onload = function () {
        var that = this;
        // 默认按比例压缩
        var w = that.width,
            h = that.height,
            scale = w / h;
        w = obj.width || w;
        h = obj.height || (w / scale);
        var quality = 0.7;  // 默认图片质量为0.7
        //生成canvas
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        // 创建属性节点
        var anw = document.createAttribute("width");
        anw.nodeValue = w;
        var anh = document.createAttribute("height");
        anh.nodeValue = h;
        canvas.setAttributeNode(anw);
        canvas.setAttributeNode(anh);
        ctx.drawImage(that, 0, 0, w, h);
        // 图像质量
        if (obj.quality && obj.quality <= 1 && obj.quality > 0) {
            quality = obj.quality;
        }
        // quality值越小，所绘制出的图像越模糊
        var base64 = canvas.toDataURL('image/jpeg', quality);
        // 回调函数返回base64的值
        callback(base64);
    }
}

/**
 * 将以base64的图片url数据转换为Blob
 * @param urlData
 *            用url方式表示的base64图片数据
 */
function convertBase64UrlToBlob(urlData) {
    var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type: mime});
}

/**
 * 将base64转换为文件
 * @param dataurl
 * @param filename
 * @returns {File}
 */
function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {type: mime});
}

/**
 * 实现将图片转化成base64
 *
 * var file = this.files[0];
 *     convertImgToBase64(file, function(base64Img){
 *     //转化后的base64
 *     $("#ywzrrzhaopian").attr("src", base64Img)
 * });
 *
 */
function convertImgToBase64(file, callback){
    var ready = new FileReader();
    /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
    ready.readAsDataURL(file);
    ready.onload = function () {
        var re = this.result;
        callback(re);
    };
}
/**
 * 时间格式转换 new Date().Format("yyyy-MM-dd hh:mm:ss")
 * @param fmt yyyy-MM-dd hh:mm:ss
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds() //秒
    };
    if (/(y+)/.test(fmt)) { //根据y的长度来截取年
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
    return fmt;
};

/**
 * 两个日期相差天数
 * @param dateStr yyyy-MM-dd hh:mm:ss
 * @returns {string} 天数
 */
Date.prototype.diff = function(dateStr){
    var date = new Date(dateStr);
    var diffDay =  (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
    return diffDay.toFixed(0);
};

/**
 * 判断是否是微信浏览器
 * @returns {boolean}
 */
function isWeiXinBrowser() {
    var ua = navigator.userAgent.toLowerCase();
    return ua.match(/MicroMessenger/i) == "micromessenger";
}

/**
 * 复制到剪切板（废弃：IE11不能使用）
 * @param str
 */
function copyToClipboard(str) {
    var save = function (e) {
        e.clipboardData.setData('text/plain', str);
        if (e){
            e.preventDefault();//阻止默认行为
        }
    };
    document.addEventListener('copy', save);
    document.execCommand("copy");//使文档处于可编辑状态，否则无效
}

/**
 * 复制到剪切板 兼容版
 * @param copyTxt
 */
function copyToClipboardCompatibility(copyTxt) {
    var createInput = document.createElement('input');
    createInput.value = copyTxt;
    document.body.appendChild(createInput);
    createInput.select(); // 选择对象
    document.execCommand("Copy"); // 执行浏览器复制命令
    createInput.className = 'createInput';
    createInput.style.display='none';
}

/**
 * 数字前补充0
 * @param num 目标数字
 * @param length 位数
 * @returns {string}
 * @constructor
 */
function repairZero(num, length) {
    if (num.toString().length >= length) {
        return num;
    }
    return (Array(length).join('0') + num).slice(-length);
}

$(document).on("keyup", ".checkNumber", function () {
    checkNumber(this)
});
$(document).on("keyup", ".checkMoney", function () {
    checkMoney(this)
});
/**
 * 检查Input数字格式（纯数字），example:
 * <input type="text" name="sl" class="form-control checkNumber">
 * @param obj
 */
function checkNumber(obj) {
    obj.value=obj.value.replace(/\D+/g,'')
}
/**
 * 检查Input金额格式（数字+小数点2位），example:
 * <input type="text" name="jz" class="form-control checkMoney">
 * @param obj
 */
function checkMoney(obj){
    if(/^\d+\.?\d{0,2}$/.test(obj.value)){
        obj.value = obj.value;
    }else{
        obj.value = obj.value.substring(0,obj.value.length-1);
    }
}

/**
 * 高德地图ipLocation
 * @param callback
 */
function ipLocation(callback) {
    ajaxGET({}, "https://restapi.amap.com/v3/ip?key=23a99cdc1a4bc9f0055876aa218c2b5f", callback);
}

function getRootPath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    return curWwwPath.substring(0, pos);
}

/**
 *  加载数据字典, 在select上面加input值写入自动回显
 *   <input type="hidden" value="${dossierBase.yt}">
 *   <select class="form-control" id="gzyt" name="yt">
 *
 *   </select>
 * @param code 数据字典编码
 * @param $select select的jQuery对象
 * @param noInitSelect2 默认select2加载，传false使用非select2加载
 */
function loadDataDictionary(code, $select, noInitSelect2) {
    ajaxGET({}, "/ark/plat/sysset/category/list_category_value.do?categoryCode=" + code, function (result) {
        var prestoredVal = $select.prev("input").val();
        $.each(result.children, function (index, item) {
            var opt;
            if (prestoredVal && prestoredVal == item.code){
                opt = "<option value='"+item.code+"' selected>"+item.text+"</option>";
            }else {
                opt = "<option value='"+item.code+"'>"+item.text+"</option>";
            }
            $select.append(opt);
            if (!noInitSelect2){
                $select.select2({
                    placeholder: '请选择'
                });
            }

        })
    });
}


function initAllTimePicker() {
    $(".ark-form-datetime").datetimepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        bootcssVer:3,
        language: 'zh-CN'
    });
}


$(document).on("click", ".copy-to-clipboard", function () {
    var str = $(this).data("value");
    copyToClipboardCompatibility(str);
    alert("复制到剪切板成功！")
});
