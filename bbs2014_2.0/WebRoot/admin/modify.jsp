<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
    
    <title>My JSP 'modify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script src="jquery2.0/jquery-2.0.0.min.js"></script> 
	<script src="js/check.js"></script>

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
	  <center><h1>���������޸�</h1></center>
	     <h1>ͷ���ϴ�</h1>
	           ע�⣺�ϴ�ͼƬ��С���ܳ���10M<br/>
	  	  <form action="FileUploadServlet" method="post"  enctype="multipart/form-data">  

		     <input type="file" size="30"   name="file" /> <br />  
			 <input name="up" type="submit" value="�ϴ�" />
	       </form> 
			<form name="form" action="AdminAction" method="post"  onsubmit="return checkAll()" >

				<input type="hidden" name="action" value="modify"></input>
				<table align="center" border="1">
				   <tr>
				      <td>�˺ţ�</td>
				      <td>
				        <input type="text" name="id" id="id" size="30" readOnly value="${admin.id}" ></input>

				      </td>
				   </tr> 
				   <tr>
				      <td>������</td>
				      <td>
				        <input type="text" name="name" id="name" size="30" value="${admin.name}" maxlength="40" onblur="checkForName(this)"></input>
						<span id="nameTip"></span>
				      </td>
				   </tr>  
				    <tr>
				      <td>�޸�����Ϊ��</td>
				      <td>
				        <input type="password" name="password" id="password" size="30" maxlength="40" onblur=""></input>
						<span id="passwordTip"></span>
				      </td>
				   </tr>
				   <tr>
				      <td>����ȷ�ϣ�</td>
				      <td>
				        <input type="password" name="pConfirm" id="pConfirm" size="30" maxlength="40"  onblur="checkForPassword(document.getElementById('password'),this)"></input>
						<span id="pConfirmTip"></span>
				      </td>
				   </tr>
<!-- 				   <tr>
				    <td>����</td>
				      <td>
				        <select name="level" id="level">
				          <option value="0"  ${admin.level==0?"selected":""} }>��������Ա</option>
				          <option value="1"  ${admin.level==1?"selected":""}>��ͨ����Ա</option>
				        </select>
				      </td>
				   </tr> -->
				   <tr>
				      <td>�����ַ��</td>
				      <td>
				        <input type="text" name="email" id="email" size="50" value="${admin.email }" maxlength="100" onblur="checkForEmail(this)"></input>
						<span id="emailTip"></span>
				      </td>
				   </tr>
				   <tr>
				      <td>�绰��</td>
				      <td>
				        <input type="text" name="phone" id="phone" size="30" value="${admin.phone}" maxlength="20" onblur="checkForPhone(this)"></input>
						<span id="phoneTip"></span>
				      </td>
				   </tr>
				  
				   <tr>
						<td>˵����</td>
						<td>
							<textarea name="_desc" rows="10" cols="50">${admin._desc}</textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2">
							<input type="submit" value="ȷ��" >
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
	   <a href="AdminArticleAction">���ص���ҳ��</a>
	 </center>
	 
  </body>
</html>
