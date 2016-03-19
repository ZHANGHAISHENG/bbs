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
		$("#nameTip").html("<font color='red'>�û�������Ϊ��</font>");
	}else{
		$("#nameTip").html("<font color='blue'>��Ч</font>");
	}
	
	return b;
}



function checkForPassword(pwd,comfirm){


	if(pwd.value.match(/^\s*\s*$/)){
		$("#passwordTip").html("<font color='red'>���벻��Ϊ��</font>");
		return false;
	}

	
	if(pwd.value!=comfirm.value){
		$("#passwordTip").html("<font color='red'>�������벻һ��</font>");
		$("#pConfirmTip").html("<font color='red'>�������벻һ��</font>");
		return false;
	}
	   $("#passwordTip").html("<font color='blue'>��Ч</font>");
	   $("#pConfirmTip").html("<font color='blue'>��Ч</font>");
	return true;
}


function checkForEmail(email){
	
	
	if(email.value.replace(/(^\s+)|(\s+$)/,"")==""){
		  $("#emailTip").html("<font color='blue'>��Ч</font>");
	    return true;
	}
	
	if(!email.value.match(/^[\w.-]+@[\w.-]+\.[\w.-]+$/)){
		$("#emailTip").html("<font color='red'>��ʽ����</font>");
		return false;
	}
	  $("#emailTip").html("<font color='blue'>��Ч</font>");
	return true;
}

function checkForPhone(phone){
	if(phone.value.search(/\D/)!=-1){
		$("#phoneTip").html("<font color='red'>��ʽ����</font>");
		return false;
	}
    	$("#phoneTip").html("<font color='blue'>��Ч</font>");
	return true;
	
}

function checkForTitle(title){
	
	var b=isValueNull(title);
	if(!b){
		$("#titleTip").html("<font color='red'>���ⲻ��Ϊ��</font>");
		return false;
	}
		$("#titleTip").html("<font color='blue'>��Ч</font>");
		return true;
	
}


function checkForContent(content){

	var b=isValueNull(content);
	if(!b){
		$("#contentTip").html("<font color='red'>���ݲ���Ϊ��</font>");
		return false;
	}
		$("#contentTip").html("<font color='blue'>��Ч</font>");
		return true;
	
}



