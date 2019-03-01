package realtime.article;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author youfang
 * @version [1.0.0, 2019-03-01 下午 05:06]
 **/
public class ArticlePublishJob implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(ArticlePublishJob.class);
    /**id映射*/
    private static volatile ConcurrentHashMap<Long, String> idMapping = new ConcurrentHashMap<>();
    /**任务缓存*/
    private static volatile ConcurrentHashMap<String, ArticlePublishJob> cache = new ConcurrentHashMap<>();
    //任务取消状态
    private volatile AtomicBoolean canceled = new AtomicBoolean(false);
    //任务id
    private String jobId;
    //文章id
    private Long articleId;
    //发布时间
    private Date pubTime;

    public ArticlePublishJob(Article article) {
        this.pubTime = article.getPubTime();
        this.jobId = UUID.randomUUID().toString();
        this.articleId = article.getId();
        //取消并清除上一次任务
        cancelAndClearLastJobIfExist();
        //缓存本次任务
        cacheThisJob();
    }
    @Override
    public void run() {
        //判断任务是否被取消
        if (!canceled.get() && new Date().after(pubTime)) {
            //更新文章状态
            Article updArticle = new Article();
            updArticle.setId(articleId);
            updArticle.setPubTime(pubTime);
            updArticle.setStatus(1);
            //从缓存中清理本任务
            System.out.println("定时执行完成：" + updArticle);
            clear();
        }
    }

    /**
     * 取消并清除上一次任务
     */
    private void cancelAndClearLastJobIfExist(){
        if (StringUtils.isNotEmpty(idMapping.get(articleId))) {
            ArticlePublishJob lastJob = cache.get(idMapping.get(articleId));
            if (null != lastJob) {
                lastJob.cancelJob();
                cache.remove(idMapping.get(articleId));
                idMapping.remove(articleId);
            }
        }
    }

    /**
     * 缓存本次任务
     */
    private void cacheThisJob(){
        //id映射
        idMapping.put(this.articleId, this.jobId);
        //文章发布任务缓存
        cache.put(this.jobId, this);
    }


    /**
     * 取消任务
     */
    public void cancelJob() {
        this.canceled.set(true);
    }

    /**
     * 清理缓存
     */
    public void clear() {
        idMapping.remove(articleId);
        cache.remove(this.jobId);
    }
}
