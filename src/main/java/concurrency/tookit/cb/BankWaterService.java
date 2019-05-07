package concurrency.tookit.cb;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 多线程计算最后合并
 *
 * 四个线程分别计算sheet数据, 最后汇总
 */
public class BankWaterService implements Runnable{

    static final int SHEETS = 4;

    private CyclicBarrier cb = new CyclicBarrier(SHEETS, this);

    Executor executor = Executors.newFixedThreadPool(SHEETS);

    /**
     * 存储每个sheet统计结果
     */
    private Map<String, Integer> sheetCount = new ConcurrentHashMap<>(SHEETS);

    /**
     * 开始并发统计每个sheet
     */
    public void count() {
        for (int i = 0; i < SHEETS; i++) {
            // 模拟计算
            executor.execute(() -> {
                sheetCount.put(Thread.currentThread().getName(), 1);
                try {
                    // 计算完等待
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        // 最终汇总
        int result = 0;
        for ( Integer count : sheetCount.values()) {
            result += count;
        }
        System.out.println("total count: " + result);
    }

    public static void main(String[] args) {
        BankWaterService waterService = new BankWaterService();
        waterService.count();
    }

}
