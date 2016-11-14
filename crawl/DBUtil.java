package com.crawl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lowagie.text.xml.xmp.DublinCoreSchema;

public class DBUtil {

private Connection con = null;
	
	private boolean autoCommit = true;
	
	public DBUtil()
	{
		
	}
	
	/**
	 * 进行数据库的操作，并获取数据库连接的对象
	 * */
	public Connection getConnection()
	{
		try{
			
			String driverName = "com.mysql.jdbc.Driver";
			String username = "sa";
		    String password = "123456";
		    
		    String url = "jdbc:mysql://localhost:3306/patent?useUnicode=true&characterEncoding=UTF-8";
		    
		    Class.forName(driverName).newInstance();
		    
		    con = DriverManager.getConnection(url,username,password);
		    con.setAutoCommit(autoCommit);
		    
		}catch (SQLException ex) {
			// TODO: handle exception
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		catch (InstantiationException ex) {
			// TODO: handle exception
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		catch (IllegalAccessException ex) {
			// TODO: handle exception
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		catch (ClassNotFoundException ex) {
			// TODO: handle exception
			Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		System.out.println("连接成功");
		return con;
	}
	
	public void close()
	{
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				con = null;
			}
		}
	}
	
}
