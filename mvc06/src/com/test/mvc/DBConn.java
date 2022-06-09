/*===================================
	DBConn.java
===================================*/

package com.test.mvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	private static Connection dbConn;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if (dbConn==null)
		{
			String url = "jdbc:oracle:thin:@211.238.142.151:1521:xe";
			String user = "scott";
			String pwd = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			dbConn = DriverManager.getConnection(url, user, pwd);
		}
		
		return dbConn;
	}
	
	public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException
	{
		if (dbConn==null)
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			dbConn = DriverManager.getConnection(url, user, pwd);
		}
		
		return dbConn;
	}
	
	public static void close()
	{
		if (dbConn != null)
		{
			try
			{
				if (!dbConn.isClosed())
				{
					dbConn.close();
				}
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		dbConn = null;
	}
}
