package com.gxa.xb.pojo;
/*
 * 用户实体类
 */
public class User {
	//登陆信息
	private int userId;
	
	private String userName; //用户名
	
	private String userPwd;//用户密码
	
	//个人信息
	private String userRealName; //用户姓名
	
	private String userAddress;//地址
	
	private String userTel;  //电话
	
	private String userEmail; //邮箱


	

	public int getUserId() {
		return userId;
	}




	public void setUserId(int userId) {
		this.userId = userId;
	}




	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getUserPwd() {
		return userPwd;
	}




	public void setUserPwd(String uesrPwd) {
		this.userPwd = uesrPwd;
	}




	public String getUserRealName() {
		return userRealName;
	}




	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}




	public String getUserAddress() {
		return userAddress;
	}




	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}




	public String getUserTel() {
		return userTel;
	}




	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}




	public String getUserEmail() {
		return userEmail;
	}




	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public User(int userId,String userName, String userPwd,
			String userRealName, String userAddress, String userTel,
			String userEmail){
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userRealName = userRealName;
		this.userAddress = userAddress;
		this.userTel = userTel;
		this.userEmail = userEmail;
	}

	public User(String userName, String userPwd,
			String userRealName, String userAddress, String userTel,
			String userEmail) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
		this.userRealName = userRealName;
		this.userAddress = userAddress;
		this.userTel = userTel;
		this.userEmail = userEmail;
	}
	

	public User(String userName,String userPwd)
	{
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}
	

	public User() {
		super();
	}
	
	
}
