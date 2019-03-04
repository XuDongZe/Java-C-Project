package com.gxa.xb.Dao;

import java.util.List;

import com.gxa.xb.pojo.Admin;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.User;

public interface adminDao {
	/**
	 * 查找所有管理员
	 * @return
	 */
	public List<Admin> findAllAdmins();
	
	/**
	 * 根据ID查找管理员
	 */
	public Admin findAdminByAdminId(int AdminId);
	
	/**
	 * 增加管理员信息
	 * @param admin
	 * @return
	 */
	public int addAdmins(Admin admin);
	
	/**
	 * 删除管理员信息
	 * @param adminId
	 * @return
	 */
	public int deleteAdmins(String name);
	
	/**
	 * 修改管理员信息
	 * @param 
	 * @param admin
	 * @return
	 */
	public int modifyAdmins(Admin admin,String name);
	
	/**
	 * 删除用户
	 * @param AdminName
	 * @return
	 */
	public int deleteusers(String name);
	
	/**
	 * 查找管理员
	 * @param AdminsName
	 * @return
	 */
	public Admin findAdminByAdminName(String AdminName);
	
	/**
	 * 管理员登陆
	 * @param admin
	 * @return
	 */
	public Admin adminLogin(Admin admin);
}
