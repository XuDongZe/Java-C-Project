package com.gxa.xb.Dao.Impl;

import java.util.ArrayList;
import java.util.List;

import com.gxa.xb.Dao.adminDao;
import com.gxa.xb.Util.JDBCUtil;
import com.gxa.xb.mapping.adminMapping;
import com.gxa.xb.mapping.userMapping;
import com.gxa.xb.pojo.Admin;
import com.gxa.xb.pojo.Book;
import com.gxa.xb.pojo.User;

public class adminDaoImpl implements adminDao {
	
	public List<Admin> findAllAdmins()
	{
		String sql="select * from admin";
		List<Admin> adminlist = new ArrayList<Admin>();
		adminlist=JDBCUtil.executeQuery(sql, new adminMapping());
		return adminlist;
	}
	/**
	 * 根据ID查找管理员
	 */
	public Admin findAdminByAdminId(int AdminId)
	{
		String sql ="select * from admin where adminId = ?";	
		return (Admin) JDBCUtil.executeQueryOne(sql, new adminMapping(), AdminId);
	}
	
	/**
	 * 增加管理员信息
	 * @param admin
	 * @return
	 */
	public int addAdmins(Admin admin)
	{
		String sql="insert into admin(adminName,adminPwd) values(?,?)";
		return JDBCUtil.executeUpdate(sql, admin.getAdminName(),admin.getAdminPwd());
	}
	
	/**
	 * 删除管理员信息
	 * @param adminId
	 * @return
	 */
	public int deleteAdmins(String name)
	{
		String sql="delete from admin where adminName=?";
		return JDBCUtil.executeUpdate(sql, name);
	}
	/**
	 * 修改管理员信息
	 * @param 
	 * @param admin
	 * @return
	 */
	public int modifyAdmins(Admin admin,String name)
	{
		String sql="update admin set adminName=?,adminPwd=? where adminName=?";
		return JDBCUtil.executeUpdate(sql,admin.getAdminName(),admin.getAdminPwd(),name);
	}
	/**
	 * 
	 * @param userID
	 * @return
	 */
	public int deleteusers(String name)
	{
		String sql = "delete from user where userName = ?";
		return JDBCUtil.executeUpdate(sql,name);
		
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	public Admin findAdminByAdminName(String AdminName)
	{
		String sql = "select * from admin where adminName=?";
		return (Admin)JDBCUtil.executeQueryOne(sql, new adminMapping(), AdminName);
    }
	
	@Override
	public Admin adminLogin(Admin admin) {
		// TODO Auto-generated method stub
		String sql = "select * from admin where adminName = ? and adminPwd = ?";
		Admin admin1 = (Admin) JDBCUtil.executeQueryOne(sql, new adminMapping(), admin.getAdminName(),admin.getAdminPwd());
		if(admin1 != null){
			return admin1;
		}
		return null;
	}
}