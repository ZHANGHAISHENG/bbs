package com.jluzh.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import com.jluzh.bean.Admin;
import com.jluzh.dao.AdminDAO;
import com.jluzh.util.DB;
import com.mysql.jdbc.Statement;


public class AdminMysqlDAO implements AdminDAO{
	
    public Admin loadById(int id){
    	Connection conn=null;
		String sql=null;
		ResultSet rs=null;
		Admin admin=null;
	    
		 try {
			 sql="select * from _admin where id="+id;
			 conn=DB.getConn();
			 rs=DB.excuteQuery(conn, sql);
			 if(rs.next()){ 
				  admin=new Admin();
				  admin.setId(rs.getInt("id")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              setLevel(rs.getInt("level")).
	              set_desc(rs.getString("_desc"));
			 }
             return admin;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			
			DB.close(rs);
			DB.close(conn);
		}
	}
   
	 public  int save( Admin admin){
		    Connection conn=null;
			PreparedStatement pStmt=null;
			int rowId=0;
			String sql="insert into _admin values(null,?,?,?,?,?,?,?,?)";
			try {
				  conn=DB.getConn();
				  conn.setAutoCommit(false);
				  pStmt=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );
				  pStmt.setString(1,admin.getName());
				  pStmt.setString(2, admin.getSex());			  
				  pStmt.setString(3,admin.getPassword());
				  pStmt.setString(4,admin.getEmail());
				  pStmt.setLong(5,admin.getPhone());
				  pStmt.setTimestamp(6,admin.getRdate());
				  pStmt.setInt(7, admin.getLevel());
				  pStmt.setString(8,admin.get_desc());

				  pStmt.executeUpdate();
                  ResultSet rsKey=pStmt.getGeneratedKeys();
				  
				  if(rsKey.next()){
					  rowId=rsKey.getInt(1);
				  }
				  conn.commit();
				  return rowId;

				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					conn.rollback();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return 0;
			}finally{
				DB.close(pStmt);
				DB.close(conn);
			}
	   }  
	 
		public boolean deleteById(int id){
		    Connection conn=null;
			String sql="delete from  _admin where id="+id;
			try {
				  conn=DB.getConn();
				  DB.excuteUpdate(conn, sql);
				  return true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}finally{
				DB.close(conn);
				
			}
	}
		
		public boolean modify(Admin admin){
			    Connection conn=null;
				PreparedStatement pStmt=null;
				String sql="update _admin set name=?,sex=?,password=?,email=?" +
						",phone=?,rdate=?,level=?,_desc=? where id="+admin.getId();
				
				try {
					  conn=DB.getConn();
					  pStmt=DB.getPStmt(conn, sql);
					  pStmt.setString(1,admin.getName());
					  pStmt.setString(2, admin.getSex());			  
					  pStmt.setString(3,admin.getPassword());
					  pStmt.setString(4,admin.getEmail());
					  pStmt.setLong(5,admin.getPhone());
					  pStmt.setTimestamp(6,admin.getRdate());
					  pStmt.setInt(7, admin.getLevel());
					  pStmt.setString(8,admin.get_desc());  
					  pStmt.executeUpdate();
					  return true;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}finally{
					DB.close(pStmt);
					DB.close(conn);
					
				}

		}
		
		
		
		public ArrayList<Admin> list(){
			Connection conn=null;
			String sql=null;
			ResultSet rs=null;
			ArrayList<Admin> admins=new ArrayList<Admin>();
			 try {
				 conn=DB.getConn();
				 
				 sql="select  * from _admin";

				 //获取查询的结果

				 rs=DB.excuteQuery(conn, sql);
				 while(rs.next()){
				   Admin admin=new Admin();
				   admin.setId(rs.getInt("id")).
						   setName(rs.getString("name")).
						   setPassword(rs.getString("password")).
						   setEmail(rs.getString("email")).
						   setPhone(rs.getLong("phone")).
						   setRdate(rs.getTimestamp("rdate")).
						   setLevel(rs.getInt("levle")).
						   set_desc(rs.getString("_desc"));	
				   admins.add(admin);
				 }
				 
				 return admins;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return admins; 
			}finally{	
				DB.close(rs);
				DB.close(conn);
			}
		}
		
		 public int find(ArrayList<Admin> admins, String keyWord,Timestamp sRDate,Timestamp eRDate,int pageNo,int pageSize){
		    	Connection conn=null;
				String sql=null;
				String sqlCount=null;
				ResultSet rs=null;
				ResultSet rsCount=null;
		        int pageCount;
				 try {
					 conn=DB.getConn();
					 
					 sql="select  * from _admin where 1=1 ";
					 
					 if(keyWord!=null){
						 sql+=" and (name like '%"+keyWord+
												 "%'  or sex like  '%"+keyWord+
												 "%'  or password like '%"+keyWord+
												 "%'  or email like  '%"+keyWord+
												 "%'  or phone like  '%"+keyWord+
												 "%'  or _desc like  '%"+keyWord+
												 "%') ";
					 }
					 
				
					 if(sRDate!=null){
						 
						 sql+=" and (rdate>='"+new SimpleDateFormat("yyyy-MM-dd").format(sRDate)+"') ";
					 }
					 
				    if(eRDate!=null){
						 
						 sql+=" and (rdate<'"+new SimpleDateFormat("yyyy-MM-dd").format(eRDate)+"') ";
					 }

		             sqlCount=sql.replaceFirst("\\*","count(*)");
					 sql+=" order by rdate desc limit " +(pageNo-1)*pageSize+" ,"+pageSize;

					 //获取页面总数				
					
					 rsCount=DB.excuteQuery(conn, sqlCount);
					 rsCount.next();
					 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

					 //获取查询的结果

					 rs=DB.excuteQuery(conn, sql);
					 while(rs.next()){
						 Admin admin =new Admin();
						  admin.setId(rs.getInt("id")).
						  setName(rs.getString("name")).
						  setSex(rs.getString("sex")).
			              setPassword(rs.getString("password")).
			              setPhone(rs.getLong("phone")).
			              setEmail(rs.getString("email")).
			              setRdate(rs.getTimestamp("rdate")).
			              set_desc(rs.getString("_desc"));
						  
						admins.add(admin);
					 }
					 

		             
		             return pageCount;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 return 0;
				}finally{
					
					DB.close(rs);
					DB.close(rsCount);
					DB.close(conn);
				}
		    }

}
