package cn.acyou.iblog.utiltytest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.redis.RedisUtil;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author youfang
 * @date 2018-02-08 22:42
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

    @Test
    public void test3(){
        String ss = "<!-- 内容开始 -->\n" +
                "<div class=\"page-header\">\n" +
                "<blockquote>\n" +
                "<a href=\"#\">Template</a>\n" +
                "</blockquote>\n" +
                "</div>\n" +
                "<p class=\"entry_data\">\n" +
                "作者：<span>youfang</span> 发布时间： <span>2017年06月21日</span> 分类：\n" +
                "<a href=\"#\">Spring</a>\n" +
                "</p>\n" +
                "<div class=\"well well-sm\">\n" +
                "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. nec elit. Aenean lacinia bibendum nulla sed consectetur.</p>\n" +
                "</div>\n" +
                "<!-- 内容结束 -->";
    }
}
