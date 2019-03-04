package com.gxa.xb.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxa.xb.Util.RowToObject;
import com.gxa.xb.pojo.Type;

public class typeMapping implements RowToObject {

	@Override
	public Object makeRowToObject(ResultSet rs) {
		// TODO Auto-generated method stub
		Type type = new Type();
		try {
			type.setTypeId(rs.getInt("typeId"));
			type.setTypeName(rs.getString("typeName"));
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return type;
	}

}
