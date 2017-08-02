package com.IotCloud.pets.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.IotCloud.pets.constant.Hql;
import com.IotCloud.pets.dao.BaseDao;
import com.IotCloud.pets.dao.TestDao;
import com.IotCloud.pets.model.Evaluation;
import com.IotCloud.pets.model.Item;
import com.IotCloud.pets.model.Test;
import com.IotCloud.pets.util.CommonUtil;

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
	public List<Item> getUnaddedItemList(String adminId) {
		return itemBaseDao.findByHql(Hql.GET_UNADDED_ITEM_LIST, adminId);
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

	// @Override
	// public boolean deleteTestItem(String adminId, String itemId) {
	// Test test = getTestItemByAdminAndItemId(adminId, itemId);
	// if (test == null) {
	// return false;
	// } else {
	// testBaseDao.delete(test);
	// return true;
	// }
	// }

	@Override
	public boolean deleteTestItems(List<Test> testList) {
		testBaseDao.batchDelete(testList);
		return true;
	}

	@Override
	public List<Test> getTestItemList(String adminId, Integer type) {
		List<Test> testList = testBaseDao.findByHql(Hql.GET_TEST_ITEM_LIST, adminId);
		if (type == null || CommonUtil.isEmpty(testList)) {
			return testList;
		} else {
			Iterator<Test> iter = testList.iterator();
			while (iter.hasNext()) {
				Test test = iter.next();
				if ((test.getType() & type) == 0) {
					iter.remove();
				}
			}
			return testList;
		}
	}

	@Override
	public boolean editTestType(String adminId, String itemId, int type) {
		Test test = getTestItemByItemId(adminId, itemId);
		if (test == null) {
			return false;
		} else {
			test.setType(type);
			testBaseDao.update(test);
			return true;
		}
	}

	@Override
	public boolean addEvaluations(List<Evaluation> evalList) {
		evalBaseDao.batchAdd(evalList);
		return true;
	}

	@Override
	public boolean deleteEvaluations(List<Evaluation> evalList) {
		if (CommonUtil.isEmpty(evalList)) {
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
	public List<Evaluation> getEvaluationList(String testId, int type) {
		return evalBaseDao.findByHql(Hql.GET_EVAL_LIST_BY_GENDER, testId, type);
	}

	@Override
	public Item getItemById(String itemId) {
		return itemBaseDao.getByHql(Hql.GET_ITEM_BY_ID, itemId);
	}

	@Override
	public Item getItemByName(String itemName) {
		return itemBaseDao.getByHql(Hql.GET_ITEM_BY_NAME, itemName);
	}

	@Override
	public Test getTestItemByItemId(String adminId, String itemId) {
		return testBaseDao.getByHql(Hql.GET_TEST_BY_ITEM_ID, adminId, itemId);
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
