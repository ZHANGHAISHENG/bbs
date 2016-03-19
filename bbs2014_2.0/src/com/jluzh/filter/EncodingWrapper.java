package com.jluzh.filter;

import java.io.UnsupportedEncodingException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class EncodingWrapper extends HttpServletRequestWrapper{
	
	private String ENCODING;

	public EncodingWrapper(HttpServletRequest request,String ENCODING) {
		super(request); //必须调用父类构造器，传入HttpServeletRequest实例，否者没有意义
		this.ENCODING=ENCODING;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value=getRequest().getParameter(name);
/*		System.out.println("value="+value);
		System.out.println("ENCODING="+ENCODING);*/
		if(name!=null){
			try {
				if(value!=null){
					value=new String(value.getBytes("ISO-8859-1"), ENCODING);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      //  System.out.println("name="+name+",value="+value);

		return value;
		
	}
	
	

}
