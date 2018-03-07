问题
什么是Cookie？什么是Session？

为什么要有cookie？

个人理解如下： 服务器需要对用户提供个性化行为 需要鉴别用户 需要获取用户的身份 由于http的无状态 那么必须要有一个手段通知给服务端

这个是笔者多年前在实习的时候对于cookie和session的粗浅认识

https://my.oschina.net/qixiaobo025/blog/134920

因此cookie和session的区别是什么？

session本质上服务端记住你身份的凭据 由于http协议大家知道cookie会被浏览器发送到服务器【哪些情况会发送？】 cookie可以用来记录你的身份信息【客户端】
f6car > 再析Cookie&Session > image2017-11-23 13:5:21.png
当禁用了cookie会怎么样？

那么cookie什么情况会发送到服务器呢？

cookie跨域和我们常说的跨域是同一回事么？浏览器的同源策略？

https://developer.mozilla.org/zh-CN/docs/Web/Security/Same-origin_policy

最初，它的含义是指，A网页设置的 Cookie，B网页不能打开，除非这两个网页"同源"。所谓"同源"指的是"三个相同"。 为什么要有同源策呢？

协议相同
域名相同
端口相同

RFC6265

For historical reasons, cookies contain a number of security and privacy infelicities. For example, a server can indicate that a given cookie is intended for "secure" connections, but the Secure attribute does not provide integrity in the presence of an active network attacker. Similarly, cookies for a given host are shared across all the ports on that host, even though the usual "same-origin policy" used by web browsers isolates content retrieved via different ports.


f6car > 再析Cookie&Session > image2017-11-23 13:15:34.png

domain
name
path
maxAge
如果domain设置成.com 怎么样~

cookie 的安全性？

secure
httpOnly
加密


一、浏览器允许每个域名所包含的 cookie 数：

Microsoft 指出 Internet Explorer 8 增加 cookie 限制为每个域名 50 个，但 IE7 似乎也允许每个域名 50 个 cookie（《Update to Internet Explorer’s Cookie Jar》）。

Firefox 每个域名 cookie 限制为 50 个。

Opera 每个域名 cookie 限制为 30 个。

Safari/WebKit 貌似没有 cookie 限制。但是如果 cookie 很多，则会使 header 大小超过服务器的处理的限制，会导致错误发生。

注：“每个域名 cookie 限制为 20 个”将不再正确！

二、当很多的 cookie 被设置，浏览器如何去响应。除 Safari（可以设置全部 cookie，不管数量多少），有两个方法：

最少最近使用（least recently used (LRU)）的方法：当 Cookie 已达到限额，自动踢除最老的 Cookie ，以使给最新的 Cookie 一些空间。 Internet Explorer 和 Opera 使用此方法。

Firefox 很独特：虽然最后的设置的 Cookie 始终保留，但似乎随机决定哪些 cookie 被保留。似乎没有任何计划（建议：在 Firefox 中不要超过 Cookie 限制）。

三、不同浏览器间 cookie 总大小也不同：

Firefox 和 Safari 允许 cookie 多达 4097 个字节 , 包括名（name）、值（value）和等号。

Opera 允许 cookie 多达 4096 个字节 , 包括：名（name）、值（value）和等号。

Internet Explorer 允许 cookie 多达 4095 个字节 , 包括：名（name）、值（value）和等号。

注：多字节字符计算为两个字节。在所有浏览器中，任何 cookie 大小超过限制都被忽略，且永远不会被设置



单点登录探索