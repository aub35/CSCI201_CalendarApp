package server;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.Driver;

public class MySQLDriver {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";
	
	//  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
	private Connection con;
	private final static String selectUser = "SELECT * FROM REGISTRATION WHERE NAME=?";
	private final static String addUser = "INSERT INTO Registration(USERNAME,PASSWORD,NAME) VALUES(?,?,?)";
	
	public MySQLDriver() {
		try {
			new Driver();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//connect with MySQL database
	public void connect(){
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}  catch(Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		}
	}
	
	//stop MySQL connection
	public void stop() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//create database
	public void createDatabase() {
	   Statement stmt = null;
	   try{
		   //Execute a query
		   System.out.println("Creating database...");
		   stmt = con.createStatement();
		   
		   String sql = "CREATE DATABASE USERS";
		   
		   stmt.executeUpdate(sql);
		   System.out.println("Database created successfully!");
	   	} catch(SQLException se) {
	   		se.printStackTrace();
	   	} catch(Exception e) {
	   		e.printStackTrace();
	   	} finally {
	   		//finally block used to close resources
	   		try{
	   			if(stmt!=null)
	   				stmt.close();
	      } catch(SQLException se2) { }// nothing we can do
	   }
	}
	
	//create table
	public void createTable() {
		Statement stmt = null;
		try{
			//Execute a query
		    System.out.println("Creating table in users database...");
		    stmt = con.createStatement();
		    
		    String sql = "CREATE TABLE REGISTRATION " +
	                   "(username VARCHAR(45) not NULL, " +
	                   " password VARCHAR(45), " + 
	                   " name VARCHAR(45), " +
	                   " PRIMARY KEY ( username ))"; 
		    
		    stmt.executeUpdate(sql);
		    System.out.println("Table created successfully!");
	   	} catch(SQLException se) {
	   		se.printStackTrace();
	   	} catch(Exception e) {
	   		e.printStackTrace();
	   	} finally {
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      } catch(SQLException se2) { }// nothing we can do
	   }
	}
	
	// Determine if user exists
	public boolean doesExist(String username) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUser);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				System.out.println(result.getString(1) + " exists in database");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(username + " not found in database");
		return false;
	}
	
	// Adding a user to the database
	public void add(String username, String password, String name) {
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, name);
			ps.executeUpdate();
			System.out.println("Adding " + username + " into table");
		} catch (SQLException e) {e.printStackTrace();}
	}
	
}
