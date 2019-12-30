package practice.concurrency.tookit.cb;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 中断检查
 */
public class CyclicBarrierTest3 {

    static CyclicBarrier cb = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                cb.await();
            } catch (InterruptedException e) {
                System.out.println(1);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            cb.await();
        } catch (Exception e) {
            System.out.println(cb.isBroken());
        }
    }

}
