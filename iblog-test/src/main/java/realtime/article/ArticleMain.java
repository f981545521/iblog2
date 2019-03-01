package realtime.article;

import cn.acyou.iblog.constant.AppConstant;
import cn.acyou.iblog.utility.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author youfang
 * @version [1.0.0, 2019-03-01 下午 05:10]
 * @since [天天健身/商品模块]
 **/
public class ArticleMain {
    /**
     * 创建线程池
     */
    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(50);

    public static void main(String[] args) {
        //新建任务，并设定执行时间
        Article article = new Article();
        article.setId(1L);
        article.setPubType(2);
        article.setStatus(0);
        article.setPubTime(DateUtil.parseDate("2019-03-01 17:57:54", AppConstant.SPECIFIC_DATE_FORMAT_PATTERN));
         Article article2 = new Article();
        article2.setId(1L);
        article2.setPubType(2);
        article2.setStatus(0);
        article2.setPubTime(DateUtil.parseDate("2019-03-01 17:58:34", AppConstant.SPECIFIC_DATE_FORMAT_PATTERN));
        ArticlePublishJob job = new ArticlePublishJob(article);
        ArticlePublishJob job2 = new ArticlePublishJob(article2);
        long delay = article.getPubTime().getTime() - System.currentTimeMillis();
        long delay2 = article2.getPubTime().getTime() - System.currentTimeMillis();
        service.schedule(job, delay, TimeUnit.MILLISECONDS);
        service.schedule(job2, delay2, TimeUnit.MILLISECONDS);
    }


}
