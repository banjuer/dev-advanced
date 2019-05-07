package concurrency.tookit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 控制有效资源数访问
 * 30个工作线程, 有效资源10个
 */
public class SemaphoreTest {

    static final int WORKERS = 9;

    static Semaphore semaphore = new Semaphore(3);
    static ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

    public static void main(String[] args) {
        for (int i = 0; i < WORKERS; i++) {
            pool.execute(() -> {
                try {
                    semaphore.acquire();
                    saveData();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
    }

    static void saveData() {
        System.out.println("saved data..");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
