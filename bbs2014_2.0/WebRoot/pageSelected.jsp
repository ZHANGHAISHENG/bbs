<%@page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'pageSelected.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
       <script src="js/linkTransToForm.js"></script>
  </head>
  
  <body>
  <!-- ------------------------------------- 分页制作  -------------------------------------------->

 	<script>
 
	  function LinkToSkipPage(){
	     var select=document.getElementById("pageNo");
	     var i=select.selectedIndex;
	     var pageNo=select.options[i].value; 
	     var aObject=document.getElementById("select");
	      aObject.href="${param.actionPage}?action=${param.action}&pageNo="+pageNo+"&pageCount=${requestScope.pageCount}${param.otherQuery}";
	    // window.location.href="${param.actionPage}?action=${param.action}&pageNo="+pageNo+"&pageCount=${requestScope.pageCount}${param.otherQuery}";
	     linkClick(aObject);
	  }
	
	</script> 

   
         共${requestScope.pageCount}页:[
  
	  <a href="${param.actionPage}?action=${param.action}&pageNo=${1}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">首页</a>
	  |<a href="${param.actionPage}?action=${param.action}&pageNo=${requestScope.pageNo-1 }&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">上一页</a>

  <c:choose>
     <c:when test="${requestScope.pageNo==1||requestScope.pageCount<=requestScope.numSize}">
        <c:choose>
          <c:when test="${requestScope.numSize<requestScope.pageCount}">
              <c:forEach var="num" begin="1" end="${requestScope.numSize}" step="1">
                 <a href="${param.actionPage}?action=${param.action}&pageNo=${num}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">
                   <c:if test="${num==requestScope.pageNo}"><font color="black">${num}</font> </c:if>
                   <c:if test="${num!=requestScope.pageNo}">${num}</c:if>
                 </a>
             </c:forEach>
          </c:when>
          
          <c:otherwise>
              <c:forEach var="num" begin="1" end="${requestScope.pageCount}" step="1">
                 <a href="${param.actionPage}?action=${param.action}&pageNo=${num}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">
                   <c:if test="${num==requestScope.pageNo}"><font color="black">${num}</font> </c:if>
                   <c:if test="${num!=requestScope.pageNo}">${num}</c:if>
                 </a>
             </c:forEach>
          </c:otherwise>
        </c:choose>
     </c:when>
     
     <c:otherwise>
        <c:choose>
          <c:when test="${(requestScope.pageNo+1)*(requestScope.numSize/2)<=requestScope.pageCount}">
             <c:forEach var="num" begin="${(requestScope.pageNo-1)*(requestScope.numSize/2)+1 }" end="${(requestScope.pageNo+1)*(requestScope.numSize/2)}" step="1">
                  <a href="${param.actionPage}?action=${param.action}&pageNo=${num}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">
                   <c:if test="${num==requestScope.pageNo}"><font color="black">${num}</font> </c:if>
                   <c:if test="${num!=requestScope.pageNo}">${num}</c:if>
                   </a>
             </c:forEach>
          </c:when>
          
          <c:otherwise>
                <c:forEach var="num" begin="${(requestScope.pageNo-1)*(requestScope.numSize/2)+1 }" end="${requestScope.pageCount}" step="1">
                  <a href="${param.actionPage}?action=${param.action}&pageNo=${num}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">
                   <c:if test="${num==requestScope.pageNo}"><font color="black">${num}</font> </c:if>
                   <c:if test="${num!=requestScope.pageNo}">${num}</c:if>
                   </a>
                </c:forEach>
          </c:otherwise>
        </c:choose>
     </c:otherwise>
  </c:choose>
  
     |<a href="${param.actionPage}?action=${param.action}&pageNo=${requestScope.pageNo+1}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">下一页</a> 
     |<a href="${param.actionPage}?action=${param.action}&pageNo=${requestScope.pageCount}&pageCount=${requestScope.pageCount}${param.otherQuery}" onclick=" return linkClick(this)">尾页</a> 

 
<a id="select" href=""></a>
  
]跳到第
     <select  id="pageNo" onchange=" LinkToSkipPage()">
       <c:forEach var="num" begin="1" end="${requestScope.pageCount}" step="1">
             <c:choose>
                 <c:when test="${num==requestScope.pageNo}">
                      <option value="${num}" selected>${num}</option>
                 </c:when>
            
		        <c:otherwise>    
		             <option value="${num}">${num}</option>
		        </c:otherwise>
             </c:choose>
       </c:forEach>
   </select>
       页  <br/>
       
       
  </body>
</html>
