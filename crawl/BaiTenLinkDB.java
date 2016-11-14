package com.crawl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class BaiTenLinkDB {

	private DBUtil manager;
	private Statement stmt;
	private PreparedStatement pstmt;
    
    private Patent patent;
    
	 public boolean saveToDB(Patent patent) 
	    {
	        boolean flag = true;
	        String sql = "insert into tb_patent(image,name,type,publicNumber,lw,applicant,applyDate,IPC,introduce)" +
	        		" values(?,?,?,?,?,?,?,?,?)";
	        manager = new DBUtil();
	       
	        try 
	        {
	        	//和数据库建立连接
	            pstmt = manager.getConnection().prepareStatement(sql);
	            
	            //从JavaBean中获取字段，写入数据库中
	            pstmt.setString(1, patent.getImage());
	            pstmt.setString(2, patent.getName());
	            pstmt.setString(3, patent.getType());
	            //System.out.println(patent.getcontent());
	            pstmt.setString(4, patent.getPublicNumber());
	            pstmt.setString(5, patent.getLw());
	            pstmt.setString(6, patent.getApplicant());
	            pstmt.setString(7, patent.getApplyDate());
	            pstmt.setString(8, patent.getIPC());
	            pstmt.setString(9, patent.getIntroduce());
	            
	            
	            flag = pstmt.execute();
	            
	        } 
	        catch (SQLException ex) 
	        {
	            ex.printStackTrace();
	        }
	        finally 
	        {
	            try 
	            {
	                pstmt.close();
	                manager.close();
	            } 
	            catch (SQLException ex) 
	            {
	                ex.printStackTrace();
	            }

	        }
	        return flag;
	    }
	
	 
    

}
