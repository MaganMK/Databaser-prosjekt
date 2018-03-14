package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class SQLConnector {
	private static String url = "jdbc:sqlite:src/database/database.db";
	private static Connection connection;
	
	// Metode for aa hente en SQL connection
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			try {
				System.out.println("Connecting database...");
				String url = "jdbc:sqlite:src/database/database.db";
				connection = DriverManager.getConnection(url);
				System.out.println("Successfully connected to the database");
			}
			catch(SQLException e) {
				System.out.println("Could not connect to database");
				throw e;
			}
		}
		return connection;
	}
	
	// Metode for aa lukke en SQL connection
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Connection closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Funker for aa sette inn i ovelsesgruppe
	public static void addExerciseGroup(String groupName) throws SQLException
	{
		Connection connection = SQLConnector.getConnection();
		Statement statement = connection.createStatement();
		
		statement.executeUpdate("INSERT INTO ovelsesgruppe " + "VALUES (\"" + groupName + "\");");
	}
	
	/*
	public static void addExerciseToGroup(String exercise, String group) throws SQLException
	{
		Connection connection = SQLConnector.getConnection();
		Statement statement = connection.createStatement();
		
		
		
		statement.executeUpdate("INSERT INTO ovelseIOvelsesgruppe " + "VALUES (\"" + exercise + "\");");
	}
	*/
	
}














