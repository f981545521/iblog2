package cn.acyou.iblog.utiltytest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.redis.RedisUtil;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @author youfang
 * @create 2018-02-08 22:42
 */
public class RedisUtilTest extends BaseTest{

    @Test
    public void testJedis() {
        RedisTemplate redisTemplate = applicationContext.getBean("redisTemplate", RedisTemplate.class);
        System.out.println(redisTemplate);
    }
    @Test
    public void testRedisUtil() {
        RedisUtil redisTemplate = applicationContext.getBean("redisUtil", RedisUtil.class);
        redisTemplate.cacheValue("first","F1");
        System.out.println(redisTemplate.getValue("first"));
        System.out.println(redisTemplate.containsValueKey("first"));
    }
}
