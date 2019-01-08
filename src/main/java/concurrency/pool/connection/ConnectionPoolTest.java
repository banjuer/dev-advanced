package concurrency.pool.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : gcs
 * @date :
 */
public class ConnectionPoolTest {

    /**
     * 等待超时时间ms
     */
    static long timeout = 1000;

    static ConnectionPool pool = new ConnectionPool(10);

    /**
     * 保证所有的ConnectionRunner同时开始
     */
    static CountDownLatch start = new CountDownLatch(1);

    /**
     * main线程等待
     */
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量
        final int threadCount = 10;
        // 每个线程获取次数
        final int count = 20;
        end = new CountDownLatch(threadCount);
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            new Thread(new ConnectionRunner(got, notGot, count), "ConnectionRunner-" + i).start();
        }
        start.countDown();
        end.await();
        System.out.println("total invoke:" + (threadCount * count));
        System.out.println("got connections:" + got);
        System.out.println("notGot connections:" + got);
    }

    static class ConnectionRunner implements Runnable {

        int count;

        AtomicInteger got;

        AtomicInteger notGot;

        ConnectionRunner(AtomicInteger got, AtomicInteger notGot, int count) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        public void run() {
            try {
                // 等待, 所有的runner同时运行
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (count > 0) {
                try {
                    Connection con = pool.fetch(timeout);
                    if (con == null)
                        notGot.getAndIncrement();
                    else {
                        try {
                            con.createStatement();
                            con.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            pool.release(con);
                            got.getAndIncrement();
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    count --;
                }
            }

            // main线程等待计数
            end.countDown();
        }
    }

}
