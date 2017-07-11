package com.IotCloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.AdminDao;


@Component("adminService")
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public boolean insertAdmin(String userName, String userPasswd, int authority, String schoolId) {
		return adminDao.insertAdmin(userName, userPasswd, authority, schoolId);
	}
	
	public boolean deleteAdmin(String userName) {
		return adminDao.deleteAdmin(userName);
	}
}
