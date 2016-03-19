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
    
    <title>My JSP 'index.jsp' starting page</title>
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
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
	  <tbody>
	    <tr>
	      <td width="140">
	          <a href="">
	           <img src="images/header-left.gif" alt="Java|Java����_������̳|ChinaJavaWorld������̳" border="0">
	          </a>
	      </td>
	      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
	      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
	    </tr>
	  </tbody>
	</table>
	<a href="UserArticleAction?action=myArticle">�ҵ�����</a>|<a href="UserArticleAction?action=selfParticipation">�ҵĲ���</a>|<a href="user/modify.jsp">���ϱ༭</a> 
	<div align="right"> <img src="headerImage/${Img}" alt="${user.name}" width="50" height="50"></img></div>
	
	
	<!-- ------------------------------------- ��ҳ����  -------------------------------------------->


<c:if test="${pageCount!=null&&pageCount>0}">
    <jsp:include page="../pageSelected.jsp"> 
		     <jsp:param name="actionPage" value="UserArticleAction"  />
		     <jsp:param name="action" value="pageNoSelected"  />
		     <jsp:param name="otherQuery" value=""/>
		</jsp:include>




       
<!--------------------------------------- �������б�  -------------------------------------------->
  <table summary="List of threads" border="1" cellpadding="0"  cellspacing="0" border="1" width="100%" align="center">
    <th bgcolor="green">����</th>
    <th bgcolor="green">ɾ��</th>
    <th bgcolor="green">�޸�</th>
    <c:forEach var="article" items="${requestScope.articles}">
    
       <tr align="center">
		    <td><a href="UserArticleAction?action=detailFlat&rootId=${article.id}">${article.title }</a></td>
		    <td><a href="UserArticleAction?action=delete&rootId=${article.id}&tag=${requestScope.tag }&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}">ɾ��</a></td>
		    <td><a href="UserArticleAction?action=loadWhenModify&id=${article.id}&tag=${requestScope.tag }&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}">�޸�</a></td>
	   </tr>
    </c:forEach>
	    
  </table> 
</c:if>	
 <center>
   <a href="ArticleAction">���ص���ҳ��</a>
 </center>
  </body>
</html>
