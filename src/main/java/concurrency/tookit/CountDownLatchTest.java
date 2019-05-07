package concurrency.tookit;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class CountDownLatchTest {

    private static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println(1);
            latch.countDown();
            System.out.println(2);
            latch.countDown();
        }).start();
        latch.await();
        System.out.println(3);
        System.out.println("all done");
    }

}
