package com.movies.ott.testJDBC;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;


public class TestConnection {
	
	public static void main(String[] agrs) {
		String user="movie";
		String password="movie";
		String jdbcUrl="jdbc:mysql://localhost:3306/movie?useSSL=false";
		
		try {
			System.out.println("Connecting To Database: " + jdbcUrl);

			Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

			System.out.println("Connection Successful!: " + myConn);

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
