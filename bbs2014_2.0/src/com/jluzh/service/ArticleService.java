package com.jluzh.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


import com.jluzh.bean.Article;

import com.jluzh.dao.ArticleDAO;
import com.jluzh.dao.mysql.ArticleMysqlDAO;

import com.sun.jmx.snmp.Timestamp;


public class ArticleService {
	   private static  ArticleService articleService=null;
	   private static ArticleDAO dao=null;
	   static{
		   if(articleService==null){
			   articleService=new ArticleService();
			   dao=new ArticleMysqlDAO();
		   }
	   }
	   
	   private ArticleService(){
		   
	   }
	   
	   public static ArticleService getInstance(){
		   return articleService;
	   }
	   
	   public  Article loadById(int id){ 
			return dao.loadById(id);
		}
		
		public  boolean save(Article article){
			return dao.save(article);
		}
		
		public  boolean  deleteById(int id){
			return dao.deleteById(id);
		}
		

		public void deleteUserWithIdByAdmin( int id,Connection conn) throws SQLException {
			dao.deleteUserWithIdByAdmin(id, conn);
			
		}
	    

		
		public  boolean modify(Article article){
			return dao.modify(article);
		}
		
		public int getRoot(ArrayList<Article> articles,int pageNo,int pageSize){
			return dao.getRoot(articles, pageNo, pageSize);
		}
		
		public int getRootByUserId(ArrayList<Article> articles,int userId,int pageNo,int pageSize){
			return dao.getRootByUserId(articles, userId, pageNo, pageSize);
		
		}
		
		public int getChildsByRootId( ArrayList<Article> articles,int rootId,int pageNo,int pageSize){
			   ArrayList<Article> articlesSorted=new ArrayList<Article>();
			   int pageCount=dao.getChildsByRootId(articlesSorted, rootId, pageNo, pageSize);
			   //��װ�����ӵ�������������,ʹ��չ�ֳ���״�ṹ
			   for(int i=articlesSorted.size()-1;i>=0;i--){

				   if(!articles.contains(articlesSorted.get(i))){
					   sortNodesBecomeTree(articles,articlesSorted,i);//��ѭ����ӵ��ӽڵ��ھɵ��ӽڵ�ǰ��,����ӵĸ��ڵ��ھɵĸ��ڵ�ĺ���
				   }

			   }
		       
			   return pageCount;
		}
		
		
		public void sortNodesBecomeTree(ArrayList<Article> articles,ArrayList<Article> articlesSorted,int node){
			//�����ڵ���ӵ�articles
			   Article article=articlesSorted.get(node);
			   articles.add(article);
			//��0��nodeλ�ö�articles������û���ӽڵ�
			   for(int i=0;i<node;i++){
				   
				   if(articlesSorted.get(i).getPid()==article.getId()){
					   if(!articlesSorted.get(i).isIsleaf()){//����ӽڵ��Ƿ�Ҷ�ӽڵ��ִ�еݹ�
						   sortNodesBecomeTree(articles, articlesSorted, i);
					   }else{
						   articles.add(articlesSorted.get(i));
					   }
				   }
			   }
		}
		
	    public int findRoot(ArrayList<Article> articles, String keyWord,int userId,Timestamp sPDate,Timestamp ePDate,int pageNo,int pageSize){
	         return dao.findRoot(articles, keyWord, userId, sPDate, ePDate, pageNo, pageSize);
	    }
	    
	    public int getRootByUserPost(ArrayList<Article> articles,int userId,int pageNo,int pageSize){
	         return dao.getRootByUserPost(articles, userId, pageNo, pageSize);
	    }

		public ArrayList<Article> getArticlesByUserId(int userId) throws SQLException {
			// TODO Auto-generated method stub
			return dao.getArticlesByUserId(userId);
		}

	    
	   
}
