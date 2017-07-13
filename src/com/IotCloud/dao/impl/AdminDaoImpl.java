package com.IotCloud.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.IotCloud.constant.Hql;
import com.IotCloud.dao.AdminDao;
import com.IotCloud.dao.BaseDao;
import com.IotCloud.model.Admin;
import com.IotCloud.util.CommonUtil;
import com.IotCloud.util.PasswordUtil;

import antlr.StringUtils;

@Repository("adminDao")
public class AdminDaoImpl implements AdminDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	private BaseDao<Admin> baseDao;

	@Override
	public Admin getAdminByUserName(String userName) {
		Admin admin = baseDao.getByHql(Hql.GET_USER_BY_NAME, userName);
		return admin;
	}
	
	@Override
	public boolean insertAdmin(String userName, String userPasswd, int authority, String orgName, String areaCode) {
//		try {
//			baseDao.updateBySql(Hql.ADD_ADMIN, CommonUtil.generateRandomUUID(), userName,
//					PasswordUtil.generatePassword(userPasswd), authority, orgName, areaCode);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
		Admin admin = new Admin();
		admin.setAdminId(CommonUtil.generateRandomUUID());
		admin.setUserName(userName);
		admin.setUserPasswd(PasswordUtil.generatePassword(userPasswd));
		admin.setAuthority(authority);
		admin.setOrgName(orgName);
		admin.setAreaCode(areaCode);
		baseDao.add(admin);
		return true;
	}

	@Override
	public boolean deleteAdmin(String userName) {
		//baseDao.updateBySql(Hql.DELETE_ADMIN_BY_USER_NAME, userName);
		Admin admin = getAdminByUserName(userName);
		if(admin == null) {
			return false;
		}
		baseDao.delete(admin);
		return true;
	}

	@Override
	public List<Admin> getAdminList() {
		return baseDao.findByHql(Hql.GET_ADMIN_LIST);
	}

	@Override
	public int updatePassword(String userName, String oldPasswd, String newPasswd) {
		Admin admin = getAdminByUserName(userName);
		if(admin == null) {
			//用户不存在
			return 11;
		}
		if(oldPasswd==null || !PasswordUtil.generatePassword(oldPasswd).equals(admin.getUserPasswd())) {
			//密码不一致
			return 12;
		}
		admin.setUserPasswd(PasswordUtil.generatePassword(newPasswd));
		baseDao.update(admin);
		return 0;
	}
}
