package com.jluzh.bean;

import java.sql.Timestamp;



public class Article {
	private int id;
	private int pid;
	private int rootid;
	private User user;
	private String title;
	private String content;
	private Timestamp pdate;
	private boolean isleaf;
	private int grade;
	
	
	public Article(){

	}
	
	
	public int getId() {
		return id;
	}
	public Article setId(int id) {
		this.id = id;
		return this;
	}
	public int getPid() {
		return pid;
	}
	public Article setPid(int pid) {
		this.pid = pid;
		return this;
	}
	public int getRootid() {
		return rootid;
	}
	public Article setRootid(int rootid) {
		this.rootid = rootid;
		return this;
	}
	public User getUser() {
		return user;
	}

	public Article setUser(User user) {
		this.user = user;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public Article setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getContent() {
		return content;
	}
	public Article setContent(String content) {
		this.content = content;
		return this;
	}
	public Timestamp getPdate() {
		return pdate;
	}
	public Article setPdate(Timestamp pdate) {
		this.pdate = pdate;
		return this;
	}
	public boolean isIsleaf() {
		return isleaf;
	}
	public Article setIsleaf(boolean isleaf) {
		this.isleaf = isleaf;
		return this;
	}
	public int getGrade() {
		return grade;
	}
	public Article setGrade(int grade) {
		this.grade = grade;
		return this;
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		
		return pdate.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Article a=null;
		if(obj instanceof Article){
			a=(Article)obj;
			if(this.id==a.getId()){
				return true;
			}
		}
		return false;
	}
	
	
	

	
}
