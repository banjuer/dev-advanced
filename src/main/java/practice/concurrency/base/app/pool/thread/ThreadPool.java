package practice.concurrency.base.app.pool.thread;

/**
 * @author : gcs
 * @date :
 */
public interface ThreadPool<Job extends Runnable> {

	/**
	 * 提交任务
	 *
	 * @param job
	 */
	void submit(Job job);

	/**
	 * 关闭线程池
	 */
	void shutdown();

	/**
	 * 增加工作线程
	 *
	 * @param nums
	 */
	void increaseWorkers(int nums);

	/**
	 * 减少工作线程
	 * 
	 * @param nums
	 */
	void decreaseWorkers(int nums);

	/**
	 * 运行的任务数量
	 * 
	 * @return
	 */
	int jobSize();

}
