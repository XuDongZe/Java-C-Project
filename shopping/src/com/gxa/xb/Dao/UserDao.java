package com.gxa.xb.Dao;

import java.util.List;

import com.gxa.xb.pojo.User;

public interface userDao {
	
	/**
	 * 查找所有用户
	 * @return
	 */
	public List<User> findAllUser();
	
	/**
	 * 通过ID查询用户
	 * @param userId
	 * @return
	 */
	public User findUserByUserId(int userId);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public int userReg(User user);
	
	
	/**
	 * 修改用户信息
	 * @param UserId
	 * @return
	 */
	public int modifyUser(User user);
	
	/**
	 * 用户登陆
	 * @param user
	 * @return
	 */
	public User userLogin(User user);
	
	/**
	 * 用户注销
	 * @param user
	 * @return
	 */
	public User userLogout(User user);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserByUserName(String userName);
}
