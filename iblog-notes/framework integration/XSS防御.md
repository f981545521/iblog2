### XSS

#### 1. 通过Mybatis

XssStringTypeHandler.java

mybatis-config.xml
```
    <typeHandlers>
        <typeHandler handler="cn.acyou.iblog.web.XssStringTypeHandler"/>
    </typeHandlers>
```
#### 2. 通过Filter

### CSRF

#### 1. 验证HTTP Referer字段
记录来源地址
#### 2. 请求中添加Token
Token，就是令牌，最大的特点就是随机性，不可预测。一般黑客或软件无法猜测出来。

Token一般用在两个地方:

1. 防止表单重复提交

如果应用于"防止表单重复提交"，服务器端第一次验证相同过后，会将session中的Token值更新下，若用户重复提交，第二次的验证判断将失败，因为用户提交的表单中的Token没变，但服务器端session中Token已经改变了。

2. anti csrf攻击（跨站点请求伪造）。

如果应用于"anti csrf攻击"，则服务器端会对Token值进行验证，判断是否和session中的Token值相等，若相等，则可以证明请求有效，不是伪造的。

> 两者在原理上都是通过session token来实现的。当客户端请求页面时，服务器会生成一个随机数Token，并且将Token放置到session当中，然后将Token发给客户端（一般通过构造hidden表单）。下次客户端提交请求时，Token会随着表单一起提交到服务器端。
#### 3. 在HTTP头中自定义属性并验证
