package dbAccess;

import java.io.File;
import java.sql.*;

public class Sqlite {

	private static String url = "jdbc:sqlite:databaseCrypt.db";

	// http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
	/**
	 * Connect to a sample database
	 */
	public static void connect() {
		Connection conn = null;
		try {
			// db parameters
			Class.forName("org.sqlite.JDBC");

			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// FUCK IT
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void createNewDatabase(String fileName) {

		File f = new File(fileName);

		if (!f.exists()) {

			try (Connection conn = DriverManager.getConnection(url)) {
				if (conn != null) {
					DatabaseMetaData meta = conn.getMetaData();
					System.out.println("The driver name is "
							+ meta.getDriverName());
					System.out.println("A new database has been created.");
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	// http://www.sqlitetutorial.net/sqlite-java/create-table/
	public static void execQueryNoReturn(String sql) {
		// SQLite connection string

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * select all rows in the warehouses table
	 */
	public static ResultSet selectAll(String sql) {
		

		try (Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement()){
			
				ResultSet rs = stmt.executeQuery(sql) ;

			return rs;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
