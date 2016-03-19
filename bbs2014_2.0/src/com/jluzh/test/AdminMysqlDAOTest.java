package com.jluzh.test;

import java.sql.Timestamp;

//import org.junit.Test;

import com.jluzh.bean.Admin;
import com.jluzh.dao.mysql.AdminMysqlDAO;

public class AdminMysqlDAOTest {
	
   private AdminMysqlDAO dao=new AdminMysqlDAO();
   
   //@Test
   public void save(){
	   Admin admin=new Admin()
	   .setName("zhangSan").
       setPassword("123456").
       setPhone((long) 1234577095).
       setEmail("as@asdQQ.com").
       setRdate(new Timestamp(System.currentTimeMillis())).
       setLevel(0).
       set_desc("no");
	   
		dao.save(admin);        
		System.out.println("Success!");         
   }
}
