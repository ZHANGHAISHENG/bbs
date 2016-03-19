package com.jluzh.test;

import java.sql.Timestamp;

import com.jluzh.bean.User;
import com.jluzh.dao.mysql.UserMysqlDAO;


public class UserMysqlDAOTest {

   UserMysqlDAO   dao=new UserMysqlDAO();
   
   //@Test
   public void loadById(){
	   User u=dao.loadById(4);
	   p(u);
	   
	   
   }
   
   
   //@Test
   public void save(){

	   User u=new User()
	              .setName("zhangSan").
	              setPassword("123456").
	              setPhone((long) 00000000000010234).
	              setEmail("as@asdQQ.com").
	              setRdate(new Timestamp(System.currentTimeMillis())).
	              set_desc("no");
	   dao.save(u);        
	   System.out.println("Success!");         

   }
   
   public void p(User u){
	   if(u==null){
		   System.out.println("²»´æÔÚ£¡");
	   }else{
	     System.out.println("id:"+u.getId()+"  name:"+u.getName()+"  password:"+u.getPassword()+"  email:"+u.getEmail()+"  phone:"+u.getPhone()+"   _desc£º"+u.get_desc());
       
	   }
   }
   
   
   
}
