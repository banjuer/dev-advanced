package practice.concurrency.base.protocol;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 等待通知机制
 *
 * 等待通知范式:
 * thread1:
 * synchronized(obj) {
 * 	while(条件不满足)
 * 		obj.wait;
 * }
 * thread2:
 * synchronized(obj) {
 * 	改变条件
 * 	obj.notify[All];
 * }
 *
 */
public class WaitNotifyMechanism {

	static Object lock = new Object();

	static volatile boolean flag = true;

	public static void main(String[] args) throws InterruptedException {
        Thread wait = new Thread(new Wait(), "Wait_Thread");
        wait.start();
        // wait线程充分运行, 跑到wait处
        TimeUnit.SECONDS.sleep(2);
        Thread notify = new Thread(new Notify(), "Notify_Thread");
        notify.start();
	}

	static class Wait implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				// 条件不满足时, wait并释放锁
				while (flag) {
					try {
						System.out.println(Thread.currentThread() + " flag is true, wait @"
								+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread() + " flag is false, running @"
						+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
	}

	static class Notify implements Runnable {

		@Override
		public void run() {
			synchronized (lock) {
			    // 获取lock并通知, 通知不会释放锁
				System.out.println(Thread.currentThread() + " hold lock, notify @"
						+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
				lock.notify();
				flag = false;
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			synchronized (lock) {
				System.out.println(Thread.currentThread() + " hold lock again, sleep @"
						+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
		}
	}

}
