package com.gxa.xb.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.User;

public class userMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		User user =new  User();
		try {
			user.setUserId(rs.getInt("userId"));
			user.setUserName(rs.getString("userName"));
			user.setUserPwd(rs.getString("userPwd"));
			user.setUserRealName(rs.getString("userPwd"));
			user.setUserAddress(rs.getString("userAddress"));
			user.setUserTel(rs.getString("userTel"));
			user.setUserEmail(rs.getString("userEmail"));
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
