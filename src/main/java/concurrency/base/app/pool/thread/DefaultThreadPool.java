package concurrency.base.app.pool.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author : gcs
 * @date :
 */
@Slf4j
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Runnable> {

    /**
     * 最大工作线程
     */
    private static final int MAX_WORKER_NUMBERS = 10;

    /**
     * 最小工作线程
     */
    private static final int MIN_WORKER_NUMBERS = 1;

    /**
     * 默认工作线程
     */
    private static final int DEFAULT_WORKER_NUMBERS = 5;

    /**
     * 工作的线程数
     */
    private int workerNums = DEFAULT_WORKER_NUMBERS;

    /**
     * job队列
     */
    private final LinkedList<Job> jobs = new LinkedList<>();


    @Override
    public void submit(Runnable runnable) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void increaseWorkers(int nums) {

    }

    @Override
    public void decreaseWorkers(int nums) {

    }

    @Override
    public int jobSize() {
        return jobs.size();
    }

    /**
     * 工作线程实现
     */
    class Worker implements Runnable {

        /**
         * 运行状态控制
         */
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    try {
                        // job队列为空则等待
                        while (jobs.isEmpty())
                            jobs.wait();
                    } catch (InterruptedException e) {
                        // 中断后结束线程
                        Thread.currentThread().interrupt();
                        return;
                    }
                    job = jobs.removeFirst();
                    if (job != null) {
                        try {
                            job.run();
                        } catch (Exception e) {
                            log.error("", e);
                        }
                    }
                }
            }
        }

        public void shutdown() {
            this.running = false;
        }

    }

}
