package com.IotCloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.dao.TestDao;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;
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

	// public boolean deleteTestItem(String adminId, String itemId) {
	// return testDao.deleteTestItem(adminId, itemId);
	// }

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

	@SuppressWarnings("deprecation")
	public String loadEvalFromXml(String adminId, InputStream input) {
		try {
			Workbook wb = new HSSFWorkbook(input);
			Test test = new Test();
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			if (rows.hasNext()) {
				rows.next();
			} else {
				logger.warn("添加失败，excel文档为空");
				return "添加失败，excel文档为空";
			}

			List<Evaluation> evalList = new ArrayList<Evaluation>();
			int rowNum = 0;
			while (rows.hasNext()) {
				Row row = rows.next();
				rowNum++;
				Iterator<Cell> cells = row.cellIterator();
				Evaluation eval = new Evaluation();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int cellNo = cell.getColumnIndex();
					eval.setEvalId(CommonUtil.generateRandomUUID());
					if (cellNo == 0) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							String itemName = cell.getStringCellValue();
							if (test.getItem() == null || !test.getItem().getItemName().equals(itemName)) {
								test = testDao.getTestItemByName(adminId, itemName);
								if (test == null) {
									return "添加失败，没有考试项目" + itemName;
								}
							}
							eval.setTestId(test.getTestId());
						} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
							eval.setTestId(test.getTestId());
						} else {
							logger.error("第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，项目名称不是字符串");
							return "添加失败，项目名称不是字符串";
						}
					} else if (cellNo == 1) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							int currType = 0;
							if ("男".equals(cell.getStringCellValue())) {
								currType = 1;
								eval.setType(currType);
							} else if ("女".equals(cell.getStringCellValue())) {
								currType = 2;
								eval.setType(currType);
							} else {
								logger.error("第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，性别必须是男或者女");
								return "添加失败，性别必须是男或者女";
							}

							if ((currType & test.getType()) == 0) {
								return "添加失败，" + test.getItem().getItemName() + "没有" + cell.getStringCellValue()
										+ "生考项";
							}
						} else {
							logger.error("第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，性别不是字符串");
							return "添加失败，性别不是字符串";
						}
					} else if (cellNo == 2) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							eval.setLowerBound(cell.getNumericCellValue());
						} else {
							logger.error("第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，最低成绩不是数字");
							return "添加失败，最低成绩不是数字";
						}
					} else if (cellNo == 3) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							eval.setUpperBound(cell.getNumericCellValue());
						} else {
							logger.error("第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，最高成绩不是数字");
							return "添加失败，最高成绩不是数字";
						}
						// } else if (cellNo == 4) {
						// if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						// eval.setUnit(cell.getStringCellValue());
						// } else {
						// return "添加失败，单位不是字符串";
						// }
					} else if (cellNo == 4) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							eval.setPoint(cell.getNumericCellValue());
						} else {
							logger.error("第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，分值不是数字");
							return "添加失败，分值不是数字";
						}
					} else {
						break;
					}

				}
				evalList.add(eval);
			}
			testDao.addEvaluations(evalList);
			return "添加成功";
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("评分标准添加失败", e);
			return "添加失败，文件格式错误";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("评分标准添加失败", e);
			return "添加失败，数据库错误";
		}
	}

	public boolean clearEvaluation(String adminId, String itemName, int gender) {
		return testDao.clearEvaluation(adminId, itemName, gender);
	}

	public Test getTestById(String testId) {
		return testDao.getTestById(testId);
	}

	public List<Evaluation> getEvaluationList(String adminId, Test test, int type) {
		// Test test = testDao.getTestById(testId);
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
