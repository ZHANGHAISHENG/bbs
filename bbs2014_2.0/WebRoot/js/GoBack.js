var i=5;
var aim;

function param(place){

	aim=place;
}

function go(){

       i--;
       document.getElementById("num").innerHTML="<font color='red' size='5'>"+i+"秒后实现跳转</font>";
       if(i<=0){
         window.location.href=aim;
         window.clearInterval("go()");
       }
  
 }

window.setInterval("go()", 1000); 




