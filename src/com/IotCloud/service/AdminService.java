package com.IotCloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.model.Admin;
import com.IotCloud.util.PasswordUtil;


@Component("adminService")
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public boolean insertAdmin(String userName, String userPasswd, int authority, String schoolId) {
		//不重复添加管理员
		Admin admin = adminDao.getAdminByUserName(userName);
		if(admin==null) {
			return adminDao.insertAdmin(userName, userPasswd, authority, schoolId);
		}else {
			return false;
		}
		
	}
	
	public boolean deleteAdmin(String userName) {
		return adminDao.deleteAdmin(userName);
	}
	
	public boolean validatePassword(String userName, String inputPasswd) {
		Admin admin = adminDao.getAdminByUserName(userName);
		if(admin == null) {
			return false;
		}
		String passwdDigest = PasswordUtil.generatePassword(inputPasswd);
		if(passwdDigest!=null && passwdDigest.equals(admin.getUserPasswd())) {
			return true;
		}else {
			return false;
		}
	}
}
