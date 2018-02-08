package cn.acyou.iblog.utilty;

import cn.acyou.iblog.utility.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author youfang
 * @create 2018-02-08 22:42
 */
public class RedisUtilTest {

    @Test
    public void test1(){
        Jedis jedis = RedisUtil.getJedis();
        System.out.println(jedis);
    }
}
