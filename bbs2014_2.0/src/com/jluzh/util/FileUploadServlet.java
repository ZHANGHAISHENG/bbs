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
	  // 获取文件名
    String filename;
    public FileUploadServlet() {
    	 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		final long MAX_SIZE =10*1024*1024;// 设置上传文件最大值   
        // 允许上传的文件格式的列表   
        final String[] allowedExt = new String[] {"jpg", "jpeg", "gif"};   
        response.setContentType("text/html");   
        // 设置字符编码为UTF-8, 统一编码，处理出现乱码问题   
        response.setCharacterEncoding("UTF-8");   
        
        //获取文件名
        PrintWriter out = response.getWriter();
        filename =(String) request.getSession().getAttribute("Img"); //由于request.getParamter("userImg")老返回null
        
        //设置出错返回的地址
    
        if(filename.matches("^(user).*")){
        	errLink="<a href=\"user/modify.jsp\" >返回</a>";   

        }else{
        	errLink="<a href=\"admin/modify.jsp\" >返回</a>";   
        }
        
        // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload   
        DiskFileItemFactory dfif = new DiskFileItemFactory();   
        dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘   
        dfif.setRepository(new File(getServletContext().getRealPath("/") 
                + "ImagesUploadTemp"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录   
        
        // 用以上工厂实例化上传组件   
        ServletFileUpload sfu = new ServletFileUpload(dfif);   
        // 设置最大上传大小   
        sfu.setSizeMax(MAX_SIZE);   

        
        // 从request得到所有上传域的列表   
        List fileList = null;   
        try {   
            fileList = sfu.parseRequest(request);   
        } catch (FileUploadException e) {// 处理文件尺寸过大异常   
            if (e instanceof SizeLimitExceededException) {   
                out.println("文件尺寸超过规定大小:" + MAX_SIZE + "字节<p />");   
                out.println(errLink);   
                return;   
            }   
            e.printStackTrace();   
        }   
        // 没有文件上传   
        if (fileList == null || fileList.size() == 0) {   
            out.println("请选择上传文件<p />");   
            out.println(errLink);   
            return;   
        }   
        // 得到所有上传的文件   
        Iterator fileItr = fileList.iterator();   
        // 循环处理所有文件   
        while (fileItr.hasNext()) {   
            FileItem fileItem = null;   
            String path = null;   
            long size = 0;   
            // 得到当前文件   
            fileItem = (FileItem) fileItr.next();   
            // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)   
            if (fileItem == null || fileItem.isFormField()) {   
                continue;   
            }   
            // 得到文件的完整路径   
            path = fileItem.getName();   
            // 得到文件的大小   
            size = fileItem.getSize();   
            if ("".equals(path) || size == 0) {   
                out.println("请选择上传文件<p />");   
                out   
                        .println(errLink);   
                return;   
            }   
  
            // 得到去除路径的文件名   
            String t_name = path.substring(path.lastIndexOf("\\") + 1);   
            // 得到文件的扩展名(无扩展名时将得到全名)   
            String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);   
            // 拒绝接受规定文件格式之外的文件类型   
            int allowFlag = 0;   
            int allowedExtCount = allowedExt.length;   
            for (; allowFlag < allowedExtCount; allowFlag++) {   
                if (allowedExt[allowFlag].equals(t_ext))   
                    break;   
            }   
            if (allowFlag == allowedExtCount) {   
                out.println("请上传以下类型的文件<p />");   
                for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)   
                    out.println("*." + allowedExt[allowFlag]   
                            + "   ");   
                out.println(errLink);   
                return;   
            }   
  

          
           
            try {   
                // 保存文件
                fileItem.write(new File(getServletContext().getRealPath("/") +"headerImage/"+filename));   
               
                out.println("文件上传成功. 已保存为: " +filename
                        + "   文件大小: " + size + "字节<p />"); 
                
                if(filename.matches("^(user).*")){
                    out.println("<a href=\"ArticleAction\">返回</a>");   

                }else{
                	out.println("<a href=\"AdminArticleAction\">返回</a>");   
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
