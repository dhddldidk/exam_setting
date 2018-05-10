package kr.or.dgit.exam_setting.djbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBCon {
	private static final DBCon instance = new DBCon(); // 순서1

	private Connection connection;

	public static DBCon getInstance() { // 순서3 get 만들기
		return instance;
	}

	private DBCon() { // 순서2 생성자를 private로 바꿔줌
		// String url = "jdbc:mysql://localhost:3306/erp_project";

		try {

			LoadProperties pro = new LoadProperties();
			Properties info = pro.getProperties();
			connection = DriverManager.getConnection(info.getProperty("url"), info);
		} catch (SQLException e) {
			System.err.printf("%s = %s%n", e.getMessage(), e.getErrorCode());
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void connectionClose() {
		try {
			connection.close();
			JOptionPane.showMessageDialog(null, "연결 해제되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
