package com.gxa.xb.pojo;
/*
 * 管理员实体类
 */
public class Admin {
	private int adminId;
	
	private String adminName;  //管理员用户名
	
	private String adminPwd;   //管理员密码

	public int getAmindId() {
		return adminId;
	}


	public int getAdminId() {
		return adminId;
	}


	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public String getAdminPwd() {
		return adminPwd;
	}


	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}


	public Admin() {
		super();
	}


	public Admin(int adminId, String adminName, String adminPwd) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminPwd = adminPwd;
	}
	
	public Admin(String adminName, String adminPwd) {
		super();
		this.adminName = adminName;
		this.adminPwd = adminPwd;
	}
	
}
