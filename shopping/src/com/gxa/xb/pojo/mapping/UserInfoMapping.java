package com.gxa.xb.pojo.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.UserInfo;

/**
 * 对象映射
 * @author root
 *
 */
public class UserInfoMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
			UserInfo user = new UserInfo();
		
		try {
			user.setUserId(rs.getInt("userId"));
			user.setUserName(rs.getString("userName"));
			user.setUserPwd(rs.getString("userPwd"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}
	
}
