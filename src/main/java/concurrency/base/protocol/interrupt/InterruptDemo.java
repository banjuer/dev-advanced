package concurrency.base.protocol.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author : gcs
 * @date :
 */
@Slf4j
public class InterruptDemo {

    static final List<Integer> pool = new LinkedList<>();

    /**
     * main等待
     */
    static CountDownLatch end = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runner(), "demo-thread").start();
        Thread.sleep(100);
        synchronized (pool) {
            pool.add(1);
            pool.notify();
        }
        end.await();
    }

    static class Runner implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            try {
                log.info("{} is waiting", name);
                synchronized (pool) {
                    pool.wait();
                }
            } catch (InterruptedException e) {
                log.info("{} is interrupted", name);
                end.countDown();
                return;
            }
            log.info("{} is running", name);
            end.countDown();
        }
    }

}
