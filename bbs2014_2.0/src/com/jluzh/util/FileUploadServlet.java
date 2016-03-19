package com.jluzh.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
    
	private String errLink;
	  // ��ȡ�ļ���
    String filename;
    public FileUploadServlet() {
    	 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		final long MAX_SIZE =10*1024*1024;// �����ϴ��ļ����ֵ   
        // �����ϴ����ļ���ʽ���б�   
        final String[] allowedExt = new String[] {"jpg", "jpeg", "gif"};   
        response.setContentType("text/html");   
        // �����ַ�����ΪUTF-8, ͳһ���룬���������������   
        response.setCharacterEncoding("UTF-8");   
        
        //��ȡ�ļ���
        PrintWriter out = response.getWriter();
        filename =(String) request.getSession().getAttribute("Img"); //����request.getParamter("userImg")�Ϸ���null
        
        //���ó����صĵ�ַ
    
        if(filename.matches("^(user).*")){
        	errLink="<a href=\"user/modify.jsp\" >����</a>";   

        }else{
        	errLink="<a href=\"admin/modify.jsp\" >����</a>";   
        }
        
        // ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload   
        DiskFileItemFactory dfif = new DiskFileItemFactory();   
        dfif.setSizeThreshold(4096);// �����ϴ��ļ�ʱ������ʱ����ļ����ڴ��С,������4K.���ڵĲ��ֽ���ʱ����Ӳ��   
        dfif.setRepository(new File(getServletContext().getRealPath("/") 
                + "ImagesUploadTemp"));// ���ô����ʱ�ļ���Ŀ¼,web��Ŀ¼�µ�ImagesUploadTempĿ¼   
        
        // �����Ϲ���ʵ�����ϴ����   
        ServletFileUpload sfu = new ServletFileUpload(dfif);   
        // ��������ϴ���С   
        sfu.setSizeMax(MAX_SIZE);   

        
        // ��request�õ������ϴ�����б�   
        List fileList = null;   
        try {   
            fileList = sfu.parseRequest(request);   
        } catch (FileUploadException e) {// �����ļ��ߴ�����쳣   
            if (e instanceof SizeLimitExceededException) {   
                out.println("�ļ��ߴ糬���涨��С:" + MAX_SIZE + "�ֽ�<p />");   
                out.println(errLink);   
                return;   
            }   
            e.printStackTrace();   
        }   
        // û���ļ��ϴ�   
        if (fileList == null || fileList.size() == 0) {   
            out.println("��ѡ���ϴ��ļ�<p />");   
            out.println(errLink);   
            return;   
        }   
        // �õ������ϴ����ļ�   
        Iterator fileItr = fileList.iterator();   
        // ѭ�����������ļ�   
        while (fileItr.hasNext()) {   
            FileItem fileItem = null;   
            String path = null;   
            long size = 0;   
            // �õ���ǰ�ļ�   
            fileItem = (FileItem) fileItr.next();   
            // ���Լ�form�ֶζ������ϴ�����ļ���(<input type="text" />��)   
            if (fileItem == null || fileItem.isFormField()) {   
                continue;   
            }   
            // �õ��ļ�������·��   
            path = fileItem.getName();   
            // �õ��ļ��Ĵ�С   
            size = fileItem.getSize();   
            if ("".equals(path) || size == 0) {   
                out.println("��ѡ���ϴ��ļ�<p />");   
                out   
                        .println(errLink);   
                return;   
            }   
  
            // �õ�ȥ��·�����ļ���   
            String t_name = path.substring(path.lastIndexOf("\\") + 1);   
            // �õ��ļ�����չ��(����չ��ʱ���õ�ȫ��)   
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);   
            // �ܾ����ܹ涨�ļ���ʽ֮����ļ�����   
            int allowFlag = 0;   
            int allowedExtCount = allowedExt.length;   
            for (; allowFlag < allowedExtCount; allowFlag++) {   
                if (allowedExt[allowFlag].equals(t_ext))   
                    break;   
            }   
            if (allowFlag == allowedExtCount) {   
                out.println("���ϴ��������͵��ļ�<p />");   
                for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)   
                    out.println("*." + allowedExt[allowFlag]   
                            + "   ");   
                out.println(errLink);   
                return;   
            }   
  

          
           
            try {   
                // �����ļ�
                fileItem.write(new File(getServletContext().getRealPath("/") +"headerImage/"+filename));   
               
                out.println("�ļ��ϴ��ɹ�. �ѱ���Ϊ: " +filename
                        + "   �ļ���С: " + size + "�ֽ�<p />"); 
                
                if(filename.matches("^(user).*")){
                    out.println("<a href=\"ArticleAction\">����</a>");   

                }else{
                	out.println("<a href=\"AdminArticleAction\">����</a>");   
                }
                
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
