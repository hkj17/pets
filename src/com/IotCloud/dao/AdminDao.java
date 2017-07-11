package com.IotCloud.dao;

import com.IotCloud.model.Admin;

/**
 * 记录跟管理员功能相关的DAO层
 * @author 胡可及
 *
 */
public interface AdminDao {
	public Admin getAdminByUserName(String userName);
	public boolean insertAdmin(String userName, String userPasswd, int authority, String schoolId);
	public boolean deleteAdmin(String userName);
}
