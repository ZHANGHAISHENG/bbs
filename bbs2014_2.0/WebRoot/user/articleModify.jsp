<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    
    <title>My JSP 'modify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script src="jquery2.0/jquery-2.0.0.min.js"></script> 
	<script src="js/check.js"></script>

  </head>
  
  <body>
  
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
	  <tbody>
	    <tr>
	      <td width="140">
	          <a href="">
	           <img src="images/header-left.gif" alt="Java|Java世界_中文论坛|ChinaJavaWorld技术论坛" border="0">
	          </a>
	      </td>
	      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
	      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
	    </tr>
	  </tbody>
	</table>
	  <center><h1>修改</h1></center>
			<form name="form" action="UserArticleAction" method="post" onsubmit="return checkAll()">
			      <input type="hidden" name="id" value="${article.id}"></input>
			      <input type="hidden" name="tag" value="${requestScope.tag}"></input>
			      <input type="hidden" name="pageNo" value="${requestScope.pageNo}"></input>
			      <input type="hidden" name="pageCount" value="${requestScope.pageCount}"></input>
				  <input type="hidden" name="action" value="modify"></input>
                  <table  width="300">
				        <tr><td>标题：</td><td><input type="text"  name="title"  id="title" value="${article.title }"  size="120" maxlength="50"  onblur="checkForTitle(this)"/>
				          <span id="titleTip"></span>
				        </td></tr>
				        
				        <tr><td>内容：</td><td><textArea class="ckeditor" name="content" id="content"  rows="10" cols="120" onblur="checkForContent(this)">${article.content }</textArea>
				          <span id="contentTip"></span>
				        </td></tr>
				        <tr align="center"><td  colspan="2"><input type="submit"  value="确定" /></td></tr>
	             </table>
			</form>
	 <center>
	   <a href="UserArticleAction?action=pageNoSelected&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}">返回到主题列表</a>
	 </center>
	 
	 <script type="text/javascript">
     
         function checkAll(){

 	      var checkArray=new Array(
							     checkForTitle(document.getElementById('title')),
							     checkForContent(document.getElementById('content')) 
					           );
					           
					           
	     for(i=0;i<checkArray.length;i++){
	         b=checkArray[i];
	         console.log("b="+b);
	         if(!b){
	         
	           return false;
	         }
	      }

	       return false;
	   
      }
     
     </script>
	 
  </body>
</html>
