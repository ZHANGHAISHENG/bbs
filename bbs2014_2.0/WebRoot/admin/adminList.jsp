<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("keyWord="+request.getParameter("keyWord"));

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'showAllAdmin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 	<script src="js/Calendar.js"></script>
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
	<!-- ------------------------------------- 搜索 --------------------------------------------->

 <div style="clear:both;"></div>  
<div align="left">
	<form name="form" action="AdminAction" method="post" >
	  <input type="hidden" name="action" value="search"></input>
		<table  border="0">
		  
		   <tr>
		      <td>关键字：</td>
		      <td>
		        <input type="text" name="keyWord" id="keyWord" size="30" ></input>
		      </td>
		   </tr>
		   <tr>
               <td>注册时间:</td>
               <td>起始时间:<input type="text" name="sRDate"  id="sRDate" readOnly size="20" onClick="setDayHM(this);" ></input>
                   <span id="sRDateTip"></span>
               </td>
               <td>终止时间:<input type="text" name="eRDate" id="eRDate" readOnly size="20" onClick="setDayHM(this);" ></input>
                   <span id="eRDateTip"></span>
               </td>
            </tr> 
			<tr >
				<td colspan="2">
					<input type="submit" value="搜" >
					</td>
			</tr>
		</table>

	</form>
 </div>
   
   <!-- ------------------------------------- 分页制作  -------------------------------------------->

<jsp:include page="../pageSelected.jsp"> 
     <jsp:param name="actionPage" value="AdminAction"  />
     <jsp:param name="action" value="pageNoSelected"  />
     <jsp:param name="otherQuery" value="&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&tag=${requestScope.tag}&sRDate=${requestScope.sRDate}&eRDate=${requestScope.eRDate}"/>
</jsp:include>

       
<!--------------------------------------- 主题帖列表  -------------------------------------------->
  <table summary="List of threads" cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
       <tr>
        <th>账号</th>
        <th>名字</th>
        <th>性别</th>
        <th>email</th>
        <th>电话</th>
        <th>添加时间</th>
        <th>描叙</th>
        <th>删除</th>
      </tr>
    <c:forEach var="admin" items="${requestScope.admins}">
       <tr >
		  <td>${admin.id }</td>
		  <td>${admin.name }</td>
		  <td>${admin.sex}&nbsp</td>
		  <td>${admin.email }&nbsp</td>
		  <td>${admin.phone }&nbsp</td>
		  <td>${admin.rdate }&nbsp</td>
		  <td>${admin._desc}&nbsp</td>
		  <td><a href="AdminAction?action=delete&id=${admin.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&sRDate=${requestScope.sRDate}&eRDate=${requestScope.eRDate}&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}">删</a></td>     
	   </tr>
    </c:forEach>
	    
  </table>
	
	 <center>
	   <a href="AdminArticleAction">返回到主页面</a>
	 </center>
	 
  </body>
</html>
