package com.IotCloud.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.IotCloud.constant.Hql;
import com.IotCloud.dao.BaseDao;
import com.IotCloud.dao.TestDao;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;
import com.IotCloud.util.CommonUtil;


@Repository("testDao")
public class TestDaoImpl implements TestDao{

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Autowired
	private BaseDao<Item> itemBaseDao;
	
	@Autowired
	private BaseDao<Test> testBaseDao;
	
	@Autowired
	private BaseDao<Evaluation> evalBaseDao;
	
	@Override
	public List<Item> getItemList() {
		return itemBaseDao.findByHql(Hql.GET_ITEM_LIST);
	}

	@Override
	public boolean addItem(String itemName) {
		Item item = new Item();
		item.setItemId(CommonUtil.generateRandomUUID());
		item.setItemName(itemName);
		itemBaseDao.add(item);
		return true;
	}

	@Override
	public int addTestItem(String adminId, String itemId, int type) {
		Item item = getItemById(itemId);
		if(item == null) {
			return 12;
		}
		Test test = new Test();
		test.setTestId(CommonUtil.generateRandomUUID());
		test.setAdminId(adminId);
		test.setItem(item);
		test.setType(type);
		testBaseDao.add(test);
		return 0;
	}

	@Override
	public boolean deleteTestItem(String adminId, String itemId) {
		Test test = getTestItemByAdminAndItemId(adminId, itemId);
		if(test==null) {
			return false;
		}else {
			testBaseDao.delete(test);
			return true;
		}
	}

	@Override
	public List<Test> getTestItemList(String adminId) {
		return testBaseDao.findByHql(Hql.GET_TEST_ITEM_LIST, adminId);
	}

	@Override
	public boolean editTestType(String adminId, String itemId, int type) {
		Test test = getTestItemByAdminAndItemId(adminId, itemId);
		if(test==null) {
			return false;
		}else {
			test.setType(type);
			testBaseDao.update(test);
			return true;
		}
	}

	@Override
	public String addEvaluation(String testId, int gender, double lowerBound, double upperBound, String unit,
			double point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteEvaluation(String testId, int gender, double point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editEvaluation(String evalId, double lowerBound, double upperBound, double point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Evaluation> getEvaluationList(String testId, int gender) {
		return evalBaseDao.findByHql(Hql.GET_EVAL_LIST_BY_TEST_ID, testId, gender);
	}
	
	@Override
	public Item getItemById(String itemId) {
		return itemBaseDao.getByHql(Hql.GET_ITEM_BY_ID, itemId);
	}
	
	@Override
	public Test getTestItemByAdminAndItemId(String adminId, String itemId) {
		return testBaseDao.getByHql(Hql.GET_TEST_BY_ADMIN_AND_ITEM_ID, adminId, itemId);
	}

	@Override
	public Test getTestById(String testId) {
		return testBaseDao.getByHql(Hql.GET_TEST_BY_ID, testId);
	}

}
