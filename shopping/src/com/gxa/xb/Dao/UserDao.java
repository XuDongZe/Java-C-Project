package com.gxa.xb.Dao;

import com.gxa.xb.pojo.UserInfo;

/**
 * 用户操作接口
 * @author root
 *
 */
public interface UserDao {
	/**
	 * 通过ID查询用户
	 * @param userId
	 * @return
	 */
	public UserInfo selectUserByUserId(int userId);
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public UserInfo userLogin(UserInfo user);
}
