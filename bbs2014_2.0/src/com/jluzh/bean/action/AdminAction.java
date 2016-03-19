package com.jluzh.bean.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.tools.jar.resources.jar;

import com.jluzh.bean.Admin;
import com.jluzh.bean.User;
import com.jluzh.service.AdminService;
import com.jluzh.util.DateDual;

public class AdminAction extends HttpServlet {

	private AdminService service=AdminService.getInstance();
	private ArrayList<Admin> admins=null;
	private int pageNo=1;
    private  int pageCount=0;
    private static final int pageSize=10;
    private static final int numSize=10;
    private HttpSession session=null;
    
	public AdminAction() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=GB18030");
		request.setCharacterEncoding("GB18030");
		session=request.getSession();
		admins=new ArrayList<Admin>();
		String action=request.getParameter("action");
		
		//登陆
		if(action!=null&&action.trim().equals("login")){
			int  id=Integer.parseInt(request.getParameter("id"));
			String password=request.getParameter("password");
		    Admin admin=service.checkForLogin(id, password);
		    
		    if(admin!=null){
		    	 session.setAttribute("Img", "admin"+id+".jpg");
		    }
		    
		    session.setAttribute("loginReturn", "loginReturn");
		    session.setAttribute("admin", admin);

		    getServletContext().getRequestDispatcher("/AdminArticleAction?action=adminLogin").forward(request, response);
		    return ;
		}
		
		//注册
		if(action!=null&&action.trim().equals("register")){
		   Admin admin=new Admin();
		   String name=request.getParameter("name");
		   String sex=request.getParameter("sex");
		   String password=request.getParameter("password");
		   int level=Integer.parseInt(request.getParameter("level"));
		   String email=request.getParameter("email");
		   String phoneStr=request.getParameter("phone");
		   long phone=0;
		   if(phoneStr.trim()!=""){
			   phone=Long.parseLong(phoneStr) ;
		   }
		   String _desc=request.getParameter("_desc");
		   admin.setName(name).setSex(sex).setPassword(password).setLevel(level).setEmail(email).setPhone(phone).setRdate(new Timestamp(System.currentTimeMillis())).set_desc(_desc);
		   
		   int  rowId=service.save(admin);
		   request.setAttribute("rowId", rowId);
		   getServletContext().getRequestDispatcher("/admin/adminRegisterIsSuccess.jsp").forward(request, response);
		   return ;
		}
		
		//管理员列表
		if(action!=null&&action.trim().equals("adminList")){
		   request.setAttribute("tag", "normal"); 
		   pageCount=service.find(admins, null, null, null, pageNo, pageSize);
		   setAll(request, response);
		   getServletContext().getRequestDispatcher("/admin/adminList.jsp").forward(request, response);
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
			
			pageCount=service.find(admins, keyWord, sRDate, eRDate, pageNo, pageSize);

						
			request.setAttribute("tag", "search"); 
			request.setAttribute("keyWord",keyWord); 
			request.setAttribute("sRDate",sRDate); 
			request.setAttribute("eRDate",eRDate); 
			setAll(request, response);
			getServletContext().getRequestDispatcher("/admin/adminList.jsp").forward(request, response);
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
				pageCount=service.find(admins, null, null, null, pageNo, pageSize);
			}else{	
				String keyWord=request.getParameter("keyWord");
				if(keyWord!=null&&keyWord.trim().equals("")){
					keyWord=null;
				}
				Timestamp sRDate=DateDual.adjustDate(request, "sRDate");
				Timestamp eRDate=DateDual.adjustDate(request, "eRDate");
				pageCount=service.find(admins, keyWord, sRDate, eRDate, pageNo, pageSize);
				
				request.setAttribute("tag", "search"); 
				request.setAttribute("keyWord",keyWord); 
				request.setAttribute("sRDate",sRDate); 
				request.setAttribute("eRDate",eRDate); 

			}
			
			setAll(request, response);
			getServletContext().getRequestDispatcher("/admin/adminList.jsp").forward(request, response);
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
		     getServletContext().getRequestDispatcher("/admin/adminDeleteIsSuccess.jsp").forward(request, response);
		     return ;
		}
		
		//个人信息修改
		if(action!=null&&action.trim().equals("modify")){
		   Admin admin=(Admin) session.getAttribute("admin");
		   String name=request.getParameter("name");
		   String password=request.getParameter("password");
		   int level=Integer.parseInt(request.getParameter("level"));
		   String email=request.getParameter("email");
		   String phoneStr=request.getParameter("phone");
		   long phone=0;
		   if(phoneStr.trim()!=""){
			   phone=Long.parseLong(phoneStr) ;
		   }
		   String _desc=request.getParameter("_desc");
		   
           admin.setName(name).setPassword(password).setLevel(level).setEmail(email).setPhone(phone).set_desc(_desc);
           
		   boolean isSuccess=service.modify(admin);
		   request.setAttribute("isSuccess", isSuccess);
		   getServletContext().getRequestDispatcher("/admin/modifyIsSuccess.jsp").forward(request, response);
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
		request.setAttribute("admins",admins );
	}
}
