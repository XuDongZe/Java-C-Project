package com.gxa.xb.pojo;
/**
 * 用户实体类
 * @author root
 *
 */
public class UserInfo {
	
	private int userId ;
	
	private String userName ;
	
	private String userPwd ;

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

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public UserInfo(int userId, String userName, String userPwd) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
	}
	public UserInfo() {
		super();
		
	}
	public UserInfo(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}
	
}
