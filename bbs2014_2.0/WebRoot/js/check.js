function isValueNull(obj){

	if(obj.value==null){
	  return false;
	}
	if(obj.value.match(/^\s*\s*$/)){
		return false;
	}
	
	return true;
}

function checkForName(obj){
	
	var b=isValueNull(obj);
	if(!b){
		$("#nameTip").html("<font color='red'>用户名不能为空</font>");
	}else{
		$("#nameTip").html("<font color='blue'>有效</font>");
	}
	
	return b;
}



function checkForPassword(pwd,comfirm){


	if(pwd.value.match(/^\s*\s*$/)){
		$("#passwordTip").html("<font color='red'>密码不能为空</font>");
		return false;
	}

	
	if(pwd.value!=comfirm.value){
		$("#passwordTip").html("<font color='red'>两次密码不一致</font>");
		$("#pConfirmTip").html("<font color='red'>两次密码不一致</font>");
		return false;
	}
	   $("#passwordTip").html("<font color='blue'>有效</font>");
	   $("#pConfirmTip").html("<font color='blue'>有效</font>");
	return true;
}


function checkForEmail(email){
	
	
	if(email.value.replace(/(^\s+)|(\s+$)/,"")==""){
		  $("#emailTip").html("<font color='blue'>有效</font>");
	    return true;
	}
	
	if(!email.value.match(/^[\w.-]+@[\w.-]+\.[\w.-]+$/)){
		$("#emailTip").html("<font color='red'>格式不对</font>");
		return false;
	}
	  $("#emailTip").html("<font color='blue'>有效</font>");
	return true;
}

function checkForPhone(phone){
	if(phone.value.search(/\D/)!=-1){
		$("#phoneTip").html("<font color='red'>格式不对</font>");
		return false;
	}
    	$("#phoneTip").html("<font color='blue'>有效</font>");
	return true;
	
}

function checkForTitle(title){
	
	var b=isValueNull(title);
	if(!b){
		$("#titleTip").html("<font color='red'>标题不能为空</font>");
		return false;
	}
		$("#titleTip").html("<font color='blue'>有效</font>");
		return true;
	
}


function checkForContent(content){

	var b=isValueNull(content);
	if(!b){
		$("#contentTip").html("<font color='red'>内容不能为空</font>");
		return false;
	}
		$("#contentTip").html("<font color='blue'>有效</font>");
		return true;
	
}



