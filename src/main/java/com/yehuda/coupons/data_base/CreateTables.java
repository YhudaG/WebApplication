package com.yehuda.coupons.data_base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {
	public static void main(String[] args) {
		String driverName = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(driverName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/?user=root&password=123456&serverTimezone=UTC";
		
//		String sql = "CREATE DATABASE coupon_DB";

		String sql3 = "DROP TABLE coupon";
		String sql5 = "DROP TABLE company";
		String sql2 = "DROP TABLE customer";
		String sql4 = "DROP TABLE customer_coupon";
		
		String sql1 = "USE coupon_DB";
//		
		
//		String sql2 = "CREATE TABLE Company(CompanyID BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, CompanyName VARCHAR(50), Password VARCHAR(50), Email VARCHAR(50))";
//		String sql3 = "CREATE TABLE Customer(CustomerID BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, CustomerName VARCHAR(50), Password VARCHAR(50))";
//		String sql4 = "CREATE TABLE Coupon(CouponID BIGINT  PRIMARY KEY NOT NULL AUTO_INCREMENT, Title VARCHAR(50),"
//				+ " StartDate DATE, EndDate DATE, Amount INTEGER, Type VARCHAR(50),"
//				+ " Message VARCHAR(50), Price FLOAT, Image VARCHAR(50), CompanyID BIGINT, "+ 
//				"    FOREIGN KEY (CompanyID) REFERENCES Company(CompanyID))";
//		
//		String sql5 = "CREATE TABLE Customer_Coupon(customerID BIGINT, CouponID BIGINT, PRIMARY KEY(CustomerID, CouponID))";

		try (Connection connection = DriverManager.getConnection(url); Statement statement = connection.createStatement()) {
			System.out.println("connection to DB");

			statement.executeUpdate(sql1);
			statement.executeUpdate(sql2);
			statement.executeUpdate(sql3);
			statement.executeUpdate(sql4);
			statement.executeUpdate(sql5);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("disconnection from DB");
	}

}
