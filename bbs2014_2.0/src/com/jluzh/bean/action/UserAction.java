package com.jluzh.bean.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jluzh.bean.User;
import com.jluzh.service.UserService;

public class UserAction extends HttpServlet {
    private UserService service=UserService.getInstance();
    HttpSession session=null;
	public UserAction() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=GB18030");
		request.setCharacterEncoding("GB18030");
		session=request.getSession();
		String action=request.getParameter("action");
		
		//登陆
		if(action!=null&&action.trim().equals("login")){
			int  id=Integer.parseInt(request.getParameter("id"));
			String password=request.getParameter("password");
		    User user=service.checkForLogin(id, password);
		    if(user!=null){
		    	 session.setAttribute("Img", "user"+id+".jpg");
		    }
		    session.setAttribute("loginReturn", "loginReturn");
		    session.setAttribute("user", user);
		    //清除Action参数以免在ArticleAction中产生干扰
             
		    getServletContext().getRequestDispatcher("/ArticleAction?action=userLogin").forward(request, response);
		    return ;
		}
		
		//注册
		if(action!=null&&action.trim().equals("register")){
		   User user=new User();
		   String name=request.getParameter("name");
		   String sex=request.getParameter("sex");
		   String password=request.getParameter("password");
		   String email=request.getParameter("email");
		   String phoneStr=request.getParameter("phone");
		   long phone=0;
		   if(phoneStr.trim()!=""){
			   phone=Long.parseLong(phoneStr) ;
		   }
		   String _desc=request.getParameter("_desc");
		   user.setName(name).setSex(sex).setPassword(password).setEmail(email).setPhone(phone).setRdate(new Timestamp(System.currentTimeMillis())).set_desc(_desc);
		   
		   int rowId=service.save(user);
		   request.setAttribute("rowId", rowId);
		   getServletContext().getRequestDispatcher("/user/userRegisterIsSuccess.jsp").forward(request, response);
		   return ;
		}
		
		//信息修改
		if(action!=null&&action.trim().equals("modify")){
		   User user=(User) session.getAttribute("user");
		   String name=request.getParameter("name");
		   String sex=request.getParameter("sex");
		   String password=request.getParameter("password");
		   String email=request.getParameter("email");
		   String phoneStr=request.getParameter("phone");
		   long phone=0;
		   if(phoneStr.trim()!=""){
			   phone=Long.parseLong(phoneStr) ;
		   }
		   String _desc=request.getParameter("_desc");
		   
           user.setName(name).setSex(sex).setPassword(password).setEmail(email).setPhone(phone).set_desc(_desc);
		   boolean isSuccess=service.modify(user);
		   request.setAttribute("isSuccess", isSuccess);
		   getServletContext().getRequestDispatcher("/user/modifyIsSuccess.jsp").forward(request, response);
		   return ;

		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    doGet(request, response);
	}

}
