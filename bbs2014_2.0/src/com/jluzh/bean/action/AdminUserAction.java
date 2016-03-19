package com.jluzh.bean.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.jluzh.bean.User;
import com.jluzh.service.UserService;
import com.jluzh.util.DateDual;

public class AdminUserAction extends HttpServlet {

	private UserService service=UserService.getInstance();
	private ArrayList<User> users=null;
	private int pageNo=1;
    private  int pageCount=0;
    private static final int pageSize=10;
    private static final int numSize=10;
    private HttpSession session=null;
    
	public AdminUserAction() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=GB18030");
		request.setCharacterEncoding("GB18030");
		session=request.getSession();
		users=new ArrayList<User>();
		String action=request.getParameter("action");
		

		
		//获取列表
		if(action!=null&&action.trim().equals("userList")){
		   request.setAttribute("tag", "normal"); 
		   pageCount=service.find(users, null, null, null, pageNo, pageSize);
		   System.out.println("******************pageCount="+pageCount);
		   setAll(request, response);
		   getServletContext().getRequestDispatcher("/admin/userList.jsp").forward(request, response);
	       return ;
		}
		//搜索

		if(action.trim().equals("search")){
			String keyWord=request.getParameter("keyWord");
			if(keyWord!=null&&keyWord.trim().equals("")){
				keyWord=null;
			}
			Timestamp sRDate=DateDual.adjustDate(request, "sRDate");
			Timestamp eRDate=DateDual.adjustDate(request, "eRDate");
			
			pageCount=service.find(users, keyWord, sRDate, eRDate, pageNo, pageSize);

						
			request.setAttribute("tag", "search"); 
			request.setAttribute("keyWord",keyWord); 
			request.setAttribute("sRDate",sRDate); 
			request.setAttribute("eRDate",eRDate); 
			setAll(request, response);
			getServletContext().getRequestDispatcher("/admin/userList.jsp").forward(request, response);
			return ;
		}
		
		//主页中翻页
		if(action!=null&&action.trim().equals("pageNoSelected")){
			String tag=request.getParameter("tag");
			//翻页越界处理
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			if(pageNo<1){
				pageNo=1;
			}
			if(pageNo>pageCount){
				pageNo=pageCount;
			}
			
			//判断当前翻页是否属于普通浏览而非搜索
			if(tag.trim().equals("normal")){
				request.setAttribute("tag", "normal"); 
				pageCount=service.find(users, null, null, null, pageNo, pageSize);
			}else{	
				String keyWord=request.getParameter("keyWord");
				if(keyWord!=null&&keyWord.trim().equals("")){
					keyWord=null;
				}
				Timestamp sRDate=DateDual.adjustDate(request, "sRDate");
				Timestamp eRDate=DateDual.adjustDate(request, "eRDate");
				pageCount=service.find(users, keyWord, sRDate, eRDate, pageNo, pageSize);
				request.setAttribute("tag", "search"); 
				request.setAttribute("keyWord",keyWord); 
				request.setAttribute("sRDate",sRDate); 
				request.setAttribute("eRDate",eRDate); 

			}
			
			setAll(request, response);
			getServletContext().getRequestDispatcher("/admin/userList.jsp").forward(request, response);
			return ;
		}
		
		//删除
		if(action!=null&&action.trim().equals("delete")){
			//获取并设置回到主页保留现场的参数
			String tag=request.getParameter("tag");
			String keyWord=request.getParameter("keyWord");
			pageNo=Integer.parseInt(request.getParameter("pageNo"));
			pageCount=Integer.parseInt(request.getParameter("pageCount"));
			Timestamp sRDate=DateDual.adjustDate(request, "sRDate");
			Timestamp eRDate=DateDual.adjustDate(request, "eRDate");
			
			request.setAttribute("tag", tag); 
			request.setAttribute("keyWord",keyWord); 
			request.setAttribute("sRDate",sRDate); 
			request.setAttribute("eRDate",eRDate); 
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("pageCount", pageCount);
			
			 int  id=Integer.parseInt(request.getParameter("id"));
			 boolean isSuccess=service.deleteById(id);

		     request.setAttribute("isSuccess", isSuccess);
		     getServletContext().getRequestDispatcher("/admin/userDeleteIsSuccess.jsp").forward(request, response);
		     return ;
		}
		

		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    doGet(request, response);
	}
	
	
	public void setAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("numSize", numSize);
		request.setAttribute("users",users );
	}
}
