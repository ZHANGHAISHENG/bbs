package com.jluzh.service;


import java.sql.Timestamp;
import java.util.ArrayList;


import com.jluzh.bean.Admin;
import com.jluzh.bean.User;

import com.jluzh.dao.AdminDAO;
import com.jluzh.dao.mysql.AdminMysqlDAO;



public class AdminService {
	
   private static  AdminService adminService=null;
   private static AdminDAO dao=null;
   static{
	   if(adminService==null){
		   adminService=new AdminService();
		   dao=new AdminMysqlDAO();
	   }
   }
   
     private AdminService(){
	   
    }
   
     public static AdminService getInstance(){
	   return adminService;
     }
     
     public Admin checkForLogin(int id,String  password){
    	   Admin admin=loadById(id);
		   if(admin==null){
			   return null;
		   }else{
			   if(!admin.getPassword().equals(password)){
				   return null;
			   }
		   }
		   
		   return admin;
	   }
   
   
    public Admin loadById(int id){
	   return dao.loadById(id);
	}
  
    public  int save( Admin admin){
	  return dao.save(admin);
    }  
 
	public boolean deleteById(int id){
	  return dao.deleteById(id);
    }
	
	public boolean modify(Admin admin){
		return dao.modify(admin);
	}
	
	
	
	public ArrayList<Admin> list(){
		return dao.list();
	}
	
	 public int find(ArrayList<Admin> admins, String keyWord,Timestamp sRDate,Timestamp eRDate,int pageNo,int pageSize){
	     return dao.find(admins, keyWord, sRDate, eRDate, pageNo, pageSize);
	 }

}
