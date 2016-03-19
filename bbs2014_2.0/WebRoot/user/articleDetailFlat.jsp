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
    
    <title>My JSP 'articleDetailFlat.jsp' starting page</title>
    
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
	           <img src="images/header-left.gif" alt="Java|Java世界_中文论坛|ChinaJavaWorld技术论坛" border="0">
	          </a>
	      </td>
	      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
	      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
	    </tr>
	  </tbody>
	</table>
	
 <!-- ------------------------------------- 分页制作  -------------------------------------------->
	
<jsp:include page="../pageSelected.jsp"> 
     <jsp:param name="actionPage" value="UserArticleAction"  />
     <jsp:param name="action" value="detailFlatPageNoSelected"  />
     <jsp:param name="otherQuery" value="&rootId=${requestScope.rootId}"/>
</jsp:include>

<!-- ------------------------------------- 主题帖平板展现  -------------------------------------------->
		
	<%-- <c:forEach var="article" begin="${(requestScope.pageNo-1)*requestScope.numSize}" end="${requestScope.pageNo*requestScope.numSize-1}" items="${requestScope.articles}" varStatus="status">
	
	 <table  width="100%" border="1">
	    <tr align="center">
	        <td width="70%" align="${status.first?'center':'left'}">
	                                  
	           <c:choose>
                 <c:when test="${article.id==requestScope.rootId||article.pid==requestScope.rootId}">
                     ${article.title}---id=${article.id}
                 </c:when>
                                                           
		        <c:otherwise>    
		              ${article.user.name}回复：
		                <c:forEach var="articleTemp" items="${requestScope.articles}" varStatus="status">
		                    <c:if test="${article.pid==articleTemp.id}">
		                       ${articleTemp.user.name}---id=${article.id}
		                    </c:if>
		                </c:forEach>
		        </c:otherwise>
             </c:choose>
	           
	        </td>
	       <td>
	           <c:if test="${article.id!=requestScope.rootId}">
			       <a href="UserArticleAction?action=delete&rootId=${article.id}">删除</a>&nbsp
	           </c:if>
	       </td>
	    </tr>
	    <tr align="${status.first?'center':'left'}"><td  colspan="2" >${article.content}</td></tr>
	 </table>
	 
	 </c:forEach> --%>
	 
	  <hr/>
<c:forEach  var="article" begin="${(requestScope.pageNo-1)*requestScope.numSize}" end="${requestScope.pageNo*requestScope.numSize-1}" items="${requestScope.articles}" varStatus="statusFirst">
	 
 <!-- -----------------------------------开始一个讨论----------------------------------------------------------------------- -->
	 <c:choose>
         <c:when test="${article.id==rootId||article.pid==rootId}">
	  <table  width="100%" border="0">
	    <tr align="center">
	        <td  rowspan="4" width="10%" bgcolor="#E0EEE0">
	        
	          <img src="headerImage/user${article.user.id}.jpg" alt="${user.name}" width="80" height="80"></img><br/>
	          
	          ${article.user.name}<br/>
	          <c:if test="${article.id==rootId||article.user.id==rootUser.id}">
	           <font size="6" color="red">楼主</font>
	            <c:set var="rootUser" value="${article.user}" scope="session"></c:set>
	          </c:if>
	        </td>
	        <td width="80%" align="${status.first?'center':'left'}">
	                                  
	           <c:choose>
                 <c:when test="${article.id==rootId}">
                     ${article.title}
                     
                 </c:when>
                                                           
		        <c:otherwise>    
		                ${article.user.id==rootUser.id? "<font size='6' color='red'>楼主</font></c:if>" : article.user.name} 回复：
		              <!-- 找到父贴名称 -->
		                <c:forEach var="articleTemp" items="${requestScope.articles}" varStatus="status">
		                    <c:if test="${article.pid==articleTemp.id}">
		                       ${articleTemp.user.id==rootUser.id? "<font size='6' color='red'>楼主</font></c:if>" : articleTemp.user.name} 
		                    </c:if>
		                </c:forEach>
		        </c:otherwise>
             </c:choose>
	           
	        </td>
	        <td align="right" width="10%" >
	            <a href="user/reply.jsp?action=reply&pArticleId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&pageNo=${requestScope.indexPageNo}&pageCount=${requestScope.indexPageCount}">回复</a>
	       </td>
	    </tr>
	    <tr align="${status.first?'center':'left'}">
	        <td  colspan="2" >${article.content}
	        
	   </c:when>
                                                           
       <c:otherwise>    
<!-- -----------------------------------展开讨论----------------------------------------------------------------------- -->
        <hr color="#F5FFFA" />
	    <table border="0" width="100%" bgcolor="#E0EEE0">
			  <tr><td  rowspan=2 width="10%"><img src="headerImage/user${article.user.id}.jpg" alt="${user.name}" width="40" height="40"></img><br/>${article.user.id==rootUser.id? "<font size='6' color='red'>楼主</font></c:if>" : article.user.name}</td>
			      <td>回复：
		                <c:forEach var="articleTemp" items="${requestScope.articles}" varStatus="status">
		                    <c:if test="${article.pid==articleTemp.id}">
		                       ${articleTemp.user.id==rootUser.id? "<font size='6' color='red'>楼主</font></c:if>" : articleTemp.user.name} :${article.content}
		                    </c:if>
		                </c:forEach>
		          </td>
		      </tr>
			  <tr><td align="right"> <a href="user/reply.jsp?action=reply&pArticleId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&pageNo=${requestScope.indexPageNo}&pageCount=${requestScope.indexPageCount}">回复</a></td></tr>
		</table>

       </c:otherwise>

   </c:choose>
<!-- -----------------------------------讨论结束----------------------------------------------------------------------- -->

 <c:if test="${article.id==rootId||articles[statusFirst.index+1].pid==rootId||statusFirst.last}">
	  
	   </td>
	 </tr>
  </table>
   <hr/>
</c:if>  

</c:forEach>
	
	 <center>
	   <a href="user/index.jsp">返回到主题列表</a>
	 </center>
  </body>
</html>
