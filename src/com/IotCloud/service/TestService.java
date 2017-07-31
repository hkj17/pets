package com.IotCloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.TestDao;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;
import com.IotCloud.poi.EvalXmlReader;
import com.IotCloud.poi.PoiListRecord;
import com.IotCloud.util.CommonUtil;

@Component("testService")
@Service
public class TestService {

	@Autowired
	private TestDao testDao;

	private static Logger logger = Logger.getLogger(TestService.class);

	public List<Item> getItemList() {
		return testDao.getItemList();
	}

	public List<Item> getUnaddedItemList(String adminId) {
		return testDao.getUnaddedItemList(adminId);
	}

	public boolean addItem(String itemName) {
		return testDao.addItem(itemName);
	}

	public int addTestItem(String adminId, String itemId, int type) {
		Test test = testDao.getTestItemByItemId(adminId, itemId);
		if (test != null) {
			// 考试项目已经存在
			return 11;
		}
		return testDao.addTestItem(adminId, itemId, type);
	}

	public boolean batchDeleteTestItem(String adminId, List<Test> testList) {
		if (CommonUtil.isEmpty(testList)) {
			return true;
		}
		// 去掉所有不属于该管理员的测试项目
		Iterator<Test> iter = testList.iterator();
		while (iter.hasNext()) {
			Test test = iter.next();
			if (test == null || test.getAdminId() == null || !test.getAdminId().equals(adminId)) {
				iter.remove();
			} else {
				// 删除与考试项目对应的评分标准
				List<Evaluation> evalList = testDao.getEvaluationList(test.getTestId());
				testDao.deleteEvaluations(evalList);
			}
		}
		return testDao.deleteTestItems(testList);
	}

	public List<Test> getTestItemList(String adminId, Integer type) {
		return testDao.getTestItemList(adminId, type);
	}

	public boolean editTestType(String adminId, String itemId, int type) {
		return testDao.editTestType(adminId, itemId, type);
	}

	public String loadEvalFromXml(String adminId, InputStream input) {
		EvalXmlReader reader = new EvalXmlReader();
		try {
			PoiListRecord<Evaluation> recordList = reader.loadFromXml(adminId, input);
			List<Evaluation> evalList = recordList.getRecordList();
			if (evalList != null) {
				Test test = new Test();
				for (Evaluation eval : evalList) {
					// get overridden itemName
					String itemName = eval.getTestId();
					if (test.getItem() == null || !test.getItem().getItemName().equals(itemName)) {
						test = testDao.getTestItemByName(adminId, itemName);
						if (test == null) {
							return "添加失败，没有考试项目" + itemName;
						}
					}

					if ((eval.getType() & test.getType()) == 0) {
						return "添加失败，" + test.getItem().getItemName() + "没有" + (eval.getType() == 1 ? "男" : "女")
								+ "生考项";
					}
					eval.setTestId(test.getTestId());
				}
				testDao.addEvaluations(recordList.getRecordList());
			}
			return recordList.getMessage();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.error("IO异常", ioe);
			return "添加失败，IO异常";
		}
	}

	public boolean clearEvaluation(String adminId, String itemName, int gender) {
		return testDao.clearEvaluation(adminId, itemName, gender);
	}

	public Test getTestById(String testId) {
		return testDao.getTestById(testId);
	}

	public List<Evaluation> getEvaluationList(String adminId, Test test, int type) {
		// 如果管理员没有这个测试项目，则不能查看测试项目的评分标准
		if (test.getAdminId() == null || !test.getAdminId().equals(adminId)) {
			return null;
		}

		// 没有对应的男生项或者女生项
		if ((test.getType() & type) == 0) {
			return null;
		}

		return testDao.getEvaluationList(test.getTestId(), type);
	}

	public String getUnitByTestItem(Test test) {
		Item item = test.getItem();
		if (item == null) {
			return null;
		}
		return item.getUnit();
	}
}
