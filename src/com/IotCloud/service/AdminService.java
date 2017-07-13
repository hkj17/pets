package com.IotCloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.model.Admin;
import com.IotCloud.util.PasswordUtil;

import net.sf.json.JSONObject;


@Component("adminService")
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public boolean insertAdmin(String userName, String userPasswd, int authority, String orgName, String areaCode) {
		//不重复添加管理员
		Admin admin = adminDao.getAdminByUserName(userName);
		if(admin==null) {
			return adminDao.insertAdmin(userName, userPasswd, authority, orgName, areaCode);
		}else {
			return false;
		}
		
	}
	
	public boolean deleteAdmin(String userName) {
		return adminDao.deleteAdmin(userName);
	}
	
	public JSONObject validatePassword(String userName, String inputPasswd) {
		Admin admin = adminDao.getAdminByUserName(userName);
		JSONObject jsonObject = new JSONObject();
		if(admin == null) {
			jsonObject.put("state", 1);
			return jsonObject;
		}
		String passwdDigest = PasswordUtil.generatePassword(inputPasswd);
		if(passwdDigest!=null && passwdDigest.equals(admin.getUserPasswd())) {
			jsonObject.put("state", 0);
			jsonObject.put("adminId", admin.getAdminId());
			jsonObject.put("orgName", admin.getOrgName());
			jsonObject.put("auth", admin.getAuthority());
			return jsonObject;
		}else {
			jsonObject.put("state", 1);
			return jsonObject;
		}
	}
	
	public int updatePassword(String userName, String oldPasswd, String newPasswd) {
		if(newPasswd==null || newPasswd.isEmpty()) {
			//新密码格式不正确
			return 13;
		}
		return adminDao.updatePassword(userName, oldPasswd, newPasswd);
	}
	
	public List<Admin> getAdminList(){
		return adminDao.getAdminList();
	}
}
