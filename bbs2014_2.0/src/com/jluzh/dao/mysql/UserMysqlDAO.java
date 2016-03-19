package com.jluzh.dao.mysql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import com.jluzh.bean.Article;
import com.jluzh.bean.User;
import com.jluzh.dao.UserDAO;

import com.jluzh.service.ArticleService;
import com.jluzh.util.DB;
import com.mysql.jdbc.Statement;


public class UserMysqlDAO implements UserDAO{
	
	public User loadById(int id){//通过
		Connection conn=null;
		String sql=null;
		ResultSet rs=null;
		User user=null;
	    
		 try {
			 sql="select * from _user where id="+id;
			 conn=DB.getConn();
			 rs=DB.excuteQuery(conn, sql);
			 if(rs.next()){ 
				  user=new User();
				  user.setId(rs.getInt("id")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
			 }
             return user;
		}catch (Exception e) {
			e.printStackTrace();
			return user;
		}finally{
			
			DB.close(rs);
			DB.close(conn);
		}
	}
	
	public  int save(User user){//通过
		    Connection conn=null;
			PreparedStatement pStmt=null;
			int rowId=0;
			String sql="insert into _user values(null,?,?,?,?,?,?,?)";
			try {
				  conn=DB.getConn();
				  conn.setAutoCommit(false);
				  pStmt=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS );

				  pStmt.setString(1,user.getName());
				  pStmt.setString(2, user.getSex());			  
				  pStmt.setString(3,user.getPassword());
				  pStmt.setString(4,user.getEmail());
				  pStmt.setLong(5,user.getPhone());
				  pStmt.setTimestamp(6,user.getRdate());
				  pStmt.setString(7,user.get_desc());

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
	
	public boolean deleteById(int id){//未验证
	    Connection conn=null;
        ArrayList<Article> articles=new ArrayList<Article>();
		try {
			  conn=DB.getConn();
			  boolean b=conn.getAutoCommit();
			  conn.setAutoCommit(false);
			  //获取用户发的帖和回帖
			  
			  articles=ArticleService.getInstance().getArticlesByUserId(id);
			  
			  //删除用户的发帖或回帖
			  for(Article a:articles){
				  ArticleService.getInstance().deleteUserWithIdByAdmin(a.getId(),conn);
			  }
			  
			  ArticleService.getInstance().deleteById(id);
			  
			  //删除用户
			  DB.excuteUpdate(conn,"delete from  _user where id="+id);
			  conn.commit();
			  conn.setAutoCommit(b);
			  return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}finally{
			DB.close(conn);
			
		}
    }
	
	public boolean modify(User user){
	    Connection conn=null;
		PreparedStatement pStmt=null;
		String sql="update _user set name=?,sex=?,password=?,email=?," +
				"phone=?,rdate=?,_desc=? where id="+user.getId();
		try {
			  conn=DB.getConn();
			  pStmt=DB.getPStmt(conn, sql);
			  pStmt.setString(1,user.getName());
			  pStmt.setString(2, user.getSex());			  
			  pStmt.setString(3,user.getPassword());
			  pStmt.setString(4,user.getEmail());
			  pStmt.setLong(5,user.getPhone());
			  pStmt.setTimestamp(6,user.getRdate());
			  pStmt.setString(7,user.get_desc());  
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
    
    public int find(ArrayList<User> users, String keyWord,Timestamp sRDate,Timestamp eRDate,int pageNo,int pageSize){
    	Connection conn=null;
		String sql=null;
		String sqlCount=null;
		ResultSet rs=null;
		ResultSet rsCount=null;
        int pageCount;
		 try {
			 conn=DB.getConn();
			 
			 sql="select  * from _user where 1=1 ";
			 
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
				 
				 sql+=" and (rdate<='"+new SimpleDateFormat("yyyy-MM-dd").format(eRDate)+"') ";
			 }

             sqlCount=sql.replaceFirst("\\*","count(*)");
			 sql+=" limit " +(pageNo-1)*pageSize+" ,"+pageSize;

			 //获取页面总数				
			
			 rsCount=DB.excuteQuery(conn, sqlCount);
			 rsCount.next();
			 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

			 //获取查询的结果

			 rs=DB.excuteQuery(conn, sql);
			 while(rs.next()){
				 User user =new User();
				  user.setId(rs.getInt("id")).
				  setName(rs.getString("name")).
				  setSex(rs.getString("sex")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
				  
				users.add(user);
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
