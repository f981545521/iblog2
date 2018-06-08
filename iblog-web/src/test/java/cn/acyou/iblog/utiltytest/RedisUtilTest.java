package cn.acyou.iblog.utiltytest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.redis.RedisUtil;
import com.google.common.collect.Collections2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * @author youfang
 * @date 2018-02-08 22:42
 */
public class RedisUtilTest extends BaseTest{

    @Autowired
    private RedisUtil redisUtil;

    private final String FIX = "CONTENT:AUDIO:INFO";

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

    @Test
    public void test3(){
        //redisUtil.hset(FIX, "1", "{'name':'张飞','age':23}");
        Object o = redisUtil.hget(FIX, "1");

        System.out.println(o.toString());
    }

    @Test
    public void test4(){
        for (int i = 1; i < 20; i++){
            redisUtil.zadd(FIX + ":youfang", "youfang - " + String.valueOf(i), Double.valueOf(String.valueOf(i)));
        }
    }

    @Test
    public void etst2(){
        Set<String> set = redisUtil.zreverseRangeByScore(FIX + ":youfang", 3L, 8L);
        for (String s: set){
            System.out.println(s);
        }
    }
}
