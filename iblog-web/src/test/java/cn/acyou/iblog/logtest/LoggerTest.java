package cn.acyou.iblog.logtest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-25 下午 05:07]
 **/
public class LoggerTest{
    private final static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void test1(){
        logger.debug("[{}]|{}|{}", "234", "MusicInfoServiceImpl.updateMusicInfoInfoByBehavior", "我就是简简单单的");
    }
}
