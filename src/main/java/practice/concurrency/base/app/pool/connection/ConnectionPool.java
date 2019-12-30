package practice.concurrency.base.app.pool.connection;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 一个简单的线程池
 * @author : gcs
 * @date :
 */
public class ConnectionPool {

    private final LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize <= 0)
            throw new IllegalArgumentException("pool's size can not be 0");
        for (int i = 0; i < initialSize; i++) {
            pool.add(ConnectionDriver.createConnection());
        }
    }

    /**
     * 释放连接
     * @param con
     */
    public void  release(Connection con) {
        if (con == null)
            throw new IllegalArgumentException("connection can not be null");
        synchronized (pool) {
            pool.add(con);
            // 通知其他线程已经归还一个连接
            pool.notifyAll();
        }
    }

    /**
     * 获取一个连接
     * @param timeout
     * @return
     */
    public Connection fetch(long timeout) throws InterruptedException {
        synchronized (pool) {
            // 忽略超时, 一直等待直至获取连接
            if (timeout <= 0) {
                while (pool.isEmpty())
                    pool.wait();
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + timeout;
                long remaining = timeout;
                while (remaining > 0) {
                    pool.wait(remaining);
                    // 唤醒后重新计算等待时间
                    remaining = future - System.currentTimeMillis();
                }
                Connection con = null;
                if (!pool.isEmpty())
                    con = pool.removeFirst();
                return con;
            }
        }
    }

}
