package br.gov.sibbr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

	public static void main(String[] args) throws SQLException {
		// Configuration:
		String server = "146.134.8.135";
		String port = "5432";
		String database = "dataportal";
		String user = "dbadmin";
		String password = "dbadmin";
		
		// Get connection:
		Connection connection = connect(server, port, database, user, password);
		
		// Execute test query:
		if (connection != null) {
			sampleQuery(connection);
			// Close connection:
			closeConnection(connection);
		}			
	}

	public static Connection connect(String server, String port, String database, String user, String password) {
		Connection conn = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(
					"jdbc:postgresql://" + ":" + port + "/" + database,
					user, password);
			if (conn != null) {
				System.out.println("Connection Established");
			} else {
				System.out.println("Connection Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sampleQuery(Connection con) throws SQLException {
		Statement stmt = null;
		String query = "select count(*) from occurrence";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int total = rs.getInt("count");
				System.out.println("Total amount of registers for the occurrence table = " + total + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
