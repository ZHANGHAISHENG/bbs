package com.jluzh.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jluzh.bean.User;


public class UserURLFilter implements Filter {
  
	private HttpServletRequest request2;
	private HttpServletResponse response2;
   

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		request2=(HttpServletRequest)request;
		response2=(HttpServletResponse) response;
		HttpSession session=request2.getSession();
	    User user=(User) session.getAttribute("user");

		if(user!=null){
			chain.doFilter(request, response);
		}else{
			
			session.setAttribute("err", "Äú»¹Î´µÇÂ½");
			response2.sendRedirect("/bbs2014_2.0/ArticleAction"); 
		}
		
	
	}


	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	


}
