package server;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.Driver;

public class MySQLDriver {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";
	static final String DB_NAME = "USERS";
	
	//  Database credentials
	private String mySQLuser;
	private String mySQLpass;
   
	private Connection con;
	private final static String selectUser = "SELECT * FROM REGISTRATION WHERE NAME=?";
	private final static String addUser = "INSERT INTO Registration(USERNAME,PASSWORD,NAME) VALUES(?,?,?)";
	
	public MySQLDriver() {
		Scanner in = null;
		try {
			new Driver();
			Class.forName(JDBC_DRIVER);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		}
		finally {
			in = new Scanner(System.in);
			System.out.println("Enter MySQL username for local host: ");
			mySQLuser = in.nextLine();
			System.out.println("Enter MySQL password for local host: ");
			mySQLpass = in.nextLine();
			if (mySQLpass.equals("")) mySQLpass = null;
			in.close();
		}
	}
	
	//connect to MySQL
	public void connect(){	
		try {
			con = DriverManager.getConnection(DB_URL, mySQLuser, mySQLpass);
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	//connect to MySQL database
	public void connectToDB(){	
		try {
			con = DriverManager.getConnection(DB_URL+DB_NAME, mySQLuser, mySQLpass);
		} catch (SQLException e) {
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
		connect();
		Statement stmt = null;
	   try{
		   //Execute a query
		   stmt = con.createStatement();
		   
		   String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
		   
		   stmt.executeUpdate(sql);
	   	} catch(SQLException se) {
	   		se.printStackTrace();
	   	} catch(Exception e) {
	   		e.printStackTrace();
	   	} finally {
	   		//finally block used to close resources
	   		try{
	   			if (stmt!=null) {
	   				stmt.close();
	   			}
	      } catch(SQLException se2) { }// nothing we can do
	   		stop();
	   }
	}
	
	//create table
	public void createTable() {
		connectToDB();
		Statement stmt = null;
		try{
			//Execute a query
		    stmt = con.createStatement();
		    
		    String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION " +
	                   "(username VARCHAR(45) not NULL, " +
	                   " password VARCHAR(45), " + 
	                   " name VARCHAR(45), " +
	                   " PRIMARY KEY ( username ))"; 
		    
		    stmt.executeUpdate(sql);
	   	} catch(SQLException se) {
	   		se.printStackTrace();
	   	} catch(Exception e) {
	   		e.printStackTrace();
	   	} finally {
	      //finally block used to close resources
	      try{
	    	  if (stmt!=null) {
	    		  stmt.close();
	    	  }
	      } catch(SQLException se2) { }// nothing we can do
	      stop();
	   }
	}
	
	//Check if database exists
	public boolean checkDBExists() {
	    try{
	        ResultSet resultSet = con.getMetaData().getCatalogs();

	        while (resultSet.next()) {
	        	String databaseName = resultSet.getString(1);
	        	if(databaseName.equals(DB_NAME)) {
	                return true;
	            }
	        }
	        resultSet.close();
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	// Determine if user exists
	public boolean doesExist(String username) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUser);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		} catch (SQLException e) {e.printStackTrace();}
	}
	
}
