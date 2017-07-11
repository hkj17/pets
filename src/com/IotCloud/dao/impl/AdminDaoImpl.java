package com.IotCloud.dao.impl;

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
	public boolean insertAdmin(String userName, String userPasswd, int authority, String schoolId) {
		try {
			baseDao.updateBySql(Hql.ADD_ADMIN, CommonUtil.generateRandomUUID(), userName,
					PasswordUtil.generatePassword(userPasswd), authority, schoolId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAdmin(String userName) {
		baseDao.updateBySql(Hql.DELETE_ADMIN_BY_USER_NAME, userName);
		return true;
	}
}
