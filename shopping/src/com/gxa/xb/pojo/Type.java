package com.gxa.xb.pojo;
/*
 * 图书类别实体类
 */
public class Type {
	private int typeId;
	
	private String typeName;  //类别名称


	public int getTypeId() {
		return typeId;
	}


	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public Type() {
		super();
	}


	public Type(int typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}
	
	public Type(String typeName){
		super();
		this.typeName = typeName;
	}
}
