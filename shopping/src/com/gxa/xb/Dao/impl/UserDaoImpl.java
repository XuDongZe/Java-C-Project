package com.gxa.xb.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.mapping.userMapping;
import com.gxa.xb.pojo.User;
import com.gxa.xb.Dao.userDao;
import com.gxa.xb.Util.JDBCUtil;

public class userDaoImpl implements userDao {

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		String sql="select * from user";
		List<User> userlist = new ArrayList<User>();
		userlist=JDBCUtil.executeQuery(sql, new userMapping());
		return userlist;
	}

	@Override
	public User findUserByUserId(int userId) {
		// TODO Auto-generated method stub
		String sql ="select * from user where userId = ?";	
		return (User) JDBCUtil.executeQueryOne(sql, new userMapping(), userId);
	}

	@Override
	public int userReg(User user) {
		// TODO Auto-generated method stub
		String sql = "insert into user(userName,userPwd) values(?,?)";
		return JDBCUtil.executeUpdate(sql,user.getUserName(),user.getUserPwd());
	}

	@Override
	public int modifyUser(User user) {
		// TODO Auto-generated method stub
		String sql = "UPDATE user SET userPwd =?, userRealName = ?,userAddress=?,userTel=?,userEmail=? WHERE userId = ?";
		return JDBCUtil.executeUpdate(sql, user.getUserPwd(),user.getUserRealName(),user.getUserAddress(),user.getUserTel(),user.getUserEmail(),user.getUserId());
	}

	@Override
	public User userLogin(User user) {
		// TODO Auto-generated method stub
		String sql = "select * from user where userName = ? and userPwd = ?";
		User users = (User) JDBCUtil.executeQueryOne(sql, new userMapping(), user.getUserName(),user.getUserPwd());
		if(users != null){
			return users;
		}
		return null;
	}

	@Override
	public User userLogout(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUserName(String name){
		String sql = "select * from user where userName = ?";
		return (User)JDBCUtil.executeQueryOne(sql, new userMapping(), name);
	}

}
