package com.IotCloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.TestDao;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;

@Component("testService")
@Service
public class TestService {
	
	@Autowired
	private TestDao testDao;
	
	public List<Item> getItemList() {
		return testDao.getItemList();
	}
	
	public boolean addItem(String itemName) {
		return testDao.addItem(itemName);
	}
	
	public int addTestItem(String adminId, String itemId, int type) {
		Test test = testDao.getTestItemByAdminAndItemId(adminId, itemId);
		if(test!=null) {
			//考试项目已经存在
			return 11;
		}
		return testDao.addTestItem(adminId, itemId, type);
	}
	
	public boolean deleteTestItem(String adminId, String itemId) {
		return testDao.deleteTestItem(adminId, itemId);
	}
	
	public List<Test> getTestItemList(String adminId) {
		return testDao.getTestItemList(adminId);
	}
	
	public boolean editTestType(String adminId, String itemId, int type) {
		return testDao.editTestType(adminId, itemId, type);
	}
	
	public List<Evaluation> getEvaluationList(String adminId, String testId, int gender) {
		//如果管理员没有这个测试项目，则不能查看测试项目的评分标准
		Test test = testDao.getTestById(testId);
		if(test == null || test.getAdminId() == null || !test.getAdminId().equals(adminId)) {
			return null;
		}
		return testDao.getEvaluationList(testId, gender);
	}
}
