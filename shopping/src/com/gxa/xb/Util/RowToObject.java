package com.gxa.xb.Util;

import java.sql.ResultSet;

/**
 * 映射接口
 * @author root
 *
 */
public interface RowToObject {
	
	public Object makeRowToObject(ResultSet rs);
}
