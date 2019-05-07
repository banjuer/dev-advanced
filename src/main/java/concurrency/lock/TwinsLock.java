package concurrency.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义的同步组件: 同一时刻，只允许至多两个线程同时访问，超过两个线程的访问将被阻塞 1. 同一时刻允许多个线程: 故需实现同步器share相关方法 2.
 * 至多2个: 维护初始值为2的状态, CAS获取锁减1
 */
public class TwinsLock {

	private Sync lock = new Sync(2);

	private static class Sync extends AbstractQueuedSynchronizer {

		Sync(int count) {
			if (count <= 0)
				throw new IllegalArgumentException("Number of resources can not be less than  1");
			setState(count);
		}

		@Override
		protected int tryAcquireShared(int reduceCount) {
			for (;;) {
                int current = getState();
                int newCount = current - reduceCount;
                // 返回值<0获取失败
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
			}
		}

		@Override
		protected boolean tryReleaseShared(int reduceCount) {
		    for (;;) {
		        int current = getState();
		        int newCount = current + reduceCount;
		        if (compareAndSetState(current, newCount))
		            return true;
            }
		}

	}

	public void lock() {
		lock.acquireShared(1);
	}

	public void unlock() {
		lock.releaseShared(1);
	}

}
