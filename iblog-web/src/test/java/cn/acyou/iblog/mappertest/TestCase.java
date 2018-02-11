package cn.acyou.iblog.mappertest;

import cn.acyou.iblog.maintest.BaseTest;
import cn.acyou.iblog.service.BossService;
import org.junit.Test;

/**
 * @author youfang
 * @date 2018-02-09 20:58
 **/
public class TestCase extends BaseTest{

    @Test
    public void test1(){
        BossService tBossService = ctx.getBean("tBosssService", BossService.class);
        System.out.println(tBossService);
    }
}
