package practice.concurrency.lock.condition;

import util.ArrayUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有界对列
 */
public class BoundedQueue<T> {

    private T[] items;

    /**
     * 队列头尾指针. head指向元素位置, tail指向即将加入的元素位置, 元素个数
     */
    private int head, tail, count;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 元素出入队列需要两中等待条件:
     * 1. 入队 -> 队列未满
     * 2. 出队 -> 队列不为空
     */
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    @SuppressWarnings("unchecked")
    public BoundedQueue(int size) {
        items = (T[])new Object[size];
    }

    /**
     * 入队
     * @param t
     */
    public void enqueue(T t) throws InterruptedException {
        lock.lock();
        try {
            // 防止过早或意外的通知
            while (count == items.length)
                notFull.await();
            items[tail] = t;
            count++;
            if (++tail == items.length)
                tail = 0;
            notEmpty.signal();
            ArrayUtil.print(items);
        } finally {
            lock.unlock();
        }
    }

    /**
     *
     * @return
     */
    public T dequeue() throws InterruptedException {
        lock.lock();
        T t;
        try {
            while (count == 0)
                notEmpty.await();
            t = items[head];
            count--;
            if (++head == items.length)
                head = 0;
            notFull.signal();
            ArrayUtil.print(items);
        } finally {
            lock.unlock();
        }
        return t;
    }

}
