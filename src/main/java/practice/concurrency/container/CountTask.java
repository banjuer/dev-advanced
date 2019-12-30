package practice.concurrency.container;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 累加计算
 * fork/join框架计算(类似mapreduce)
 */
public class CountTask extends RecursiveTask<Integer> {

    /**
     * 分割阀值
     */
    private static final int THRESHOLD = 1024;
    private int start, end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean computable = (end - start) <= THRESHOLD;
        if (computable) {
            for (int i = start; i <= end; i ++) {
                sum += i;
            }
        } else {
            // 需要继续细分
            int m = (end - start) / 2 + start;
            RecursiveTask left = new CountTask(start, m);
            RecursiveTask right = new CountTask(m + 1, end);
            left.fork();
            right.fork();
            int lsum = (Integer) left.join();
            int rsum = (Integer) right.join();
            sum = lsum + rsum;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int start = 1, end = 102400;
        long t1 = System.currentTimeMillis();
        int result = 0;
        for (int i = start; i <= end; i++) {
            result += i;
        }
        long t2 = System.currentTimeMillis();
        System.out.println("normal compute result:" + result +" cost:" + (t2 - t1));
        ForkJoinPool pool = new ForkJoinPool();
        RecursiveTask<Integer> task = new CountTask(start, end);
        ForkJoinTask<Integer> future = pool.submit(task);
        result = future.get();
        long t3 = System.currentTimeMillis();
        System.out.println("fork/join compute result:" + result +" cost:" + (t3 - t2));
    }

}
