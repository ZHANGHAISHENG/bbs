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

public class ArticleAction extends HttpServlet {

    private ArticleService service=ArticleService.getInstance();
    private int pageNo=1;
    private  int pageCount=0;
    
    private static final int pageSize=10;
    private static final int numSize=10;
    private ArrayList<Article> articles=null;
    private HttpSession session=null;

	public ArticleAction() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GB18030");
		request.setCharacterEncoding("GB18030");
		session=request.getSession();
		String action=request.getParameter("action");
		articles=new ArrayList<Article>();
		pageNo=1;
		pageCount=0;
		
		
		
		//�����б�չ��(�մ�ҳ��)
		if(action==null||action.trim().equals("userLogin")){ 
		
			if(session.getAttribute("err")!=null){
				request.setAttribute("err",session.getAttribute("err"));
				session.removeAttribute("err");
			}

			request.setAttribute("tag", "normal"); 
			pageCount=service.getRoot(articles, pageNo, pageSize);

			setAll(request, response);

			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		    return ;
			
		}
		
		//��ҳ�������
		if(action.trim().equals("search")){
			
			String keyWord=request.getParameter("keyWord");
			pageCount=service.findRoot(articles, keyWord, 0, null, null, pageNo, pageSize);
						
			request.setAttribute("tag", "search"); 
			request.setAttribute("keyWord",keyWord); 
			setAll(request, response);
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return ;
		}
		
		
		//��ҳ�з�ҳ
		if(action!=null&&action.trim().equals("pageNoSelected")){
			String tag=request.getParameter("tag");
			//��ҳԽ�紦��
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			if(pageNo<1){
				pageNo=1;
			}
			if(pageNo>pageCount){
				pageNo=pageCount;
			}

			//�жϵ�ǰ��ҳ�Ƿ�������ͨ�����������
			if(tag.trim().equals("normal")){
				pageCount=service.getRoot(articles, pageNo, pageSize);
				request.setAttribute("tag", "normal"); 
			}else{	
				String keyWord=request.getParameter("keyWord");
				pageCount=service.findRoot(articles, keyWord, 0, null, null, pageNo, pageSize);
				request.setAttribute("tag", "search"); 
				request.setAttribute("keyWord",keyWord); 
			}
			setAll(request, response);
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return ;
		}
		
		
		
		//���������ƽ��չ��
		if(action!=null&&action.trim().equals("detailFlat")){
			
			int rootId=Integer.parseInt(request.getParameter("rootId"));
			int indexPageNo=Integer.parseInt(request.getParameter("indexPageNo"));//ƽ��չ�ַ��ص���ҳʱ��
			int indexPageCount=Integer.parseInt(request.getParameter("indexPageCount"));//ƽ��չ�ַ��ص���ҳʱ��
			String tag=request.getParameter("tag");//ƽ��չ�ַ��ص���ҳʱ��
			String keyWord=request.getParameter("keyWord");//ƽ��չ�ַ��ص���ҳʱ��
			
			request.setAttribute("rootId",rootId);

		    pageCount=service.getChildsByRootId(articles, rootId, pageNo, pageSize); 
		    
		    request.setAttribute("indexPageNo",indexPageNo); 
		    request.setAttribute("indexPageCount",indexPageCount); 
		    request.setAttribute("tag",tag); 
		    request.setAttribute("keyWord",keyWord); 

		    setAll(request, response);
		   
			getServletContext().getRequestDispatcher("/articleDetailFlat.jsp").forward(request, response);
			return ;
		}
		
		//������ƽ��չ���з�ҳ(�����ٴη������ݿ�,�����ڷ���request��,���Ի�����Ҫ�������ݿ�)
		if(action!=null&&action.trim().equals("detailFlatPageNoSelected")){
			int indexPageNo=Integer.parseInt(request.getParameter("indexPageNo"));//ƽ��չ�ַ��ص���ҳʱ��
			int indexPageCount=Integer.parseInt(request.getParameter("indexPageCount"));//ƽ��չ�ַ��ص���ҳʱ��
			String tag=request.getParameter("tag");//ƽ��չ�ַ��ص���ҳʱ��
			String keyWord=request.getParameter("keyWord");//ƽ��չ�ַ��ص���ҳʱ��
			request.setAttribute("indexPageNo",indexPageNo); 
		    request.setAttribute("indexPageCount",indexPageCount); 
		    request.setAttribute("tag",tag); 
		    request.setAttribute("keyWord",keyWord);  
			    
			int rootId=Integer.parseInt(request.getParameter("rootId"));
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			if(pageNo<1){
				pageNo=1;
			}
			if(pageNo>pageCount){
				pageNo=pageCount;
			}

			request.setAttribute("rootId",rootId);
			pageCount=service.getChildsByRootId(articles, rootId, pageNo, pageSize); 
			setAll(request, response);
			getServletContext().getRequestDispatcher("/articleDetailFlat.jsp").forward(request, response);
			return ;
		}


		//����
		if(action!=null&&action.trim().equals("post")){
	         Article article=new Article();
	         String title=request.getParameter("title");
	         String content=request.getParameter("content");
	         User user=(User) session.getAttribute("user");

	         article.setUser(user).setTitle(title).setContent(content).setPdate(new Timestamp(System.currentTimeMillis()));
		     boolean isSuccess=service.save(article);

		     request.setAttribute("isSuccess", isSuccess);
		     getServletContext().getRequestDispatcher("/user/postIsSuccess.jsp").forward(request, response);
		     return ;
		     
		}
		
		//����
		if(action!=null&&action.trim().equals("reply")){
	         Article article=new Article();
	         String title=request.getParameter("title");
	         String content=request.getParameter("content");
	         int pArticleId=Integer.parseInt(request.getParameter("pArticleId"));
	         
	         //��ȡ�ص���ҳ�����ֳ��Ĳ���,����ӵ�request��������
	         String tag=request.getParameter("tag");
	         String keyWord=request.getParameter("keyWord");
			 pageNo=Integer.parseInt(request.getParameter("pageNo"));
			 pageCount=Integer.parseInt(request.getParameter("pageCount"));
			 
			 request.setAttribute("tag", tag); 
			 request.setAttribute("keyWord",keyWord); 
			 request.setAttribute("pageNo",pageNo); 
			 request.setAttribute("pageCount",pageCount); 
	         
	         //��ȡ����
	         Article pArticle=service.loadById(pArticleId);
	         
	         //��ȡ�û�
	         User user=(User) session.getAttribute("user");
	         
             //���û����Ĳ���		    
	         article.setPid(pArticle.getId() ).
				setRootid(pArticle.getRootid()).
				setUser(user).
				setTitle(title).
				setContent(content).
				setPdate(new Timestamp(System.currentTimeMillis())).
				setIsleaf(true).
				setGrade(pArticle.getGrade()+1); 
	         
	         
	         
	         boolean isSuccess=service.save(article);

		     request.setAttribute("isSuccess", isSuccess);
		     getServletContext().getRequestDispatcher("/user/replyIsSuccess.jsp").forward(request, response);
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
