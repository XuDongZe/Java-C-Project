package com.gxa.xb.Dao.impl;

import com.gxa.xb.Dao.UserDao;
import com.gxa.xb.Util.JDBCUtil;
import com.gxa.xb.pojo.UserInfo;
import com.gxa.xb.pojo.mapping.UserInfoMapping;

public class UserDaoImpl implements UserDao {

	@Override
	public UserInfo selectUserByUserId(int userId) {
		// TODO Auto-generated method stub
		String sql ="select * from userInfo where userId = ?";
		
		return (UserInfo) JDBCUtil.selectOneQuery(sql, new UserInfoMapping(), userId);
	}

	@Override
	public UserInfo userLogin(UserInfo user) {
		// TODO Auto-generated method stub
		String sql = "select * from userInfo where userName = ? and userPwd = ?";
		UserInfo userInfo = (UserInfo) JDBCUtil.selectOneQuery(sql, new UserInfoMapping(), user.getUserName(),user.getUserPwd());
		if(userInfo != null){
			return userInfo;
		}
		return null;
	}

}
