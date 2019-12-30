package practice.concurrency.tookit;

import java.util.concurrent.TimeUnit;

/**
 * 主线程等待其他线程结束
 */
public class JoinWaitTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is competed");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1");
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is competed");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("all done.");
    }

}
