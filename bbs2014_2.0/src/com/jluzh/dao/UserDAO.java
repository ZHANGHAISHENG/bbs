package com.jluzh.dao;


import java.sql.Timestamp;

import java.util.ArrayList;

import com.jluzh.bean.User;


public interface UserDAO {
	public User loadById(int id);
	
	public  int save(User user);
	public boolean deleteById(int id);
	
	public boolean modify(User user);
    
    public int find(ArrayList<User> users, String keyWord,Timestamp sRDate,Timestamp eRDate,int pageNo,int pageSize);
}
