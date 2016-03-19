package com.jluzh.dao;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jluzh.bean.Article;

import com.sun.jmx.snmp.Timestamp;



public interface ArticleDAO {
	public  Article loadById(int id);
	
	public  boolean save(Article article);
	
	public  boolean  deleteById(int id);
	
    public  void  deleteUserWithIdByAdmin(int id,Connection conn) throws SQLException ;
	
	public  void  deleteById(Connection conn,int id) throws SQLException;
	
	public  boolean modify(Article article);
	
	public int getRoot(ArrayList<Article> articles,int pageNo,int pageSize);
	
	public int getRootByUserPost(ArrayList<Article> articles,int userId,int pageNo,int pageSize);

	public int getRootByUserId(ArrayList<Article> articles,int userId,int pageNo,int pageSize);
	
	public int getChildsByRootId( ArrayList<Article> articles,int rootId,int pageNo,int pageSize);
	
    public int findRoot(ArrayList<Article> articles, String keyWord,int userId,Timestamp sPDate,Timestamp ePDate,int pageNo,int pageSize);

	public ArrayList<Article> getArticlesByUserId(int userId) throws SQLException;
}
