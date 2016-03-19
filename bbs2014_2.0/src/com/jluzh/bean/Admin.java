package com.jluzh.bean;

import java.sql.Timestamp;

public class Admin {
	 private int id;
	   private String name;
	   private String sex;
	   private String password;
	   private String email;
	   private Long phone;
	   private Timestamp rdate;
	   private int level;
       private String _desc;
	   
		public int getId() {
			return id;
		}
		public Admin setId(int id) {
			this.id = id;
			return this;
		}
		public String getName() {
			return name;
		}
		public Admin setName(String name) {
			this.name = name;
			return this;
		}
		public String getSex() {
			return sex;
		}
		public Admin setSex(String sex) {
			this.sex = sex;
			return this;
		}
		public String getPassword() {
			return password;
		}
		public Admin setPassword(String password) {
			this.password = password;
			return this;
		}
		public String getEmail() {
			return email;
		}
		public Admin setEmail(String email) {
			this.email = email;
			return this;
		}
		public Long getPhone() {
			return phone;
		}
		public Admin setPhone(Long phone) {
			this.phone = phone;
			return this;
		}
		public Timestamp getRdate() {
			return rdate;
		}
		public Admin setRdate(Timestamp rdate) {
			this.rdate = rdate;
			return this;
		}
		public String get_desc() {
			return _desc;
		}
		public Admin set_desc(String _desc) {
			this._desc = _desc;
			return this;
		}
	    public int getLevel() {
			return level;
		}
		public Admin setLevel(int level) {
			this.level = level;
			return this;
		}
	   
	   
}
