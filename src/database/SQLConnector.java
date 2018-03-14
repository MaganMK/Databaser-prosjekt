package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class SQLConnector {
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/didris_test4";
	private static String username = "didris_test";
	private static String password = "1234";
	private static Connection connection;
	
    public static void showResult(int fromDate, int toDate, String ovelse) {

    }
	// Metode for aa hente en SQL connection
	public static Connection getConnection() throws SQLException {
		if(connection == null) {
			try {
				System.out.println("Connecting database...");
				// Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(SQLConnector.url, SQLConnector.username, SQLConnector.password);
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
	public static void insertToTable(String table, String value) throws SQLException, ClassNotFoundException
	{
		Connection connection = SQLConnector.getConnection();
		Statement statement = connection.createStatement();
		
		statement.executeUpdate("INSERT INTO " + table + " VALUES (\"" + value + "\");");
	}
	
	public static void addExerciseToGroup(String exercise, String group) throws SQLException, ClassNotFoundException
	{
		Connection connection = SQLConnector.getConnection();
		Statement statement = connection.createStatement();
		
		statement.executeUpdate("INSERT INTO OvelseIOvelsesgruppe VALUES (\"" + exercise + "\",\"" + group + "\");");
	}
	
	public static List<String> getExercisesInGroup(String group) throws ClassNotFoundException, SQLException
	{
		List<String> result = new ArrayList<>();
		ResultSet rs = getResultSet("SELECT * FROM `OvelseIOvelsesgruppe` WHERE ovelsesgruppe_navn =\"" + group+"\";");
		while(rs.next()) {
				String current = rs.getString("ovelse_navn");									
				result.add(current);
			
		}
		return result;
	}
	
	public static ResultSet getResultSet(String query) throws SQLException, ClassNotFoundException {
		Connection connection;
		try {
			connection = SQLConnector.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch(SQLException e) {
			throw e;
		}
	}
	public static List<ArrayList<String>> getAllSessions() throws SQLException, ClassNotFoundException {
		List<ArrayList<String>> result = new ArrayList<>();
		ResultSet rs = getResultSet("SELECT * FROM `Okt`;");
		while(rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(String.valueOf(rs.getInt("okt_id"))); 
				temp.add(rs.getString("dato")); 
				temp.add(rs.getString("tidspunkt")); 
				temp.add(String.valueOf(rs.getInt("varighet"))); 
				temp.add(String.valueOf(rs.getInt("personlig_form"))); 
				temp.add(String.valueOf(rs.getInt("prestasjon"))); 	
				temp.add(rs.getString("notat"));
				result.add(temp);
		}
		return result;
	}
}














