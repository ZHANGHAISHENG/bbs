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
	           <img src="images/header-left.gif" alt="Java|Java����_������̳|ChinaJavaWorld������̳" border="0">
	          </a>
	      </td>
	      <td><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
	      <td width="1%"><img src="images/header-right.gif" alt="" border="0"></td>
	    </tr>
	  </tbody>
	</table>
	<!-- ------------------------------------- ���� --------------------------------------------->

 <div style="clear:both;"></div>  
<div align="left">
	<form name="form" action="AdminAction" method="post" >
	  <input type="hidden" name="action" value="search"></input>
		<table  border="0">
		  
		   <tr>
		      <td>�ؼ��֣�</td>
		      <td>
		        <input type="text" name="keyWord" id="keyWord" size="30" ></input>
		      </td>
		   </tr>
		   <tr>
               <td>ע��ʱ��:</td>
               <td>��ʼʱ��:<input type="text" name="sRDate"  id="sRDate" readOnly size="20" onClick="setDayHM(this);" ></input>
                   <span id="sRDateTip"></span>
               </td>
               <td>��ֹʱ��:<input type="text" name="eRDate" id="eRDate" readOnly size="20" onClick="setDayHM(this);" ></input>
                   <span id="eRDateTip"></span>
               </td>
            </tr> 
			<tr >
				<td colspan="2">
					<input type="submit" value="��" >
					</td>
			</tr>
		</table>

	</form>
 </div>
   
   <!-- ------------------------------------- ��ҳ����  -------------------------------------------->

<jsp:include page="../pageSelected.jsp"> 
     <jsp:param name="actionPage" value="AdminAction"  />
     <jsp:param name="action" value="pageNoSelected"  />
     <jsp:param name="otherQuery" value="&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&tag=${requestScope.tag}&sRDate=${requestScope.sRDate}&eRDate=${requestScope.eRDate}"/>
</jsp:include>

       
<!--------------------------------------- �������б�  -------------------------------------------->
  <table summary="List of threads" cellpadding="0" cellspacing="0" border="1" width="100%" align="center">
       <tr>
        <th>�˺�</th>
        <th>����</th>
        <th>�Ա�</th>
        <th>email</th>
        <th>�绰</th>
        <th>���ʱ��</th>
        <th>����</th>
        <th>ɾ��</th>
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
		  <td><a href="AdminAction?action=delete&id=${admin.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&sRDate=${requestScope.sRDate}&eRDate=${requestScope.eRDate}&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}">ɾ</a></td>     
	   </tr>
    </c:forEach>
	    
  </table>
	
	 <center>
	   <a href="AdminArticleAction">���ص���ҳ��</a>
	 </center>
	 
  </body>
</html>
