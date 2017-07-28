package com.IotCloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.model.Admin;
import com.IotCloud.model.Student;
import com.IotCloud.model.Test;
import com.IotCloud.util.CommonUtil;
import com.IotCloud.util.PasswordUtil;

@Component("adminService")
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private TestService testService;
	
	@Autowired 
	private StudentService studentService;

	public boolean insertAdmin(String adminId, String userName, String userPasswd, int authority, String orgName,
			String areaCode) {
		// 不重复添加管理员
		Admin admin = adminDao.getAdminByUserName(userName);
		if (admin == null) {
			return adminDao.insertAdmin(adminId, userName, userPasswd, authority, orgName, areaCode);
		} else {
			return false;
		}

	}

	// public boolean deleteAdmin(String userName) {
	// return adminDao.deleteAdmin(userName);
	// }

	public boolean batchDeleteAdmin(List<Admin> adminList) {
		if (CommonUtil.isEmpty(adminList)) {
			return true;
		}
		for(Admin admin : adminList) {
			List<Test> testList = testService.getTestItemList(admin.getAdminId(), null);
			testService.batchDeleteTestItem(admin.getAdminId(), testList);
			List<Student> studentList = studentService.getStudentList(admin.getAdminId(), null, null, null);
			studentService.deleteStudents(admin.getAdminId(), studentList);
		}
		return adminDao.deleteAdmins(adminList);
	}

	public Admin validatePassword(String userName, String inputPasswd) {
		Admin admin = adminDao.getAdminByUserName(userName);
		if (admin == null) {
			return null;
		}
		String passwdDigest = PasswordUtil.generatePassword(inputPasswd);
		if (passwdDigest != null && passwdDigest.equals(admin.getUserPasswd())) {
			return admin;
		} else {
			return null;
		}
	}

	public int updatePassword(String adminId, String oldPasswd, String newPasswd) {
		if (newPasswd == null || newPasswd.isEmpty()) {
			// 新密码格式不正确
			return 13;
		}
		return adminDao.updatePassword(adminId, oldPasswd, newPasswd);
	}

	public List<Admin> getAdminList(String adminId) {
		return adminDao.getAdminList(adminId);
	}

	public int resetPassword(String userName) {
		return adminDao.resetPassword(userName);
	}
	
	public boolean updateAdminInfo(String adminId, String orgName, String areaCode) {
		return adminDao.updateAdminInfo(adminId, orgName, areaCode);
	}
}
