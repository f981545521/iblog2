//文章分类
$('.dropdown-sin-1').dropdown({
  readOnly: true,
  input: '<input type="text" maxLength="20" placeholder="请输入搜索关键字">',
  choice: function() {
    //console.log(arguments, this);
  }
});

var Random = Mock.Random;	    
var json1 = Mock.mock({
  "data|10-50": [{
    name: function() {
      return Random.name(true)
    },
    "id": function() {
      return Random.cword(3);
    },
    "disabled|1-2": true,
    groupName: '分组名',
    "groupId|1-4": 1,
    "selected": false
  }]
});
//文章标签
$('.dropdown-mul-1').dropdown({
  data: json1.data,
  limitCount: 40,
  multipleMode: 'label',
  choice: function() {
    console.log(arguments, this);
  }
});


$(function(){
	
});











