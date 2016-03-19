package com.jluzh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB {
	
	private static final String url="jdbc:mysql://localhost:3306/bbs2014";
	private static final String userName="root";
	private static final String passWord="123456";
	static {//ʹ�þ�̬���������ÿ�ζ�Ҫ��������
		try {
			Class.forName("com.mysql.jdbc.Driver");//��������
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	DB(){}
	
	public static Connection getConn(){
		DataSource dataSource=null;
		Connection conn=null;
	
/*		try {			
			conn=DriverManager.getConnection(url,userName,passWord);
		//	conn.setCatalog("test");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		 try {
				Context context=new InitialContext();
				Context envContext=(Context) context.lookup("java:/comp/env");
				dataSource=(DataSource) envContext.lookup("jdbc/My");
				conn=dataSource.getConnection();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(SQLException e){
				e.printStackTrace();
			}
		return conn;
	}

	
	
	public static Statement getStmt(Connection conn){
		Statement stmt=null;
	
		try {
		    stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	public static Statement getStmt(Connection conn,int resultSetType, int resultSetConcurrency){
		Statement stmt=null;
	
		try {
		    stmt=conn.createStatement(resultSetType,resultSetConcurrency);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	

	
	
	public static PreparedStatement getPStmt(Connection conn,String sql){
		PreparedStatement pStmt=null;
	
		try {
			pStmt=conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pStmt;
	}
	
	
	public static PreparedStatement getPStmt(Connection conn,String sql,int autoGeneratedKeys){
		PreparedStatement pStmt=null;
	
		try {
			pStmt=conn.prepareStatement(sql,autoGeneratedKeys);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pStmt;
	}

	
	
	public static ResultSet excuteQuery(Statement stmt,String sql){
		ResultSet rs=null;
	
		try {
			rs=stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	public  static void excuteUpdate(Connection conn,String sql) throws SQLException{
                
				conn.createStatement().executeUpdate(sql);
                
	}
	
		public static ResultSet excuteQuery(Connection conn,String sql){
			ResultSet  rs=null;
		
			try {
				rs=conn.createStatement().executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rs;
		}

		
		public static void close(Connection conn) {
			if(conn!=null){
				try {
					conn.close();
					conn=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}
		
		public static void close(Statement stmt) {
			if(stmt!=null){
				try {
					stmt.close();
					stmt=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}
		
		
		public static void close(PreparedStatement pStmt) {
			if(pStmt!=null){
				try {
					pStmt.close();
					pStmt=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}
		public static void close(ResultSet rs) {
			if(rs!=null){
				try {
					rs.close();
					rs=null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}


}