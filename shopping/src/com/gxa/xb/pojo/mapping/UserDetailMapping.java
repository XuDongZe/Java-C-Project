package com.gxa.xb.pojo.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Dao.UserDao;
import com.gxa.xb.Dao.impl.UserDaoImpl;
import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.UserDetail;

public class UserDetailMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		UserDetail detail = new UserDetail();
		UserDao udao = new UserDaoImpl();
		try {
			detail.setUserDetailId(rs.getInt("detail"));
			detail.setUserDetailName(rs.getString("userDetailName"));
			detail.setUserDetailTel(rs.getString("userDetailTel"));
			detail.setUserDetailAddress(rs.getString("userDetailAddress"));
			detail.setUser(udao.selectUserByUserId(rs.getInt("userId")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return detail;
	}

}
