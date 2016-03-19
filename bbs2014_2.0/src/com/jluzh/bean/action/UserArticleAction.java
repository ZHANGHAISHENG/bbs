package com.jluzh.bean.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jluzh.bean.Article;
import com.jluzh.bean.User;
import com.jluzh.service.ArticleService;
import com.sun.corba.se.spi.orbutil.fsm.Action;

public class UserArticleAction extends HttpServlet {

    private ArticleService service=ArticleService.getInstance();
    private int pageNo=1;
    private  int pageCount=0;
    private static final int pageSize=10;
    private static final int numSize=10;
    private ArrayList<Article> articles=null;
    private HttpSession session=null;

	public UserArticleAction() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GB18030");
		request.setCharacterEncoding("GB18030");
		session=request.getSession();
		String action=request.getParameter("action");
		articles=new ArrayList<Article>();
		
		
		//�ҵ�����
		if(action!=null&&action.trim().equals("myArticle")){ 
			User user=(User) session.getAttribute("user");

			pageCount=service.getRootByUserPost(articles, user.getId(), pageNo, pageSize);
			setAll(request, response);
			getServletContext().getRequestDispatcher("/user/index.jsp").forward(request, response);
         
		    return ;
			
		}
	
		//��ҳ�з�ҳ
		if(action!=null&&action.trim().equals("pageNoSelected")){
			User user=(User) session.getAttribute("user");
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			
			//��ҳԽ�紦��
			if(pageNo<1){
				pageNo=1;
			}
			if(pageNo>pageCount){
				pageNo=pageCount;
			}
			pageCount=service.getRootByUserPost(articles, user.getId(), pageNo, pageSize);
			setAll(request, response);
			getServletContext().getRequestDispatcher("/user/index.jsp").forward(request, response);
			return ;
		}
		//�޸ĵ�ʱ����Ҫload����
		if(action!=null&&action.trim().equals("loadWhenModify")){
			int id=Integer.parseInt(request.getParameter("id"));
			String tag=request.getParameter("tag");
			pageNo=Integer.parseInt(request.getParameter("pageNo"));//�����ص���ҳ��ʱ�ܹ�����ԭ����״̬
			pageCount=Integer.parseInt(request.getParameter("pageCount"));//�����ص���ҳ��ʱ�ܹ�����ԭ����״̬
			
			Article article=service.loadById(id);
			request.setAttribute("article", article);
			request.setAttribute("tag", tag);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageCount", pageCount);
			getServletContext().getRequestDispatcher("/user/articleModify.jsp").forward(request, response);
            return ;
		}

		//�޸�
		if(action!=null&&action.trim().equals("modify")){
			
	         int id=Integer.parseInt(request.getParameter("id")) ;
	         String tag=request.getParameter("tag");
	         pageNo=Integer.parseInt(request.getParameter("pageNo"));//�����ص���ҳ��ʱ�ܹ�����ԭ����״̬
		     pageCount=Integer.parseInt(request.getParameter("pageCount"));//�����ص���ҳ��ʱ�ܹ�����ԭ����״̬
	        
	         String title=request.getParameter("title");
	         String content=request.getParameter("content");
	       
	         Article article=new Article();
	         article.setId(id).
			 setTitle(title).
			 setContent(content);
			
	          
	         boolean isSuccess=service.modify(article);
		     request.setAttribute("isSuccess", isSuccess);
		     request.setAttribute("tag", tag);
		     request.setAttribute("pageNo", pageNo);
			 request.setAttribute("pageCount", pageCount);
		     getServletContext().getRequestDispatcher("/user/articleModifyIsSuccess.jsp").forward(request, response);
		   
		     return ;
		     
		}
		
		//ɾ��
		if(action!=null&&action.trim().equals("delete")){
	         int rootId=Integer.parseInt(request.getParameter("rootId")) ;
	         String tag=request.getParameter("tag");
	         pageNo=Integer.parseInt(request.getParameter("pageNo"));//�����ص���ҳ��ʱ�ܹ�����ԭ����״̬
		     pageCount=Integer.parseInt(request.getParameter("pageCount"));//�����ص���ҳ��ʱ�ܹ�����ԭ����״̬
	         
		     boolean isSuccess=service.deleteById(rootId);

		     request.setAttribute("isSuccess", isSuccess);
		     request.setAttribute("tag", tag);
		     request.setAttribute("pageNo", pageNo);
			 request.setAttribute("pageCount", pageCount);
		     getServletContext().getRequestDispatcher("/user/articleDeleteIsSuccess.jsp").forward(request, response);
		     return ;
		     
		}


		//���������ƽ��չ��
		if(action!=null&&action.trim().equals("detailFlat")){
			
			int rootId=Integer.parseInt(request.getParameter("rootId"));
		    pageCount=service.getChildsByRootId(articles, rootId, pageNo, pageSize);
	        request.setAttribute("rootId", rootId);
		    setAll(request, response);
			getServletContext().getRequestDispatcher("/user/articleDetailFlat.jsp").forward(request, response);

			return ;
		}
		
		//�Լ���������ƽ��չ���еķ�ҳ
		if(action!=null&&action.trim().equals("detailFlatPageNoSelected")){

			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			int rootId=Integer.parseInt(request.getParameter("rootId"));
			//��ҳԽ�紦��
			if(pageNo<1){
				pageNo=1;
			}
			if(pageNo>pageCount){
				pageNo=pageCount;
			}
	        request.setAttribute("rootId", rootId);
			pageCount=service.getChildsByRootId(articles, rootId, pageNo, pageSize);
		        
		    setAll(request, response);
			getServletContext().getRequestDispatcher("/user/articleDetailFlat.jsp").forward(request, response);
			return ;
		}
		
		//�����������(���ڱ��˷�����������)
		if(action!=null&&action.trim().equals("selfParticipation")){
			User user=(User) session.getAttribute("user");
			pageCount=service.getRootByUserId(articles, user.getId(), pageNo, pageSize);
			setAll(request, response);
			getServletContext().getRequestDispatcher("/user/selfParticipation.jsp").forward(request, response);
			return ;
		}
		
		
		//������������еķ�ҳ
		if(action!=null&&action.trim().equals("selfParticipationPageNoSelected")){
			User user=(User) session.getAttribute("user");
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			
			//��ҳԽ�紦��
			if(pageNo<1){
				pageNo=1;
			}
			if(pageNo>pageCount){
				pageNo=pageCount;
			}
			pageCount=service.getRootByUserId(articles, user.getId(), pageNo, pageSize);
			setAll(request, response);
			getServletContext().getRequestDispatcher("/user/selfParticipation.jsp").forward(request, response);
			return ;
		}

		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    doGet(request, response);
	}
	
	
	
	//��������չ�������������õ�session
	public void setAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("numSize", numSize);
		request.setAttribute("articles",articles );
		
	}
	


	
}
