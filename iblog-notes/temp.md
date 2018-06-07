127.0.0.1:6379[2]> set counter 100 
OK
127.0.0.1:6379[2]> incr counter //自增
(integer) 101
127.0.0.1:6379[2]> decr counter //自减
(integer) 100
127.0.0.1:6379[2]> exists counter //是否存在key
(integer) 1
127.0.0.1:6379[2]> expire counter 100 //为key设置过期时间
(integer) 1
127.0.0.1:6379[2]> ttl counter //查看key的过期时间
(integer) 58

### Redis的List
127.0.0.1:6379> rpush list A //向List中增加元素
(integer) 1
127.0.0.1:6379> rpush list B
(integer) 2
127.0.0.1:6379> lpush list B
(integer) 3
127.0.0.1:6379> lrange list 0 -1
1) "B"
2) "A"
3) "B"
> 注意:LRANGE 带有两个索引，一定范围的第一个和最后一个元素。这两个索引都可以为负来告知Redis从尾部开始计数，因此-1表示最后一个元素，-2表示list中的倒数第二个元素，以此类推。

- 还有一个重要的命令是pop,它从list中删除元素并同时返回删除的值。可以在左边或右边操

127.0.0.1:6379> rpop list
"B"
127.0.0.1:6379> lrange list 0 -1
1) "B"
2) "A"
127.0.0.1:6379> lpop list
"B"
127.0.0.1:6379> lrange list 0 -1
1) "A"

- 可以使用LTRIM把list从左边截取指定长度。
localhost:2>rpush list 1 2 3 4 5
5
localhost:2>ltrim list 0 2
OK
localhost:2>lrange list 0 -1
1) 1
2) 2
3) 3



