package com.gxa.xb.Dao;

import java.util.List;

import com.gxa.xb.pojo.Type;

public interface typeDao {
	/**
	 * 查询所有类别
	 * @return
	 */
	public List<Type> findAllTypes();
	
	/**
	 * 根据类别Id查找对应类别
	 * @param typeId
	 * @return
	 */
	public Type findTypeByTypeId(int typeId);
	
	/**
	 * 添加新的类别
	 * @param typeName
	 * @return
	 */
	public int addType(String typeName);
	
	/**
	 * 根据类别ID删除类别
	 * @param typeId
	 * @return
	 */
	public int deleteType(int typeId);
}
