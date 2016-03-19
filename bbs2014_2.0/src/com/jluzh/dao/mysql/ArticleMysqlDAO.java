package com.jluzh.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import com.jluzh.bean.Article;
import com.jluzh.bean.User;
import com.jluzh.dao.ArticleDAO;
import com.jluzh.service.UserService;
import com.jluzh.util.DB;
import com.sun.jmx.snmp.Timestamp;


public class ArticleMysqlDAO implements ArticleDAO{
	
	
	public  Article loadById(int id){ //通过
		Connection conn=null;
		Statement stmt=null;
		String sql=null;
		ResultSet rs=null;
		Article article=new Article();
	    
		 try {
			 sql="select a.*,u.id uid,u.name,u.sex,u.password,u.email,u.phone,u.rdate,u._desc from _article a,_user  u where a.userid=u.id and a.id="+id;
			 conn=DB.getConn();
			 stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 rs=DB.excuteQuery(stmt, sql);
			 if(rs.next()){
				  User user=new User();
				  user.setId(rs.getInt("uid")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
				 
				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
			 }
             return article;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			
			DB.close(rs);
			DB.close(conn);
		}
	}
	
	public  boolean save(Article article){//通过
		Connection conn=null;
		String sql=null;
		PreparedStatement pStmt=null;
		ResultSet rs=null;
		int isleaf;
		 try {
			  conn=DB.getConn();
			  sql="insert into _article values(null,?,?,?,?,?,?,?,?)";
			  pStmt=DB.getPStmt(conn, sql,Statement.RETURN_GENERATED_KEYS);
			  
			  boolean b=conn.getAutoCommit();
			  conn.setAutoCommit(false);
              
			  if(article.getPid()!=0){//判断是否往虚拟的父贴添加子贴
					  //获取父叶子节点grade
					  rs=DB.excuteQuery(conn,"select * from _article where id="+article.getPid());
					  rs.next();
					  isleaf=rs.getInt("isleaf");
					  	
					   //插入子贴
					  pStmt.setInt(1, article.getPid());
					  pStmt.setInt(2, article.getRootid());
					  pStmt.setInt(3, article.getUser().getId());
					  pStmt.setString(4,  article.getTitle());
					  pStmt.setString(5, article.getContent());
					  pStmt.setTimestamp(6, article.getPdate());
					  pStmt.setInt(7, 0);
					  pStmt.setInt(8, article.getGrade());
					  pStmt.executeUpdate();
					  //如果父贴是没有子贴设置isleaf=1
		                if(isleaf==0){
		                	DB.excuteUpdate(conn,"update _article set isleaf=1 where id="+article.getPid());
		                }
					    
               }else{
            	 //插入最新贴子
					  pStmt.setInt(1, 0);
					  pStmt.setInt(2, 1);//为了与上面的通配符统一，所以暂时为1
					  pStmt.setInt(3, article.getUser().getId());
					  pStmt.setString(4,  article.getTitle());
					  pStmt.setString(5, article.getContent());
					  pStmt.setTimestamp(6, article.getPdate());
					  pStmt.setInt(7, 0);  
					  pStmt.setInt(8, 1);
					  pStmt.executeUpdate();
					  
					  //更新rootid
					  ResultSet rsGenerKey=pStmt.getGeneratedKeys();
					  rsGenerKey.next();
					  int id=rsGenerKey.getInt(1);
					  rsGenerKey.close();
					  DB.excuteUpdate(conn, "update _article set rootid="+id+" where id="+id);
               }
			  
			 
			 conn.commit();
			 conn.setAutoCommit(b);
			 return true;
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}finally{
			DB.close(rs);
			DB.close(pStmt);
			DB.close(conn);			
		}
	}
	
	public  boolean  deleteById(int id){//通过
		
		Connection conn=null;
		String sql=null;
		ResultSet rs=null;
		Article article=new Article();
		 try {
			 conn=DB.getConn();
			 boolean b=conn.getAutoCommit();
			 conn.setAutoCommit(false);
			//删除自身目录以及所有子贴
			 deleteById(conn, id);
				
			//判断是否要将父贴变成叶子贴
			 article=loadById(id);
			
			 sql="select * from _article where pid="+article.getPid();
			 rs=DB.excuteQuery(conn, sql);
			 if(!rs.next()){
				 sql="update _article set isleaf=0 where  id="+article.getPid();
				 DB.excuteUpdate(conn, sql);
			 }
			 conn.commit();
			 conn.setAutoCommit(b);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}finally{
			
			DB.close(rs);
			DB.close(conn);
		}	
		
	}
	
  public  void  deleteUserWithIdByAdmin(int id,Connection conn) throws SQLException{
		String sql=null;
		ResultSet rs=null;
		Article article=new Article();

		//删除自身目录以及所有子贴
		 deleteById(conn, id);
			
		//判断是否要将父贴变成叶子贴
		 article=loadById(id);
		
		 sql="select * from _article where pid="+article.getPid();
		 rs=DB.excuteQuery(conn, sql);
		 if(!rs.next()){
			 sql="update _article set isleaf=0 where  id="+article.getPid();
			 DB.excuteUpdate(conn, sql);
		 }	
	}
	
	public  void  deleteById(Connection conn,int id) throws SQLException{//通过
		
		ResultSet rs=null;
		String sql="select * from _article where pid="+id;
		int leaf;
		int chilId;
		 try {
			 rs=DB.excuteQuery(conn, sql);
			 
			 while(rs.next()){
				   chilId=rs.getInt("id");
				   leaf=rs.getInt("isleaf");
			        if(leaf!=0){//非叶子节点就执行递归	
			        	deleteById(conn,chilId);
			        }else{
				        DB.excuteUpdate(conn,"delete from _article where id="+chilId); //删除子节点
			        }
			 }
			 DB.excuteUpdate(conn,"delete from _article where id="+id); //删除根节点
	
		}finally{
			DB.close(rs);
		}
	}
	
	public  boolean modify(Article article){
		Connection conn=null;
		String sql=null;
		 try {
			  conn=DB.getConn();
			  sql="update _article set title='"+article.getTitle()+"',  content='"+article.getContent()+"' where id="+article.getId();		  
			  System.out.println(sql);
			  DB.excuteUpdate(conn, sql);
			  return true; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			DB.close(conn);			
		}
	}
	
	public int getRoot(ArrayList<Article> articles,int pageNo,int pageSize){//通过
		Connection conn=null;
		Statement stmt=null;
		String sql=null;
		String sqlCount=null;
		ResultSet rs=null;
		ResultSet rsCount=null;
        int pageCount;
		 try {
			 conn=DB.getConn();
			 stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 
			 
			 sql="select  a.*,u.id uid,u.name,u.sex,u.password,u.email,u.phone,u.rdate,u._desc from _article a,_user  u where a.userid=u.id and a.grade=1 order by pdate desc";

             sqlCount=sql.replaceFirst("a\\.\\*,u\\.id uid,u\\.name,u\\.sex,u\\.password,u\\.email,u\\.phone,u\\.rdate,u\\._desc","count(*)");
			 sql+=" limit " +(pageNo-1)*pageSize+" ,"+pageSize;

			 //获取页面总数				
			
			 rsCount=DB.excuteQuery(conn, sqlCount);
			 rsCount.next();
			 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

			 //获取查询的结果

			 rs=stmt.executeQuery(sql);
			 while(rs.next()){
				 Article article =new Article();
				 
				 User user=new User();
				  user.setId(rs.getInt("uid")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
				 
				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
                articles.add(article);
			 }
			 

             
             return pageCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return 0;
		}finally{
			
			DB.close(rs);
			DB.close(rsCount);
			DB.close(stmt);
			DB.close(conn);
		}
	}
	
	
	public int getRootByUserPost(ArrayList<Article> articles,int userId,int pageNo,int pageSize){
		Connection conn=null;
		Statement stmt=null;
		String sql=null;
		String sqlCount=null;
		ResultSet rs=null;
		ResultSet rsCount=null;
        int pageCount;
		 try {
			 conn=DB.getConn();
			 stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 
			 
			 sql="SELECT DISTINCT a.*  FROM _article a WHERE a.userid="+userId +" AND a.pid=0 ORDER BY a.pdate DESC";

             sqlCount=sql.replaceFirst("a\\.\\*","count(*)");
			 sql+=" limit " +(pageNo-1)*pageSize+" ,"+pageSize;

			 //获取页面总数				
			
			 rsCount=DB.excuteQuery(conn, sqlCount);
			 rsCount.next();
			 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

			 //获取查询的结果

			 rs=stmt.executeQuery(sql);
			 while(rs.next()){
				 
				 Article article =new Article();
				 
				 User user=UserService.getInstance().loadById(userId);

				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
                articles.add(article);
			 }
			 

             
             return pageCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return 0;
		}finally{
			
			DB.close(rs);
			DB.close(rsCount);
			DB.close(stmt);
			DB.close(conn);
		}
	}
	
	
	
	public int getRootByUserId(ArrayList<Article> articles,int userId,int pageNo,int pageSize){
		Connection conn=null;
		Statement stmt=null;
		String sql=null;
		String sqlCount=null;
		ResultSet rs=null;
		ResultSet rsCount=null;
        int pageCount;
		 try {
			 conn=DB.getConn();
			 stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 
			 
			 sql="SELECT DISTINCT a2.*  FROM _article a1,_article a2 WHERE a1.userid="+userId +" AND a1.pid!=0 AND a1.rootId=a2.id ORDER BY a1.pdate DESC";

             sqlCount=sql.replaceFirst("a2\\.\\*","count(*)");
			 sql+=" limit " +(pageNo-1)*pageSize+" ,"+pageSize;

			 //获取页面总数				
			
			 rsCount=DB.excuteQuery(conn, sqlCount);
			 rsCount.next();
			 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

			 //获取查询的结果

			 rs=stmt.executeQuery(sql);
			 while(rs.next()){
				 
				 Article article =new Article();
				 
				 User user=UserService.getInstance().loadById(userId);

				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
                articles.add(article);
			 }
			 

             
             return pageCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return 0;
		}finally{
			
			DB.close(rs);
			DB.close(rsCount);
			DB.close(stmt);
			DB.close(conn);
		}
	}
	
	
	public int getChildsByRootId( ArrayList<Article> articles,int rootId,int pageNo,int pageSize){//通过
		Connection conn=null;
		Statement stmt=null;
		String sql=null;
		String sqlCount=null;
		ResultSet rs=null;
		ResultSet rsCount=null;
        int pageCount;
		 try {
			 conn=DB.getConn();
			 stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 
			 
			 sql="select  a.*,u.id uid,u.name,u.sex,u.password,u.email,u.phone,u.rdate,u._desc from _article a,_user  u where a.userid=u.id  and rootid="+rootId+"  order by pdate desc";

             sqlCount=sql.replaceFirst("a\\.\\*,u\\.id uid,u\\.name,u\\.sex,u\\.password,u\\.email,u\\.phone,u\\.rdate,u\\._desc","count(*)");
            /* sql+=" limit " +(pageNo-1)*pageSize+" ,"+pageSize;*/
			 //获取页面总数				
			
			 rsCount=DB.excuteQuery(conn, sqlCount);
			 rsCount.next();
			 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

			 //获取查询的结果

			 rs=stmt.executeQuery(sql);
			 while(rs.next()){
                 Article article =new Article();
				 User user=new User();
				  user.setId(rs.getInt("uid")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
				 
				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
                articles.add(article);
			 }
			 

             
             return pageCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return 0;
		}finally{
			
			DB.close(rs);
			DB.close(rsCount);
			DB.close(stmt);
			DB.close(conn);
		}
	}
	
    public int findRoot(ArrayList<Article> articles, String keyWord,int userId,Timestamp sPDate,Timestamp ePDate,int pageNo,int pageSize){//通过
    	Connection conn=null;
    	Statement stmt=null;
		String sql=null;
		String sqlCount=null;
		ResultSet rs=null;
		ResultSet rsCount=null;
        int pageCount;
		 try {
			 conn=DB.getConn();
			 
            stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 
			 
			 sql="select  a.*,u.id uid,u.name,u.sex,u.password,u.email,u.phone,u.rdate,u._desc from _article a,_user  u where a.userid=u.id  and a.pid=0 ";

			 
			 if(keyWord!=null){
				 sql+=" and (title like '%"+keyWord+"%'  or content like  '%"+keyWord+"%') ";
			 }
			 
			 if(userId!=0){
				 sql+=" and (a.userid ="+userId+") ";
			 }
			 
			 if(sPDate!=null){
				 
				 sql+=" and (pdate>='"+new SimpleDateFormat("yyyy-MM-dd").format(sPDate)+"') ";
			 }
			 
		    if(ePDate!=null){
				 
				 sql+=" and (pdate<='"+new SimpleDateFormat("yyyy-MM-dd").format(ePDate)+"') ";
			 }

             sqlCount=sql.replaceFirst("a\\.\\*,u\\.id uid,u\\.name,u\\.sex,u\\.password,u\\.email,u\\.phone,u\\.rdate,u\\._desc","count(*)");
			 sql+="   order by a.pdate desc  limit " +(pageNo-1)*pageSize+" ,"+pageSize;
			 //获取页面总数				
			
			 rsCount=DB.excuteQuery(conn, sqlCount);
			 rsCount.next();
			 pageCount=(rsCount.getInt(1)+pageSize-1)/pageSize;

			 //获取查询的结果

			 rs=stmt.executeQuery(sql);
			 while(rs.next()){
                 Article article =new Article();
				 User user=new User();
				  user.setId(rs.getInt("uid")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
				 
				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
                articles.add(article);
			 }
			 

             
             return pageCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return 0;
		}finally{
			
			DB.close(rs);
			DB.close(rsCount);
			DB.close(stmt);
			DB.close(conn);
		}
    }

	public ArrayList<Article> getArticlesByUserId(int userId) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn=null;
		Statement stmt=null;
		String sql=null;
		ResultSet rs=null;
		ArrayList<Article> articles=new ArrayList<Article>();
		 try {
			 conn=DB.getConn();
			 stmt=DB.getStmt(conn, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 
			 
			 sql="select  a.*,u.id uid,u.name,u.sex,u.password,u.email,u.phone,u.rdate,u._desc from _article a,_user  u where a.userid=u.id and a.grade=1 and u.id="+userId+" order by pdate desc";

			 //获取查询的结果

			 rs=stmt.executeQuery(sql);
			 while(rs.next()){
				 Article article =new Article();
				 
				 User user=new User();
				  user.setId(rs.getInt("uid")).
				  setName(rs.getString("name")).
	              setPassword(rs.getString("password")).
	              setPhone(rs.getLong("phone")).
	              setEmail(rs.getString("email")).
	              setRdate(rs.getTimestamp("rdate")).
	              set_desc(rs.getString("_desc"));
				 
				  article.setId(rs.getInt("id")).
					setPid(rs.getInt("pid")).
					setRootid(rs.getInt("rootid")).
					setUser(user).
					setTitle(rs.getString("title")).
					setContent(rs.getString("content")).
					setPdate(rs.getTimestamp("pdate")).
					setIsleaf(rs.getInt("isleaf")==0? true:false).
					setGrade(rs.getInt("grade")); 
                articles.add(article);
			 }
		} finally{
			DB.close(rs);
			DB.close(stmt);
			DB.close(conn);
		}
		return articles;
	}
	
	
	
}
