package cn.acyou.iblog.quarz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youfang
 * @date 2018-02-26 12:47
 **/
public class MonitorJob {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 第一个任务
     *
     */
    public void firstJob(){
        log.info("first QuarzJob"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    }
}
