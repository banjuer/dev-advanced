package concurrency.pool.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author : gcs
 * @date :
 */
public class ConnectionDriver {

	/**
	 * 模拟数据库操作, 提交时线程睡眠100ms
	 */
	static class ConnectionHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if ("commit".equals(method.getName()))
				TimeUnit.MILLISECONDS.sleep(100);
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
