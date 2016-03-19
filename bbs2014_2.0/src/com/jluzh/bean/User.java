package com.jluzh.bean;

import java.sql.Timestamp;

public class User {
   private int id;
   private String name;
   private String sex;
   private String password;
   private String email;
   private long phone; 
   private Timestamp rdate;
   private String _desc;
   
	public int getId() {
		return id;
	}
	public User setId(int id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public User setName(String name) {
		this.name = name;
		return this;
	}
	public String getSex() {
		return sex;
	}
	public User setSex(String sex) {
		this.sex = sex;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	public long getPhone() {
		return phone;
	}
	public User setPhone(long phone) {
		this.phone = phone;
		return this;
	}
	public Timestamp getRdate() {
		return rdate;
	}
	public User setRdate(Timestamp rdate) {
		this.rdate = rdate;
		return this;
	}
	public String get_desc() {
		return _desc;
	}
	public User set_desc(String _desc) {
		this._desc = _desc;
		return this;
	}
	   
   
   
}
