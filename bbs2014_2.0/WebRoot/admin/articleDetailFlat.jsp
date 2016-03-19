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
   <script src="../js/linkTransToForm.js"></script>
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
	<span id="aaa"></span>
	
	
	<div id="text" > 
	  <p >��̳: Java����*������(ģ��)</p>
	  <p > ̽��Java���Ի���֪ʶ,�����﷨�� ���һ���� ��ͬ��ߣ�л���κ���ʽ�Ĺ�� </p>
                <%--             ���⣺${detailFlatArticles[0].title}<br/> --%>
	  
	</div>

   
<!-- ------------------------------------- ��ҳ����  -------------------------------------------->

<jsp:include page="../pageSelected.jsp"> 
     <jsp:param name="actionPage" value="AdminArticleAction"  />
     <jsp:param name="action" value="detailFlatPageNoSelected"  />
     <jsp:param name="otherQuery" value="&rootId=${requestScope.rootId}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&indexPageNo=${requestScope.indexPageNo}&indexPageCount=${requestScope.indexPageCount}"/>
</jsp:include>

<!-- ------------------------------------- ������ƽ��չ��  -------------------------------------------->
		
	<%-- <c:forEach var="article" begin="${(requestScope.pageNo-1)*requestScope.numSize}" end="${requestScope.pageNo*requestScope.numSize-1}" items="${requestScope.articles}" varStatus="status">
	
	 <table  width="100%" border="1">
	    <tr align="center">
	        <td  rowspan="4" width="10%" >
	        <img src="headerImage/user${article.user.id}.jpg" alt="${user.name}" width="80" height="80"></img><br/>
	          ${article.user.name}
	        </td>
	        <td width="80%" align="${status.first?'center':'left'}">
	                                  
	           <c:choose>
                 <c:when test="${article.id==rootId||article.pid==rootId}">
                     ${article.title}
                 </c:when>
                                                           
		        <c:otherwise>    
		              ${article.user.name}�ظ���
		                <c:forEach var="articleTemp" items="${requestScope.articles}" varStatus="status">
		                    <c:if test="${article.pid==articleTemp.id}">
		                       ${articleTemp.user.name}
		                    </c:if>
		                </c:forEach>
		        </c:otherwise>
             </c:choose>
	           
	        </td>
	        
	    </tr>
	    <tr align="${status.first?'center':'left'}"><td  colspan="2" >${article.content}</td></tr>
	 </table>
	 
	 </c:forEach> --%>
	 
	 
	  <hr/>
<c:forEach  var="article" begin="${(requestScope.pageNo-1)*requestScope.numSize}" end="${requestScope.pageNo*requestScope.numSize-1}" items="${requestScope.articles}" varStatus="statusFirst">
	 
 <!-- -----------------------------------��ʼһ������----------------------------------------------------------------------- -->
	 <c:choose>
         <c:when test="${article.id==rootId||article.pid==rootId}">
	  <table  width="100%" border="0">
	    <tr align="center">
	        <td  rowspan="3" width="10%" bgcolor="#E0EEE0">
	        
	          <img src="headerImage/user${article.user.id}.jpg" alt="${user.name}" width="80" height="80"></img><br/>
	          
	          ${article.user.name}<br/>
	          <c:if test="${article.id==rootId||article.user.id==rootUserAtAdminLook.id}">
	           <font size="6" color="red">¥��</font>
	            <c:set var="rootUserAtAdminLook" value="${article.user}" scope="session"></c:set>
	          </c:if>
	        </td>
	        <td width="80%" align="${status.first?'center':'left'}">
	                                  
	           <c:choose>
                 <c:when test="${article.id==rootId}">
                     ${article.title}
                     
                 </c:when>
                                                           
		        <c:otherwise>    
		                ${article.user.id==rootUserAtAdminLook.id? "<font size='6' color='red'>¥��</font></c:if>" : article.user.name} �ظ���
		              <!-- �ҵ��������� -->
		                <c:forEach var="articleTemp" items="${requestScope.articles}" varStatus="status">
		                    <c:if test="${article.pid==articleTemp.id}">
		                       ${articleTemp.user.id==rootUserAtAdminLook.id? "<font size='6' color='red'>¥��</font></c:if>" : articleTemp.user.name} 
		                    </c:if>
		                </c:forEach>
		        </c:otherwise>
             </c:choose>
	           
	        </td>
	        <td align="right" width="10%" >
<%-- 	            <a href="user/reply.jsp?action=reply&pArticleId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&pageNo=${requestScope.indexPageNo}&pageCount=${requestScope.indexPageCount}">�ظ�</a>
 --%>	     </td>
	    </tr>
	    <tr align="${status.first?'center':'left'}">
	        <td  colspan="2" >${article.content}
	        
	   </c:when>
                                                           
       <c:otherwise>    
<!-- -----------------------------------չ������----------------------------------------------------------------------- -->
        <hr color="#F5FFFA" />
	    <table border="0" width="100%" bgcolor="#E0EEE0">
			  <tr><td  rowspan=2 width="10%"><img src="headerImage/user${article.user.id}.jpg" alt="${user.name}" width="40" height="40"></img><br/>${article.user.id==rootUserAtAdminLook.id? "<font size='6' color='red'>¥��</font></c:if>" : article.user.name}</td>
			      <td>�ظ���
		                <c:forEach var="articleTemp" items="${requestScope.articles}" varStatus="status">
		                    <c:if test="${article.pid==articleTemp.id}">
		                       ${articleTemp.user.id==rootUserAtAdminLook.id? "<font size='6' color='red'>¥��</font></c:if>" : articleTemp.user.name} :${article.content}
		                    </c:if>
		                </c:forEach>
		          </td>
		      </tr>
		</table>

       </c:otherwise>

   </c:choose>
<!-- -----------------------------------���۽���----------------------------------------------------------------------- -->

 <c:if test="${article.id==rootId||articles[statusFirst.index+1].pid==rootId||statusFirst.last}">
	  
	   </td>
	 </tr>
  </table>
   <hr/>
</c:if>  

</c:forEach>
	
	 <center>
	   <a href="AdminArticleAction?action=pageNoSelected&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&pageNo=${requestScope.indexPageNo}&pageCount=${requestScope.indexPageCount}"  onclick=" return linkClick(this)">���ص���ҳ</a>
	 </center>
  </body>
</html>