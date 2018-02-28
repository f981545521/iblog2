# KindEditor常用API

##  1、加载编辑器：

### 方式一：
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="content"]', {
		allowFileManager : true
	});
	
### 方式二：
KindEditor.ready(function(K) {
	window.editor=K.create('#Keditor');
});

## 2、获取输入的内容
- editor.html();（含有标签）
- editor.text();（文本内容，不含有标签）

## 3、判断文本域中是否为空
- editor.isEmpty();

## 4、获取文本域中选中的内容	
- editor.selectedHtml()	

## 5、为文本域设置内容
- editor.html('<h3>Hello KindEditor</h3>');（html标签会正确翻译）
- editor.text('<h3>Hello KindEditor</h3>');（html内容会认为是内容的一部分）

## 6、在当前标签位置插入HTML内容	
- editor.insertHtml('<strong>插入HTML</strong>');

## 7、在文本域中追加内容（追加到最后）
- editor.appendHtml('<strong>添加HTML</strong>');

## 8、清空文本域
- editor.html('');

## 9、统计字数
- editor.count();（字数统计包含HTML代码。）
- editor.count('text');（字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字。）

## 10、
- ...
