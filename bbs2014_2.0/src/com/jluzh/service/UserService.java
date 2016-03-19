package com.jluzh.service;


import java.sql.Timestamp;
import java.util.ArrayList;


import com.jluzh.bean.User;
import com.jluzh.dao.UserDAO;
import com.jluzh.dao.mysql.UserMysqlDAO;


public class UserService {
	   private static  UserService userService=null;
	   private static UserDAO dao=null;
	   static{
		   if(userService==null){
			   userService=new UserService();
			   dao=new UserMysqlDAO();
		   }
	   }
	   
	   private UserService(){
		   
	   }
	   
	   public static UserService getInstance(){
		   return userService;
	   }
	   
	   
	   public User checkForLogin(int id,String  password){
		   User user=loadById(id);
		   if(user==null){
			   return null;
		   }else{
			   if(!user.getPassword().equals(password)){
				   return null;
			   }
		   }
		   
		   return user;
	   }
	   
	   public User loadById(int id){
		   return dao.loadById(id);
	   }
	   
	  
		
		public  int save(User user){
			     return dao.save(user);
		}  
		
		public boolean deleteById(int id){
		   return dao.deleteById(id);
	    }
		
		public boolean modify(User user){
		  return dao.modify(user);

  	    }
	    
	    public int find(ArrayList<User> users, String keyWord,Timestamp sRDate,Timestamp eRDate,int pageNo,int pageSize){
	    	return dao.find(users, keyWord, sRDate, eRDate, pageNo, pageSize);
	    }
}


