<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'deleteArticleIsSuccess.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <c:choose>
       <c:when test="${isSuccess}">
                                  删除成功!<br/>
       </c:when>
       <c:otherwise>
                                  删除失败!<br/>
       </c:otherwise>
     
     </c:choose>
     
     <script src="js/GoBack.js"></script>
     <script >param('AdminUserAction?action=pageNoSelected&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&sRDate=${requestScope.sRDate}&eRDate=${requestScope.eRDate}&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}')</script>

     <div>
       <span id="num"><font color="red" size="5">5秒后实现跳转</font></span><br/>
       <a href="AdminUserAction?action=pageNoSelected&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&sRDate=${requestScope.sRDate}&eRDate=${requestScope.eRDate}&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}"}>点击跳转</a>
     </div>
  </body>
</html>
