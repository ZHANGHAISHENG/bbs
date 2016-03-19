package com.jluzh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class EncodingFilter implements Filter {
	private String ENCODING;


	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		ENCODING=filterConfig.getInitParameter("ENCODING");

	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request2 = (HttpServletRequest) request;
        System.out.println(request2.getMethod());
		 if(request2.getMethod().equals("GET")){
			 request2=new EncodingWrapper(request2, ENCODING);
		 }else{
			  request2.setCharacterEncoding(ENCODING);
		 }
		 
		 chain.doFilter(request2, response);
	}


	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	


}
