$(function(){
	console.log(1);
	/**在页面加载的时候发送ajax请求获取天气数据*/
	var url='../common/weather.do';
	$.getJSON(url,function(result){
		showWeather(result);
	});
	
});
/**显示天气*/
function showWeather(result){
	console.log(result);
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

