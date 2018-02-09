package cn.acyou.iblog.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author youfang
 * @date 2018-02-09 12:36
 **/
public class AfterCommitExecutorImpl extends TransactionSynchronizationAdapter implements AfterCommitExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AfterCommitExecutorImpl.class);
    private static final ThreadLocal<List<Runnable>> RUNNABLES = new NamedThreadLocal<>("AfterCommitRunnable");
    private ExecutorService threadPool;
    @Value("${aftercommit.thread}")
    private int threadCount;

    @Override
    public void execute(Runnable runnable) {
        LOGGER.info("Submitting new runnable {} to run after commit", runnable);
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            LOGGER.info("Transaction synchronization is NOT ACTIVE. Executing right now runnable {}", runnable);
            runnable.run();
            return;
        }
        List<Runnable> threadRunnables = RUNNABLES.get();
        if (threadRunnables == null) {
            threadRunnables = new ArrayList<>();
            RUNNABLES.set(threadRunnables);
            TransactionSynchronizationManager.registerSynchronization(this);
        }
        threadRunnables.add(runnable);
    }

    @Override
    public void afterCommit() {
        List<Runnable> threadRunnables = RUNNABLES.get();
        for (Runnable runnable : threadRunnables) {
            try {
                threadPool.execute(runnable);
            } catch (RuntimeException e) {
                LOGGER.error("Failed to execute runnable " + runnable, e);
            }
        }
    }

    @Override
    public void afterCompletion(int status) {
        LOGGER.info("Transaction completed with status {}", status == STATUS_COMMITTED ? "COMMITTED" : "ROLLED_BACK");
        RUNNABLES.remove();
    }

    @PostConstruct
    public void init() {
        threadPool = Executors.newFixedThreadPool(threadCount);
    }

    @PreDestroy
    public void shutDown() {
        threadPool.shutdown();
        LOGGER.info("shutdown threadPool");

    }
}
