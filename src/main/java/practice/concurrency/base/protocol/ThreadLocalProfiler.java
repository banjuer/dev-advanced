package practice.concurrency.base.protocol;

import java.util.concurrent.TimeUnit;

/**
 * threadlocal:
 */
public class ThreadLocalProfiler {

    private static final ThreadLocal<Long> TIME_LOCAL = new ThreadLocal<Long>(){
        protected Long initalValue() {
            return System.currentTimeMillis();
        }
    };

    public static void begin() {
        TIME_LOCAL.set(System.currentTimeMillis());
    }

    public static long end() {
        return System.currentTimeMillis() - TIME_LOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalProfiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(ThreadLocalProfiler.end());
    }

}
