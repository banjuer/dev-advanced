package practice.concurrency.lock.condition;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BoundedQueueTest {

    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<Integer> queue = new BoundedQueue<>(5);
        Thread producer = new Thread(new Producer(queue), "producer");
        Thread consumer = new Thread(new Consumer(queue), "consumer");
        consumer.start();
        producer.start();
        TimeUnit.SECONDS.sleep(60);
    }

    static class Producer implements Runnable {

        private BoundedQueue<Integer> queue;

        Producer(BoundedQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            Random random = new Random(System.currentTimeMillis());
            try {
                while (true) {
                    int p = random.nextInt(100);
                    queue.enqueue(p);
                    System.out.println("produce:" + p);
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable{

        private BoundedQueue<Integer> queue;

        Consumer(BoundedQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int c = queue.dequeue();
                    System.out.println("cost: " + c);
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
