package com.yehuda.coupons.utils;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yehuda.coupons.data_base.ConnectionPool;
import com.yehuda.coupons.exceptions.ApplicationException;

public class JdbcUtils {
//	static {
//		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

//	public static Connection getConnection() throws SQLException {
//		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupons1?autoReconnect=true&useSSL=false", "root", "1234");
//		return connection;
//	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement) throws ApplicationException {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}

		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws ApplicationException {
		closeResources(connection, preparedStatement);
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
