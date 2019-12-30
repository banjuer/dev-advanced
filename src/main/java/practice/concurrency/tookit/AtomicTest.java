package practice.concurrency.tookit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

    static volatile boolean flag = true;
    static String dateHour = "2019051110";
    static AtomicInteger total = new AtomicInteger(5);
    static ExecutorService executorService = new ThreadPoolExecutor(4, 6, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) {
        while (flag)
            executorService.execute(() -> {
                if (total.get() > 0) {
                    total.decrementAndGet();
                    increase();
                } else
                    flag = false;
            });
        executorService.shutdown();
    }

    static synchronized void increase() {
        String dateHourStr = dateHour;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date =  sdf.parse(dateHourStr);
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, 1);
            date = calendar.getTime();
            String tmp = sdf.format(date);
            System.out.println(tmp);
            dateHour = tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
