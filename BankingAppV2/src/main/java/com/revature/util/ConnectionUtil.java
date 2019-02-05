package com.revature.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
private static Connection connection;
	
	public ConnectionUtil() {
		
	}
	
	public ConnectionUtil(String url, String role, String password, 
			Driver driver) throws SQLException {
		
		ConnectionUtil connectionUtil = new ConnectionUtil("jdbc:postgresql://localhost:5432/postgres",
				"bank_project_jdbc", "top-secret-password", new org.postgresql.Driver());
	}
		static {
			try {
				DriverManager.registerDriver(new org.postgresql.Driver());
			} catch(SQLException e) {
				e.printStackTrace();
			}
		
	}
		
	public static Connection newConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                "bank_project_jdbc", "top-secret-password");
	}
}
