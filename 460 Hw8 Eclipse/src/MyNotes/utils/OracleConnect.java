package MyNotes.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleConnect {
	public static final String connect_string = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	public static final String user_name = "zuniga7";
	public static final String password = "a2398";

	private static Connection connection;
	protected static Statement statement;

	public OracleConnect() {
		// establish connection
		try {
			Class.forName("oracle.jdbc.OracleDriver"); // Registers drivers

			connection = DriverManager.getConnection(connect_string, user_name,
					password); // get a connection
			if (connection == null)
				throw new Exception("getConnection failed");

			connection.setAutoCommit(true);// optional, but it sets auto commit
			// to true

			// create a statement (which allows me to go back on the result set)
			statement = connection
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

		} catch (SQLException e1) {
			System.out.println("Invalid username or password!");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * @return the statement
	 */
	public static Statement getStatement() {
		return statement;
	}
}
