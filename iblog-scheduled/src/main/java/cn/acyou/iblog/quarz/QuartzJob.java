package cn.acyou.iblog.quarz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-28 上午 09:47]
 **/
public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobName = jobExecutionContext.getJobDetail().getName(); // 获取jobName做业务处理
        System.out.println(jobName + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
        System.out.println("【移除定时】开始...");
        QuartzManager.removeJob(jobName);
        System.out.println("【移除定时】成功");
    }
}
