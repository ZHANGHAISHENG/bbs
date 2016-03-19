package com.jluzh.util;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class DateDual {
  public static Timestamp adjustDate(HttpServletRequest request,String strQueryDate){  
	     String strDate=request.getParameter(strQueryDate);   

	     Timestamp date=null;
	     if(strDate!=null&&!strDate.trim().equals("")){
	   
	        if(strDate.matches("^\\s*\\d{4}-\\d{2}-\\d{2}\\s*$")){
	           strDate=strDate.trim()+" 00:00:00";//ע�⣺�м�ֻ��һ���ո�
	        }
	        if(strDate.matches("^\\s*\\d{4}-\\d{2}-\\d{2}\\s\\d{2}\\:\\d{2}\\s*$")){
	           strDate=strDate.trim()+":00";//ע�⣺�м�ֻ��һ���ո�
	        }
	          
	         date=Timestamp.valueOf(strDate); 
	    }
	     return date;
	  }
}
