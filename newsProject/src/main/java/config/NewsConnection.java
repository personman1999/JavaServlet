package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class NewsConnection {
	public static Connection getConnection() {
		Connection connection = null;
		// Khai báo driver sử dụng cho JDBC
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Dùng driver để mở kết nối đến CSDL
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/NewsDB", "root", "admin123");
		} catch (Exception e) {
			System.out.println("Lỗi kết nối CSDL" + e.getMessage());
		}

		return connection;
	}
}
