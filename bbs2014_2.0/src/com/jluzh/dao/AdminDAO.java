package com.jluzh.dao;

import java.sql.Timestamp;

import java.util.ArrayList;

import com.jluzh.bean.Admin;


public interface AdminDAO {
	public Admin loadById(int id);
	   
	public  int save( Admin admin);
	public boolean deleteById(int id);
	
	public boolean modify(Admin admin);

	public ArrayList<Admin> list();
	
    public int find(ArrayList<Admin> admins, String keyWord,Timestamp sRDate,Timestamp eRDate,int pageNo,int pageSize);

}
