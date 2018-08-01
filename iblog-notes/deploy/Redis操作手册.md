### Redis操作手册

官方指导手册：http://www.redis.cn/

### windows下redis 开机自启动

1，在redis的目录下执行（执行后就作为windows服务了）

redis-server --service-install redis.windows.conf
2，安装好后需要手动启动redis

redis-server --service-start
3，停止服务

redis-server --service-start
4，卸载redis服务

redis-server --service-uninstall

### 操作命令
- 选择数据库
```
127.0.0.1:6379[1]> select 3
OK
```
> 每个数据库都有属于自己的空间，不必担心之间的key冲突。


- 清空数据库1
```
127.0.0.1:6379[3]> flushdb
OK
```
> flushdb命令清除数据，只会清除当前的数据库下的数据，不会影响到其他数据库。
- 清空数据库2
```
127.0.0.1:6379[3]> flushall
OK
```
> flushall命令会清除这个实例的数据。在执行这个命令前要格外小心。

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

#### Redis的List
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

#### Redis的Hash
- 可以使用LTRIM把list从左边截取指定长度。
localhost:2>rpush list 1 2 3 4 5
5
localhost:2>ltrim list 0 2
OK
localhost:2>lrange list 0 -1
1) 1
2) 2
3) 3

#### Redis的ZSet数据结构
Redis有序集合和无序集合一样也是string类型元素的集合,且不允许重复的成员。
不同的是**每个元素都会关联一个double类型的分数**。redis正是通过分数来为集合中的成员进行从小到大的排序。
有序集合的成员是唯一的,但分数(score)却可以重复。


> redis的zset原来是解决排行榜的标配，天生就是来做排行榜的



Boolean add(K key, V value, double score);
新增一个有序集合，存在的话为false，不存在的话为true

Long add(K key, Set<TypedTuple<V>> tuples);
新增一个有序集合

Long remove(K key, Object... values);
从有序集合中移除一个或者多个元素

Double incrementScore(K key, V value, double delta);
增加元素的score值，并返回增加后的值

Long rank(K key, Object o);
返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列

Long reverseRank(K key, Object o);
返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列

Set<V> range(K key, long start, long end);
通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列

Set<TypedTuple<V>> rangeWithScores(K key, long start, long end);
通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列

Set<V> rangeByScore(K key, double min, double max);
通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列

Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max);
通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列

Set<V> rangeByScore(K key, double min, double max, long offset, long count);
通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列

Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count);
通过分数返回有序集合指定区间内的成员对象，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列

Set<V> reverseRange(K key, long start, long end);
通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列

Set<TypedTuple<V>> reverseRangeWithScores(K key, long start, long end);
通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递减(从大到小)顺序排列

#### Redis事务
MULTI 、 EXEC 、 DISCARD 和 WATCH 是 Redis 事务相关的命令。事务可以一次执行多个命令， 并且带有以下两个重要的保证：
- 事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
- 事务是一个原子操作：事务中的命令要么全部被执行，要么全部都不执行。

EXEC 命令负责触发并执行事务中的所有命令：
- 如果客户端在使用 MULTI 开启了一个事务之后，却因为断线而没有成功执行 EXEC ，那么事务中的所有命令都不会被执行。
- 另一方面，如果客户端成功在开启事务之后执行 EXEC ，那么事务中的所有命令都会被执行。

##### 用法
MULTI 命令用于开启一个事务，它总是返回 OK 。 MULTI 执行之后， 客户端可以继续向服务器发送任意多条命令， 这些命令不会立即被执行， 而是被放到一个队列中， 当 EXEC命令被调用时， 所有队列中的命令才会被执行。

另一方面， 通过调用 DISCARD ， 客户端可以清空事务队列， 并放弃执行事务。

```
    127.0.0.1:6379[3]> set count 10
    OK
    127.0.0.1:6379[3]> get count
    "10"
    127.0.0.1:6379[3]> multi
    OK
    127.0.0.1:6379[3]> incr count
    QUEUED
    127.0.0.1:6379[3]> incr count
    QUEUED
    127.0.0.1:6379[3]> incr count
    QUEUED
    127.0.0.1:6379[3]> exec
    1) (integer) 11
    2) (integer) 12
    3) (integer) 13
    127.0.0.1:6379[3]>
```
#### Redis持久化
1. RDB持久化方式能够在指定的时间间隔能对你的数据进行快照存储.
2. AOF持久化方式记录每次对服务器写的操作,当服务器重启的时候会重新执行这些命令来恢复原始的数据,AOF命令以redis协议追加保存每次写的操作到文件末尾.Redis还能对AOF文件进行后台重写,使得AOF文件的体积不至于过大.
```
[root@acyou ~]# vi /etc/redis.conf
修改：appendonly yes
[root@acyou ~]# cd /var/lib/redis
[root@acyou redis]# ls
appendonly.aof  backup.db  dump.rdb  root
```

> 参考 [Spring中使用RedisTemplate操作Redis](https://www.cnblogs.com/EasonJim/p/7803067.html#autoid-0-0-0)

