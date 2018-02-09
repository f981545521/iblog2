package cn.acyou.iblog.maintest;

import cn.acyou.iblog.executor.AfterCommitExecutor;
import org.junit.Test;

/**
 * @author youfang
 * @date 2018-02-09 12:48
 **/
public class ExcutorTest extends BaseTest{

    @Test
    public void test1(){
        AfterCommitExecutor executor = ctx.getBean("afterCommitExecutor", AfterCommitExecutor.class);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 100; i > 0; i--) {
                    if (i == 50){
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                }
            }
        });
        log.info("执行结束！！");
        System.out.println(executor);


    }
}
