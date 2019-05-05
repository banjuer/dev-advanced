package concurrency.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程对count加100次
 */
public class MutexTest {

	private static Mutex mutex = new Mutex();
	private static int count = 0;
	private static int THREAD_NUMBERS = 1;

	public static void main(String[] args) throws InterruptedException {
		AtomicInteger times = new AtomicInteger(100);
		for (int i = 0; i < THREAD_NUMBERS; i++) {
			new Thread(new Adder(times), String.valueOf(i)).start();
		}
		TimeUnit.SECONDS.sleep(2);
		System.out.println("add total 100, now count is " + count);
	}

	static void incr() {
		mutex.lock();
		++count;
		// incr();
        mutex.unlock();
	}

	static class Adder implements Runnable {

		private AtomicInteger times;

		Adder(AtomicInteger times) {
			this.times = times;
		}

		@Override
		public void run() {
			while (times.get() != 0) {
				times.decrementAndGet();
				incr();
			}
		}
	}

}
