package concurrency.tookit;

import java.util.concurrent.Exchanger;

/**
 * 线程间数据交换
 * 数据校验, 遗传算法
 */
public class ExchangerTest {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {

        new Thread(() -> {
            String A = "流水A";
            try {
                String B = exchanger.exchange(A);
                System.out.println(Thread.currentThread().getName() + " get exchange: " + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            String B = "流水B";
            try {
                String A = exchanger.exchange(B);
                System.out.println(Thread.currentThread().getName() + " get exchange: " + A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
