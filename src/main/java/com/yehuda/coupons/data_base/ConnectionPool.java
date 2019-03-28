package com.yehuda.coupons.data_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;

public class ConnectionPool {
	private static ConnectionPool instance = null;
	private Set<Connection> connections = new HashSet<Connection>();

	private final String url1 = "jdbc:mysql://localhost/coupon_DB?serverTimezone=UTC";
	private final String url2 = "root";
	private final String url3 = "123456";
	private final String driverName = "com.mysql.cj.jdbc.Driver";

	private static final int MAX_CON = 10;
	private boolean open = true;

	/**
	 * CTOR, create the instance of ConnectionPool. the CTOR connect to the data
	 * base of the coupon system, and create a pool of connections.
	 * 
	 * @throws ApplicationException
	 */
	private ConnectionPool() throws ApplicationException {
		super();
		try {
			Class.forName(driverName).newInstance();
		} catch (Exception e) {
			// in the next version, here i will log the exception to data base / file
			throw new ApplicationException(ErrorType.CONNECTION_POOL_ERROR, e);

		}
		for (int i = 0; i < MAX_CON; i++) {
			try {
				connections.add(DriverManager.getConnection(url1, url2, url3));
			} catch (SQLException e) {
				// in the next version, here i will log the exception to data base / file
				throw new ApplicationException(ErrorType.CONNECTION_POOL_ERROR, e);
			}
		}
	}

	/**
	 * returns a connection to the DataBase from the ConnectionPool. if all
	 * connections in used, the method waiting to a connection that will be
	 * available.
	 * 
	 * @return connection to DataBase
	 * @throws ApplicationException
	 */
	public synchronized Connection getConnection() throws ApplicationException {
		// check if the system is open
		if (open) {

			Iterator<Connection> iterator = connections.iterator();
			while (!iterator.hasNext()) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new ApplicationException(ErrorType.CONNECTIONS_ERROR, e);
				}
			}
			return iterator.next();

		} else {
			throw new ApplicationException(ErrorType.SYSTEM_CLOSE_ERROR);
		}
	}

	/**
	 * putting the current connection into the ConnectionPool, and notifies there is
	 * a connection that ready to use.
	 * 
	 * @param connection a connection that unused
	 * @throws ApplicationException
	 */
	public synchronized void returnConnection(Connection connection) throws ApplicationException {
		
		connections.add(connection);

		try {
			notifyAll();
		} catch (IllegalMonitorStateException e) {
			throw new ApplicationException(ErrorType.CONNECTIONS_ERROR, e);
		}
	}

	/**
	 * close all connections to the DataBase that in the ConnectionPool.
	 * 
	 * @throws ApplicationException
	 */
	public void closeAllConnections() throws ApplicationException {
		open = false;
		while (connections.size() < MAX_CON) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new ApplicationException(ErrorType.CONNECTIONS_ERROR, e);
			}
		}
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ApplicationException(ErrorType.SQL_EXCEPTION, e);
			}
		}
	}

	/**
	 * returns the instance of the class ConnectionPool
	 * 
	 * @return singleton of ConnectionPool
	 * @throws ApplicationException
	 */
	public static ConnectionPool getInstance() throws ApplicationException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
}
