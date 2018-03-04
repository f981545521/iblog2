$(function(){
	//console.log(1);
	/**在页面加载的时候发送ajax请求获取天气数据*/
	var url='../common/weather.do';
	$.getJSON(url,function(result){
		showWeather(result);
	});
    var initUrl = '../main/initPage.do';
    $.getJSON(initUrl,function (result) {
        showMainPage(result);
    })
	
});
/**
 * 显示日历
 */
//获取当前年月日,空位补0
function getNowFormatDate() {
    var day = new Date();
    var Year = 0;
    var Month = 0;
    var Day = 0;
    var CurrentDate = "";
    Year = day.getFullYear();
    Month = day.getMonth() + 1;
    Day = day.getDate();
    CurrentDate += Year + "-";
    if(Month >= 10) {
        CurrentDate += Month + "-";
    } else {
        CurrentDate += "0" + Month + "-";
    }
    if(Day >= 10) {
        CurrentDate += Day;
    } else {
        CurrentDate += "0" + Day;
    }

    return CurrentDate;
}
$(function() {
    $("#calendarfix").jeDate({
        fixedCell: "calendarfixdiv",
        isinitVal: true,
        festival: true,
        sminDate: '2016-06-16',
        maxDate: '2025-06-16',
        format: 'YYYY年MM月DD日 hh:mm:ss',
        marks: [getNowFormatDate()],
        zIndex: 3000
    });
});
/**显示天气*/
function showWeather(result){
	//console.log(result);
	//清空之前存在的数据
	$('.weather').empty();
	var weathers=result.data;
	//判断是否有返回数据返回
	if(result.state==0){
		$('.weather').append('<li>城市：'+weathers[0].province+''+weathers[0].city+'</li>');
		$('.weather').append('<li>天气：'+weathers[0].weather+' <img src="../img/weather/'+weathers[0].image+'"></li>');
		$('.weather').append('<li>温度：'+weathers[0].temperature+'</li>');
		$('.weather').append('<li>风向：'+weathers[0].wind+'</li>');
		$('.weather').append('<li>'+weathers[0].todayScene+'</li>');
		$('.weather').append('<li>更新时间：'+weathers[0].updateTime+'</li>');
	}else{
		$('.weather').append('<li>未获取到天气！</li>');
	}
}
//<li>城市：江苏南京</li>
//<li>天气：多云 <img src="../img/weather/0.gif"></li>
//<li>温度：28℃/38℃</li>
//<li>风向：西南风3-4级</li>
//<li>今日天气实况：气温：33℃；风向/风力：南风 3级；湿度：60%；紫外线强度：中等。空气质量：良。</li>
//<li>更新时间：2017-7-18 11:33:40</li>
function showMainPage(result) {
    //文章分类
    var sortList = result.data.sortList;
    for (var i = 0; i < sortList.length; i++) {
        var li = '<li class="list-group-item"><span class="badge">' + sortList[i].totalCount + '</span>' + sortList[i].sortName + '</li>';
        li = $(li);
        $('#sort').append(li);
        li.data('id',sortList[i].id);
    }
    //文章归档
    var pigeonholeList = result.data.pigeonholeList;
    for (var i = 0; i < pigeonholeList.length; i++) {
        $('#archives').append('<li class="list-group-item"><span class="badge">' + pigeonholeList[i].countTotal + '</span>' + pigeonholeList[i].pigeonhole + '</li>');
    }
    //文章内容
    var blogList = result.data.blogVoList;
    var content = $('.content');
    for (var i = 0; i < blogList.length; i++) {
        //获取数据
        var title = blogList[i].title;
        var author = blogList[i].author;
        var sortName = blogList[i].sortName;
        var excerpt = blogList[i].excerpt;
        var creationtime = new Date(blogList[i].creationtime).format("yyyy-MM-dd hh:mm:ss");
        var contentBody = contentTemplate.replace('[title]', title);
        contentBody = contentBody.replace('[author]', author);
        contentBody = contentBody.replace('[sortName]', sortName);
        contentBody = contentBody.replace('[excerpt]', excerpt);
        contentBody = contentBody.replace('[creationtime]', creationtime);
        contentBody = $(contentBody);
        content.append(contentBody);
    }
}

var contentTemplate =
    "<!-- 内容开始 -->" +
    "<div class='page-header'>" +
        "<blockquote>" +
        "<a href='#'>[title]</a>" +
        "</blockquote>" +
    "</div>" +
        "<p class='entry_data'>" +
        "作者：<span>[author]</span> | 发布时间： <span>[creationtime]</span> | 分类：" +
        "<a href='#'>[sortName]</a>" +
        "</p>" +
    "<div class='well well-sm'>" +
        "<p>[excerpt]</p>" +
    "</div>" +
    "<!-- 内容结束 -->";
