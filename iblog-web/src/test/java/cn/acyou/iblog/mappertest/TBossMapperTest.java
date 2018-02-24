package cn.acyou.iblog.mappertest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.mappers.BossMapper;
import cn.acyou.iblog.model.test.TBoss;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.List;

/**
 * @author youfang
 * @date 2018-02-09 19:56
 **/
public class TBossMapperTest extends BaseTest{

    private BossMapper tBossMapper;

    @Before
    public void initTest() {
        tBossMapper = applicationContext.getBean("bossMapper", BossMapper.class);
    }

    @Test
    public void test1(){
        tBossMapper.getAllTBoss();
    }

    /**
     * 既然有了 SqlSessionFactory ，顾名思义，我们就可以从中获得 SqlSession 的实例了。
     * SqlSession 完全包含了面向数据库执行 SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。
     */
    @Test
    public void test2(){
        SqlSessionFactory session = applicationContext.getBean("sqlSessionFactory",SqlSessionFactory.class);
        SqlSession sqlSession = session.openSession();
        BossMapper tm = sqlSession.getMapper(BossMapper.class);
        List<TBoss> tBossList = tm.getAllTBoss();
        System.out.println(tBossList);
        System.out.println(session);
        sqlSession.close();
    }
    @Test
    public void test3(){
        MapperScannerConfigurer scannerConfigurer = applicationContext.getBean("mapperScannerConfigurer",MapperScannerConfigurer.class);
        System.out.println(scannerConfigurer);


    }
}
