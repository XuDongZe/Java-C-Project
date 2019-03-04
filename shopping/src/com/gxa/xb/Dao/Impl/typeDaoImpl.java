package com.gxa.xb.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.mapping.typeMapping;
import com.gxa.xb.mapping.userMapping;
import com.gxa.xb.pojo.Type;
import com.gxa.xb.pojo.User;
import com.gxa.xb.Dao.typeDao;
import com.gxa.xb.Util.JDBCUtil;

public class typeDaoImpl implements typeDao {

	@Override
	public List<Type> findAllTypes() {
		String sql = "select * from type";
		List<Type> typelist = new ArrayList<Type>();
		typelist=(List)JDBCUtil.executeQuery(sql, new typeMapping());
		return typelist;
	}

	@Override
	public Type findTypeByTypeId(int typeId) {
		// TODO Auto-generated method stub
		String sql = "select * from type where typeId = ?";
		return (Type)JDBCUtil.executeQueryOne(sql, new typeMapping(), typeId);
	}

	@Override
	public int addType(String typeName) {
		// TODO Auto-generated method stub
		String sql="insert into type (typeName) values(?)";
		JDBCUtil.executeUpdate(sql,typeName);
		return 0;
	}

	@Override
	public int deleteType(int typeId) {
		// TODO Auto-generated method stub
		String sql= "delete from type where typeId = ?";
		JDBCUtil.executeUpdate(sql,typeId);
		return 0;
	}
	
	

}
