package cn.acyou.iblog.utility;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis连接池
 *
 * @author youfang
 * @date 2017年7月26日 下午4:05:35
 */
public class RedisUtil {
    //日志记录器
    private static final Logger log = Logger.getLogger(RedisUtil.class);
    //Redis服务器IP
    private static String ADDR;
    //Redis端口号
    private static int PORT;
    //访问密码
    //private static String AUTH;
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW;

    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        log.info("Redis init >>>>>>>>>>>>>>>>>>");
        Properties pro = new Properties();//配置文件对象
        try {
            //从配置文件给私有属性赋值
            pro.load(RedisUtil.class.getClassLoader().getResourceAsStream("redis.properties"));
            ADDR = pro.getProperty("rADDR");
            PORT = Integer.parseInt(pro.getProperty("rPORT"));
            //AUTH=pro.getProperty("rAUTH");
            MAX_ACTIVE = Integer.parseInt(pro.getProperty("rMAX_ACTIVE"));
            MAX_IDLE = Integer.parseInt(pro.getProperty("rMAX_IDLE"));
            MAX_WAIT = Integer.parseInt(pro.getProperty("rMAX_WAIT"));
            TEST_ON_BORROW = Boolean.getBoolean(pro.getProperty("rTEST_ON_BORROW"));

            //配置Redis
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT);

        } catch (IOException e) {
            e.printStackTrace();
            log.error("加载Redis配置文件");
            throw new RuntimeException("加载Redis失败");
        }

    }

    /**
     * 获取Jedis实例
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
    
    
    
