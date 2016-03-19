<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%--  <base href="<%=basePath%>"> --%>
    
    <title>My JSP 'register.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="../jquery2.0/jquery-2.0.0.min.js"></script> 
	<script src="../js/check.js"></script>

  </head>
  
	  <body>
	      <table border="0" cellpadding="0" cellspacing="0" width="100%">
		  <tbody>
		    <tr>
		      <td width="140">
		          <a href="">
		           <img src="../images/header-left.gif" alt="Java|Java世界_中文论坛|ChinaJavaWorld技术论坛" border="0">
		          </a>
		      </td>
		      <td><img src="../images/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
		      <td width="1%"><img src="../images/header-right.gif" alt="" border="0"></td>
		    </tr>
		  </tbody>
		</table>
		
      <center><h1>添加管理员</h1></center>
			<form name="form" action="../AdminAction" method="post"  onsubmit="return checkAll()">
				<input type="hidden" name="action" value="register"></input>
				<table align="center" border="1">
				   <tr>
				      <td>姓名：</td>
				      <td>
				        <input type="text" name="name" id="name" size="30" onblur="checkForName(this)"></input>
						<span id="nameTip"></span>
				      </td>
				   </tr>
				   <tr>
				      <td>性别：</td>
				      <td>
				        <select name="sex" id="sex">
				          <option value="男">男</option>
				          <option value="女">女</option>
				        </select>
				      </td>
				   </tr>
				    <tr>
				      <td>密码：</td>
				      <td>
				        <input type="password" name="password" id="password" size="30" onblur=""></input>
						<span id="passwordTip"></span>
				      </td>
				   </tr>
				   <tr>
				      <td>密码确认：</td>
				      <td>
				        <input type="password" name="pConfirm" id="pConfirm" size="30" onblur="checkForPassword(document.getElementById('password'),this)"></input>
						<span id="pConfirmTip"></span>
				      </td>
				   </tr>
				   <tr>
				    <td>级别：</td>
				      <td>
				        <select name="level" id="level">
				          <option value="0">超级管理员</option>
				          <option value="1">普通管理员</option>
				        </select>
				      </td>
				   </tr>
				    <tr>
				      <td>邮箱地址：</td>
				      <td>
				        <input type="text" name="email" id="email" size="50" onblur="checkForEmail(this)"></input>
						<span id="emailTip"></span>
				      </td>
				   </tr>
				   <tr>
				      <td>电话：</td>
				      <td>
				        <input type="text" name="phone" id="phone" size="30" onblur="checkForPhone(this)"></input>
						<span id="phoneTip"></span>
				      </td>
				   </tr>
				  
				   <tr>
						<td>说明：</td>
						<td>
							<textarea name="_desc" rows="10"
								cols="50">
							</textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2">
							<input type="submit" value="确定" >
							</td>
					</tr>
				</table>

			</form>
			
				<script  type="text/javascript">
  	

	    function checkAll(){
	    
	    var checkArray=new Array(
			                     checkForName(document.getElementById('name')),
							     checkForPassword(document.getElementById('password'),document.getElementById('pConfirm')),
							     checkForEmail(document.getElementById('email'))   ,
							     checkForPhone(document.getElementById('phone')) 
					           );
					           
					           
	     for(i=0;i<checkArray.length;i++){
	         b=checkArray[i];
	         if(!b){
	         
	           return false;
	         }
	      }
	       return true;
	   } 
	      
	
	</script>
	
	<center>
	    <a href="../AdminArticleAction">返回到主页</a>
	</center>
  </body>
</html>
