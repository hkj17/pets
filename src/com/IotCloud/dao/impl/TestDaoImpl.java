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
public class TestDaoImpl implements TestDao {

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
		if (item == null) {
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

//	@Override
//	public boolean deleteTestItem(String adminId, String itemId) {
//		Test test = getTestItemByAdminAndItemId(adminId, itemId);
//		if (test == null) {
//			return false;
//		} else {
//			testBaseDao.delete(test);
//			return true;
//		}
//	}
	
	@Override
	public boolean batchDeleteTestItem(List<Test> testList) {
		if(!CommonUtil.isEmpty(testList)) {
			testBaseDao.batchDelete(testList);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Test> getTestItemList(String adminId) {
		return testBaseDao.findByHql(Hql.GET_TEST_ITEM_LIST, adminId);
	}

	@Override
	public boolean editTestType(String adminId, String itemId, int type) {
		Test test = getTestItemByAdminAndItemId(adminId, itemId);
		if (test == null) {
			return false;
		} else {
			test.setType(type);
			testBaseDao.update(test);
			return true;
		}
	}

	@Override
	public boolean addEvaluation(List<Evaluation> evalList) {
		evalBaseDao.batchAdd(evalList);
		return true;
	}
	
	@Override
	public boolean deleteEvaluation(List<Evaluation> evalList) {
		if(CommonUtil.isEmpty(evalList)) {
			return false;
		}
		evalBaseDao.batchDelete(evalList);
		return true;
	}

	@Override
	public boolean clearEvaluation(String adminId, String itemName, int gender) {
		Test test = getTestItemByName(adminId, itemName);
		if (test == null) {
			// 没有该测试项目
			return false;
		}
		List<Evaluation> evals = evalBaseDao.findByHql(Hql.GET_EVAL_LIST_BY_GENDER, test.getTestId(), gender);
		evalBaseDao.batchDelete(evals);
		return true;
	}

	@Override
	public List<Evaluation> getEvaluationList(String testId) {
		return evalBaseDao.findByHql(Hql.GET_EVAL_LIST_BY_TEST_ID, testId);
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

	@Override
	public Test getTestItemByName(String adminId, String itemName) {
		return testBaseDao.getByHql(Hql.GET_TEST_ITEM_BY_NAME, adminId, itemName);
	}

}
