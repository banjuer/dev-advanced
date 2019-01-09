package concurrency.pool.connection;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author : gcs
 * @date :
 */
@Slf4j
public class ConnectionDriver {

	/**
	 * 模拟数据库操作, 提交时线程睡眠100ms
	 */
	static class ConnectionHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if ("commit".equals(method.getName())) {
				TimeUnit.MILLISECONDS.sleep(100);
				log.info("connection is commited");
			}
			return null;
		}
	}

	/**
	 * 创建一个commit时睡眠100ms的连接代理
	 */
	public static Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
				new Class[] { Connection.class }, new ConnectionHandler());
	}

}
