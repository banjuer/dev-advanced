package practice.concurrency.base.protocol;

/**
 * 加入目标线程, 并等待其执行结束后返回
 *
 * Demo: 创建10个线程, 每个线程等待前驱线程运行结束
 */
public class Join {

	public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            previous = thread;
            thread.start();
        }

        System.out.println("main is terminated...");
	}

    /**
     * 传入前驱线程, 当前线程等待前驱线程结束
     */
	static class Domino implements Runnable {

	    private Thread previous;

	    Domino(Thread previous) {
	        this.previous = previous;
        }

		@Override
		public void run() {
            try {
                previous.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is terminated...");
        }
	}

}
