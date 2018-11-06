package jdbc_practice;

import java.sql.*;

public class Driver {
	
	/* 
	 * 	Potential Error: Time Zone...
	 * 
	 * 	Solution:
	 *
	 * 	SET @@global.time_zone = '+00:00';
	 *	SET @@session.time_zone = '+00:00';
	 * 	SELECT @@global.time_zone, @@session.time_zone; 
	 */

	public static void main(String[] args) throws SQLException {
		/* This program uses MySQL's Sakila database and can be downloaded from MySQL's website. */
		
		// Replace with your database credentials
		String user = "root";
		String password = ""; 
		String url = "jdbc:mysql://localhost:3306/sakila";
		String sql;
		
		// Connect to the Database
		Connection con = DriverManager.getConnection(url, user, password);
		
		/* Blocks of different instructions */
		
		/* DQL: Data Query Language */
		try {
			sql = "SELECT * FROM actor LIMIT 100;";
			
			// Create a SQL statement
			Statement st = con.createStatement();
			
			// Execute the query
			ResultSet resultSet = st.executeQuery(sql);
			
			// Process the result set
			while(resultSet.next()) {
				System.out.println(resultSet.getInt("actor_id") + " - " + resultSet.getString("first_name"));
			}
			
			st.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		/* DML: Data Manipulation Language */
		try {
			sql = "INSERT INTO actor (first_name, last_name) VALUES (?, ?);";
			
			// Create a SQL statement
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "KENNETH");
			st.setString(2, "WONG");
			
			// Execute
			int numRowsAffected = st.executeUpdate();
			
			System.out.println("Insert Completed");
			
			// See the inserted value
			sql = "SELECT * FROM actor WHERE first_name = ?;"; 
			st = con.prepareStatement(sql);
			st.setString(1, "KENNETH");
			ResultSet resultSet = st.executeQuery();
			while(resultSet.next()) {
				System.out.println(resultSet.getString("first_name") + " - " + resultSet.getString("last_name"));
			}
					
			// Delete it
			sql = "DELETE FROM actor WHERE last_name = ?;";
			st = con.prepareStatement(sql);
			st.setString(1, "WONG");
			
			numRowsAffected = st.executeUpdate();
			
			// Make sure it is deleted
			sql = "SELECT * FROM actor WHERE last_name = ?;";
			st = con.prepareStatement(sql);
			st.setString(1, "WONG");
			resultSet = st.executeQuery();
			
			System.out.println("Delete complete");
			
			// See if there are any more WONG's
			while (resultSet.next()) {
				System.out.println(resultSet.getString("first_name") + " - " + resultSet.getString("last_name"));
			}
			
			st.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Close connection
		con.close();
	}

}
