package practice.concurrency.base.app.pool.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : gcs
 * @date :
 */
@Slf4j
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

	/**
	 * 最大工作线程
	 */
	private static final int MAX_WORKER_NUMBERS = 10;

	/**
	 * 最小工作线程
	 */
	private static final int MIN_WORKER_NUMBERS = 1;

	/**
	 * 默认工作线程
	 */
	private static final int DEFAULT_WORKER_NUMBERS = 5;

	/**
	 * 工作的线程数
	 */
	private int workerNums = DEFAULT_WORKER_NUMBERS;

	/**
	 * job队列
	 */
	private final LinkedList<Job> jobs = new LinkedList<>();

	/**
	 * 工作者队列
	 */
	private final List<Worker> workers = Collections.synchronizedList(new LinkedList<>());

	/**
	 * 线程编号
	 */
	private AtomicInteger threadNum = new AtomicInteger();

	public DefaultThreadPool() {
		this(DEFAULT_WORKER_NUMBERS);
	}

	public DefaultThreadPool(int nums) {
		workerNums = nums < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS
				: nums > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : nums;

		initializeWorkers(workerNums);
	}

	@Override
	public void submit(Job job) {
		if (job == null)
			throw new IllegalArgumentException("job can not be null");
		synchronized (jobs) {
			jobs.add(job);
			// 通知一个工作线程
			jobs.notify();
		}
	}

	@Override
	public void shutdown() {
		for (Worker worker : workers) {
			worker.shutdown();
		}
	}

	@Override
	public void increaseWorkers(int nums) {
        synchronized (jobs) {
            checkWorkerNums(workerNums + nums);
            initializeWorkers(nums);
            this.workerNums += nums;
        }
	}

	@Override
	public void decreaseWorkers(int nums) {
        synchronized (jobs) {
            checkWorkerNums(workerNums - nums);
            while (nums > 0) {
                Worker worker = workers.remove(0);
                if (worker != null) {
                    worker.shutdown();
                    nums--;
                }
            }
            workerNums -= nums;
        }
	}

    /**
     * 工作线程数量合法性检查
     * @param workers
     */
	private void checkWorkerNums(int workers) {
        if (workers > MAX_WORKER_NUMBERS || workers < MIN_WORKER_NUMBERS)
            throw new IllegalArgumentException("illegal argument nums:" + workers);
    }

	/**
	 * 初始化线程工作者
	 * 
	 * @param nums
	 */
	private void initializeWorkers(int nums) {
		for (int i = 0; i < nums; i++) {
			Worker worker = new Worker();
			workers.add(worker);
			new Thread(worker, "DefaultThreadPool-Worker-" + threadNum.getAndIncrement()).start();
		}
	}

	@Override
	public int jobSize() {
		return jobs.size();
	}

	/**
	 * 工作线程实现
	 */
	class Worker implements Runnable {

		/**
		 * 运行状态控制
		 */
		private volatile boolean running = true;

		@Override
		public void run() {
			while (running) {
				Job job;
				synchronized (jobs) {
					try {
						// job队列为空则等待
						while (jobs.isEmpty())
							jobs.wait();
					} catch (InterruptedException e) {
						// 中断后结束线程
						Thread.currentThread().interrupt();
						return;
					}
					job = jobs.removeFirst();
					if (job != null) {
						try {
							job.run();
						} catch (Exception e) {
							log.error("", e);
						}
					}
				}
			}
		}

		void shutdown() {
			this.running = false;
		}

	}

}
