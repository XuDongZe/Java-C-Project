// JavaScript Document
function logcheck(){
	var userName = window.document.getElementById("myusn").value;
	var userPwd = window.document.getElementById("mypwd").value;
	if(userName==null || userName==""){
		alert("用户名不可以为空,请重新输入!");
		return false;
	}
	else if(userPwd==null || userPwd==""){
		alert("密码不可以为空,请重新输入!");
		return false;
	}
	else
		return true;

}

function regcheck(){
	var userName = window.document.getElementById("myusn").value;
	var userPwd = window.document.getElementById("mypwd").value;
	var userPwd2 =  window.document.getElementById("mypwd2").value;
	if(userName==null || userName==""){
		alert("用户名不可以为空,请重新输入!");
		return false;
	}
	else if(userPwd.length<6 || userPwd.length > 16 || userPwd==null || userPwd==""){
		alert("密码格式不正确，请重新输入!");
		return false;
	}
	else if(userPwd != userPwd2){
		alert("两次输入密码不一致,请重新输入!");
		return false;
	}
	else{
		return true;
	}
}
function judgenull()
{
	var name = window.document.getElementsByName("bookName");
	var author = window.document.getElementsByName("bookAuthor");
	var pic = window.document.getElementsByName("bookPic");
	var price = window.document.getElementsByName("bookPrice");
	var pub = window.document.getElementsByName("bookPub");
	var desc = window.document.getElementsByName("bookDesc");
	var typeid = window.document.getElementsByName("typeId");
	if(name[0].value==""||author[0].value==""||pic[0].value==""||price[0].value==""||pub[0].value==""||desc[0].value==""||typeid[0].value=="")
		{
		alert("有空项目没有填写哦www");
		return false;
		}
	else
		{
		return true;
		}
	
}
function judgenull1()
{
	var origname = window.document.getElementsByName("bookOrigName");
	var name = window.document.getElementsByName("bookName");
	var author = window.document.getElementsByName("bookAuthor");
	var pic = window.document.getElementsByName("bookPic");
	var price = window.document.getElementsByName("bookPrice");
	var pub = window.document.getElementsByName("bookPub");
	var desc = window.document.getElementsByName("bookDesc");
	var typeid = window.document.getElementsByName("typeId");
	if(origname[0].value==""||name[0].value==""||author[0].value==""||pic[0].value==""||price[0].value==""||pub[0].value==""||desc[0].value==""||typeid[0].value=="")
		{
		alert("有空项目没有填写哦www");
		return false;
		}
	else if(origname[0].value==name[0].value){
		alert("你在爪子，书名和原书名一样了，zz");
		return false;
	}
	else
		{
		return true;
		}
	
}

function judgenull2()
{
	var name = window.document.getElementsByName("adminname");
	var password = window.document.getElementsByName("adminpassword");
	var password2 = window.document.getElementsByName("adminpassword2");
	if(name[0].value==""||password[0].value==""||password2[0].value=="")
		{
		alert("有空项目没有填写哦www");
		return false;
		}
	else if(password[0].value!=password2[0].value){
		alert("你在爪子，两次密码不一样，zz");
		return false;
	}
	else
		{
		return true;
		}
	
}

function judgenull3()
{
	var origname = window.document.getElementsByName("adminOrigname");
	var name = window.document.getElementsByName("adminname");
	var password = window.document.getElementsByName("adminpas");
	if(origname[0].value==""||name[0].value==""||password[0].value=="")
		{
		alert("有空项目没有填写哦www");
		return false;
		}
	else if(origname[0].value==name[0].value){
		alert("你在爪子，两个名字还能一样，zz");
		return false;
	}
	else
		{
		return true;
		}
	
}
