<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";


%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<%--  <base href="<%=basePath%>">  --%>
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<style type="text/css">
	#login {	border:0px solid  #0C0;
		float:right;
		height:100px;
		width:500px;
		background-color: #FFF;
	}
	
	 #text {border:0px solid  #0C0;
		float:left;
		height:100px;
		width:700px;
		
		background-color: #FFF;
	}
</style>
	<script src="js/linkTransToForm.js"></script>
	<script src="jquery2.0/jquery-2.0.0.min.js"></script> 
	<script src="js/check.js"></script>
    <script>
     function checkForId(obj){

	   if(obj.value==null){
	
         document.getElementById("idErr").innerHTML="<font color='red'>账号不能为空</font>";
	     return false;
		}
		if(obj.value.match(/^\s*\s*$/)){

		    document.getElementById("idErr").innerHTML="<font color='red'>账号不能为空</font>";
			return false;
		}
		  document.getElementById("idErr").innerHTML="";

	 return true; 
    }
    </script>
   
  </head>
  
  <body >
  
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
	
	
<div id="text" > 

  <p >论坛: Java语言*初级版(模仿)</p>
  <p > 探讨Java语言基础知识,基本语法等 大家一起交流 共同提高！谢绝任何形式的广告 </p>

   <a  href="user/post.jsp">
     <img src="images/post-16x16.gif" alt="发表新主题" border="0" height="16" width="16"/>
                  发表新主题
   </a>

</div>
	
<!-- ------------------------------------- 登陆,注册 --------------------------------------------->
<div id="login" align="right">	
   <c:if test="${user!=null }">
	   <a href="user/index.jsp" ><img src="headerImage/${Img}" alt="${user.name}" width="50" height="50"></img></a>
      
   </c:if>
   

   <form id="form"  name="form" action="UserAction" method="post" onsubmit="return checkForId(document.getElementById('id')) ">

    <input type="hidden" name="action" value="login"></input>
     <table  border="0" width="">

    <tr><td>账号：</td>
	    <td><input  type="text" name="id" id="id"  size="30"  maxlength="20" onblur="checkForId(this)" />
		<span id="idErr"></span>
		</td>
	 </tr>

	 <tr>
	 <td>密码:</td>
	    <td><input  type="password" name="password" size="30"  maxlength="20"/>
		<span id="passwordErr"></span>
		</td>
		
	 </tr>
	
	  <tr>
	  <td></td>
	   <td align="center">
	       <input type="submit" value="确定">  
	   </td>
	 </tr>
   </table>
  </form>
  <br/>
  <c:if test="${user!=null}">
  <p onclick="document.getElementById('form').style.display='';this.style.display='none'"><font color="blue">点击</font>重新登录</p>
  </c:if>
  <font color="red">${requestScope.err}</font><br/>
       没有账号?<a href="user/register.jsp">注册</a>
 </div>
 

 
 
 <c:if test="${loginReturn!=null}">

  <c:choose>
    <c:when test="${user!=null}">
       <script>
           // document.getElementById("idErr").innerHTML="<font color='blue'>已登录</font>";
             document.getElementById("form").style.display="none";
		</script>
    </c:when>
    <c:otherwise>
       <script>
           document.getElementById("idErr").innerHTML="<font color='red'>用户名或密码不正确</font>";
		</script>
    </c:otherwise>
  </c:choose>

</c:if>	
	
 
<!-- ------------------------------------- 搜索 --------------------------------------------->

 <div style="clear:both;"></div>  
 
   <form action="ArticleAction" method="post">
       <input type="hidden" name="action" value="search"></input>
       <input type="text" name="keyWord"  size="80"/>
       <input type="submit" name="sure"  value="search" />       
   </form>
   
<!-- ------------------------------------- 分页制作  -------------------------------------------->

<jsp:include page="pageSelected.jsp"> 
     <jsp:param name="actionPage" value="ArticleAction"  />
     <jsp:param name="action" value="pageNoSelected"  />
     <jsp:param name="otherQuery" value="&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}"/>
</jsp:include>

       
<!--------------------------------------- 主题帖列表  -------------------------------------------->
  <table summary="List of threads" border="1" cellpadding="0" cellspacing="0" width="100%" align="center">
    <th bgcolor="green" width="15%">作者</th>
    <th bgcolor="green">主题</th>
    <th bgcolor="green" width="15%">回复</th>
    <c:forEach var="article" items="${requestScope.articles}">
       <tr align="center">
		    <td>${article.user.name}</td>
		    <td><a href="ArticleAction?action=detailFlat&rootId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&indexPageNo=${requestScope.pageNo}&indexPageCount=${requestScope.pageCount}" onclick=" return linkClick(this)">${article.title }</a></td>
		    <td><a href="user/reply.jsp?action=reply&pArticleId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}"  onclick=" return linkClick(this)" onclick=" return linkClick(this)">回复</a></td>
	   </tr>
    </c:forEach>
	    
  </table>
    
  </body>
</html>
