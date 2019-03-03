package com.gxa.xb.pojo;

/**
 * 用户详情
 * @author root
 *
 */
public class UserDetail {
	private int userDetailId;
	
	private String userDetailName;
	
	private String userDetailTel;
	
	private String userDetailAddress; 
	
	private UserInfo user ;

	public int getUserDetailId() {
		return userDetailId;
	}

	public void setUserDetailId(int userDetailId) {
		this.userDetailId = userDetailId;
	}

	public String getUserDetailName() {
		return userDetailName;
	}

	public void setUserDetailName(String userDetailName) {
		this.userDetailName = userDetailName;
	}

	public String getUserDetailTel() {
		return userDetailTel;
	}

	public void setUserDetailTel(String userDetailTel) {
		this.userDetailTel = userDetailTel;
	}

	public String getUserDetailAddress() {
		return userDetailAddress;
	}

	public void setUserDetailAddress(String userDetailAddress) {
		this.userDetailAddress = userDetailAddress;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public UserDetail(String userDetailName, String userDetailTel,
			String userDetailAddress) {
		super();
		this.userDetailName = userDetailName;
		this.userDetailTel = userDetailTel;
		this.userDetailAddress = userDetailAddress;
	}
	
	public UserDetail() {
		super();
		
	}
	
	
}
