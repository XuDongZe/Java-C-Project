package com.gxa.xb.Util;

import java.sql.ResultSet;

/*
 * 映射接口
 */
public interface RowToObject {
	
	public Object makeRowToObject(ResultSet rs);
}
