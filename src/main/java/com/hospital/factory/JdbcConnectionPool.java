package com.hospital.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import com.hospital.utils.Properties;


/**
 * Jdbc connection pool.
 *
 * @author Shivam Khare
 */
public class JdbcConnectionPool {

	private Properties properties;

	Queue<Connection> connectionPool = new LinkedList<Connection>();

	public JdbcConnectionPool(Properties properties) {
		this.properties = properties;
		initializeConnectionPool();
	}

	public synchronized Connection getConnectionFromPool() {
		Connection connection = null;
		if (connectionPool.size() > 0) {
			connection = (Connection) connectionPool.remove();
		}
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection) {
		if(connection != null) {
			connectionPool.add(connection);
		}
	}

	private void initializeConnectionPool() {
		while (!checkIfConnectionPoolIsFull()) {
			connectionPool.add(createNewConnectionForPool());
		}
	}

	private synchronized boolean checkIfConnectionPoolIsFull() {
		final int MAX_POOL_SIZE = properties.getPaymentDbConnectionCount();
		if (connectionPool.size() < MAX_POOL_SIZE) {
			return false;
		}
		return true;
	}

	private Connection createNewConnectionForPool() {

		Connection connection = null;
		try {
			connection = (Connection) DriverManager.getConnection(properties.getPaymentDbUrl(), properties.getPaymentDbUsername(), properties.getPaymentDbPassword());
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
