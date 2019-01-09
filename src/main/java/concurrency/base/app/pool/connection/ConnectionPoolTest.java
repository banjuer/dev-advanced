package concurrency.base.app.pool.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : gcs
 * @date :
 */
@Slf4j
public class ConnectionPoolTest {

	/**
	 * 等待超时时间ms
	 */
	private static long timeout = 1000;

	private static ConnectionPool pool = new ConnectionPool(10);

	/**
	 * 保证所有的ConnectionRunner同时开始
	 */
	private static CountDownLatch start = new CountDownLatch(1);

	/**
	 * main线程等待
	 */
	private static CountDownLatch end;



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
		log.info("total invoke:{}", threadCount * count);
		log.info("got connections:{}", got);
		log.info("notGot connections:{}", notGot);
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

		@Override
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
					count--;
				}
			}

			// main线程等待计数
			end.countDown();
		}
	}

}
