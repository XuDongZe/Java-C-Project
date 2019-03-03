// JavaScript Document
function test(){
 var check = window.document.getElementById("chekbox").checked;
	if(check == true){
window.document.getElementById("register_bootm").style.display="block";
		}else{
			window.document.getElementById("register_bootm").style.display="none";
			}
}