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
	  <script src="../js/linkTransToForm.js"></script>
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
	 <script>
     function checkForId(obj){

	   if(obj.value==null){
	
         document.getElementById("idErr").innerHTML="<font color='red'>�˺Ų���Ϊ��</font>";
	     return false;
		}
		if(obj.value.match(/^\s*\s*$/)){

		    document.getElementById("idErr").innerHTML="<font color='red'>�˺Ų���Ϊ��</font>";
			return false;
		}
		  document.getElementById("idErr").innerHTML="";

	 return true; 
    }
    </script>
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
	
	
<div id="text" > 

  <p >��̳: Java����*������(ģ��)</p>
  <p > ̽��Java���Ի���֪ʶ,�����﷨�� ���һ���� ��ͬ��ߣ�л���κ���ʽ�Ĺ�� </p>


</div>
	
<!-- ------------------------------------- ��½,ע�� --------------------------------------------->
<div id="login" align="right">	
 <c:if test="${admin!=null }">
	   <a href="admin/index.jsp" ><img src="headerImage/${Img}" alt="${admin.name}" width="50" height="50"></img></a>
  </c:if>
  
  <form  name="form" action="AdminAction" method="post" onsubmit="return checkForId(document.getElementById('id')) ">

    <input type="hidden" name="action" value="login"></input>
     <table  border="0" width="">

    <tr><td>�˺ţ�</td>
	    <td><input  type="text" name="id" id="id"  size="30"  maxlength="20" onblur="checkForId(this)"/>
		<span id="idErr"></span>
		</td>
	 </tr>

	 <tr>
	 <td>����:</td>
	    <td><input  type="password" name="password" size="30"  maxlength="20"/>
		<span id="passwordErr"></span>
		</td>
		
	 </tr>
	
	  <tr>
	  <td></td>
	   <td align="center">
	       <input type="submit" value="ȷ��">  
	   </td>
	 </tr>
   </table>

  
   <font color="red">${requestScope.err}</font><br/>
  </form>   <br/>

   
     <c:if test="${admin!=null&&admin.level==0}">
          <a href="admin/adminRegister.jsp"> ��ӹ���Ա</a>|<a href="AdminAction?action=adminList"> ����Ա�б�</a>|<a href="AdminUserAction?action=userList"> �û��б�</a>|<a href="admin/modify.jsp">�������ϱ༭</a> 
           <p onclick="document.getElementById('form').style.display='';this.style.display='none'"><font color="blue">���</font>���µ�¼</p>
     </c:if>
      <c:if test="${admin!=null&&admin.level==1}">
          <a href="AdminUserAction?action=userList"> �û��б�</a>|<a href="admin/modify.jsp">�������ϱ༭</a> 
           <p onclick="document.getElementById('form').style.display='';this.style.display='none'"><font color="blue">���</font>���µ�¼</p>
     </c:if>
      
 </div>
 
 
 <c:if test="${loginReturn!=null}">

  <c:choose>
    <c:when test="${admin!=null}">
       <script>
            //document.getElementById("idErr").innerHTML="<font color='blue'>�ѵ�¼</font>";
         document.getElementById("form").style.display="none";
		</script>
    </c:when>
    <c:otherwise>
       <script>
           document.getElementById("idErr").innerHTML="<font color='red'>�û��������벻��ȷ</font>";
		</script>
    </c:otherwise>
  </c:choose>

</c:if>	
	
 
<!-- ------------------------------------- ���� --------------------------------------------->

 <div style="clear:both;"></div>  
 
   <form action="AdminArticleAction" method="post">
       <input type="hidden" name="action" value="search"></input>
       <input type="text" name="keyWord"  size="80"/>
       <input type="submit" name="sure"  value="search" />       
   </form>
   
<!-- ------------------------------------- ��ҳ����  -------------------------------------------->

<jsp:include page="../pageSelected.jsp"> 
     <jsp:param name="actionPage" value="AdminArticleAction"  />
     <jsp:param name="action" value="pageNoSelected"  />
     <jsp:param name="otherQuery" value="&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}"/>
</jsp:include>

       
<!--------------------------------------- �������б�  -------------------------------------------->
  <table summary="List of threads" border="1" cellpadding="0" cellspacing="0" width="100%" align="center">
    <th bgcolor="green" width="15%">����</th>
    <th bgcolor="green">����</th>
     <c:if test="${admin!=null }">
    <th bgcolor="green">ɾ��</th>
    </c:if>
    <c:forEach var="article" items="${requestScope.articles}">
       <tr align="center">
		    <td>${article.user.name}</td>
		    <td><a href="AdminArticleAction?action=detailFlat&rootId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&indexPageNo=${requestScope.pageNo}&indexPageCount=${requestScope.pageCount}" onclick=" return linkClick(this)">${article.title }</a></td>
		    <c:if test="${admin!=null }">
		     <td><a href="AdminArticleAction?action=delete&rootId=${article.id}&rootId=${article.id}&tag=${requestScope.tag}&keyWord=${requestScope.keyWord}&pageNo=${requestScope.pageNo}&pageCount=${requestScope.pageCount}" >ɾ��</a></td>
		    </c:if>
	   </tr>
    </c:forEach>
	    
  </table>
    
  </body>
</html>
