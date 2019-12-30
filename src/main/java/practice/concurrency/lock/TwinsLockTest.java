package practice.concurrency.lock;

import java.util.concurrent.TimeUnit;

/**
 * 创建
 */
public class TwinsLockTest {

    private static TwinsLock lock = new TwinsLock();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Worker(), "thread" + i).start();
        }

        for (int i = 0; i < 5; i++) {
            TimeUnit.SECONDS.sleep(2);
            System.out.println();
        }
    }

    static class Worker implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }

}
