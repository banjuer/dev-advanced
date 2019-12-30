package practice.concurrency.tookit.cb;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest1 {

    static CyclicBarrier cb = new CyclicBarrier(2);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        new Thread(new Worker()).start();
        cb.await();
        System.out.println(2);
    }

    static class Worker implements Runnable {

        @Override
        public void run() {
            try {
                cb.await();
                System.out.println(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
