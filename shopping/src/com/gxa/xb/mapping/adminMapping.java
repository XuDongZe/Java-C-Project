package com.gxa.xb.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.Admin;

public class adminMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		Admin admin = new Admin();
		try {
			admin.setAdminId(rs.getInt("adminId"));
			admin.setAdminName(rs.getString("adminName"));
			admin.setAdminPwd(rs.getString("adminPwd"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
}
