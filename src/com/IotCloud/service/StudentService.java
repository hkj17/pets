package com.IotCloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.IotCloud.constant.DefaultValues;
import com.IotCloud.dao.StudentDao;
import com.IotCloud.dao.TestDao;
import com.IotCloud.data.StudentTestResult;
import com.IotCloud.data.TestResult;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Record;
import com.IotCloud.model.Student;
import com.IotCloud.model.Test;
import com.IotCloud.util.CommonUtil;

@Component("studentService")
@Service
public class StudentService {

	@Autowired
	StudentDao studentDao;

	@Autowired
	TestDao testDao;

	@SuppressWarnings("deprecation")
	public String loadStudentsFromXml(String adminId, InputStream input) {
		try {
			Workbook wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			if (rows.hasNext()) {
				rows.next();
			} else {
				return "添加失败，excel文档为空";
			}

			List<Student> studentList = new ArrayList<Student>();
			DecimalFormat df = new DecimalFormat("0");
			while (rows.hasNext()) {
				Row row = rows.next();
				Iterator<Cell> cells = row.cellIterator();
				Student student = new Student();
				while (cells.hasNext()) {
					Cell cell = cells.next();
					int cellNo = cell.getColumnIndex();
					student.setStudentId(CommonUtil.generateRandomUUID());
					student.setAdminId(adminId);

					if (cellNo == 0) {// 学生姓名
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							student.setStudentName(cell.getStringCellValue());
						} else {
							return "添加失败，考生姓名不是字符串";
						}
					} else if (cellNo == 1) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							student.setStudentNo(Integer.parseInt(df.format(cell.getNumericCellValue())));
						} else {
							return "添加失败，考生学号不是数字";
						}
					} else if (cellNo == 2) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							student.setTesterNo(Long.parseLong(df.format(cell.getNumericCellValue())));
						} else {
							return "添加失败，考生考号不是数字";
						}
					} else if (cellNo == 3) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							if ("男".equals(cell.getStringCellValue())) {
								student.setGender(1);
							} else if ("女".equals(cell.getStringCellValue())) {
								student.setGender(2);
							} else {
								return "添加失败，性别必须是男或者女";
							}
						} else {
							return "添加失败，性别不是字符串";
						}
					} else if (cellNo == 4) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							student.setSchoolName(cell.getStringCellValue());
						} else {
							return "添加失败，学校名称不是字符串";
						}
					} else if (cellNo == 5) {
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							student.setClassName(cell.getStringCellValue());
						} else {
							return "添加失败，班级不是字符串";
						}
					} else {
						break;
					}
				}
				studentList.add(student);
			}

			studentDao.addStudents(studentList);
			return "添加成功";
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return "文件格式错误";
		} catch (Exception e) {
			e.printStackTrace();
			return "数据库错误";
		}
	}

	public List<String> getSchoolNameListByAdminId(String adminId) {
		return studentDao.getSchoolNameListByAdminId(adminId);
	}

	public List<String> getClassNameListBySchool(String adminId, String schoolName) {
		return studentDao.getClassNameListBySchool(adminId, schoolName);
	}

	public List<Student> getStudentListByAdminId(String adminId) {
		return studentDao.getStudentListByAdminId(adminId);
	}

	public Student getStudentByTesterNo(String adminId, long testerNo) {
		return studentDao.getStudentByTesterNo(adminId, testerNo);
	}

	public List<TestResult> getRecordListByStudent(Student student, String startTime, String endTime) {
		List<TestResult> testResultList = new ArrayList<TestResult>();
		List<Record> recordList = studentDao.getTestRecordByStudent(student.getAdminId(), student.getTesterNo(),
				startTime, endTime);
		if (CommonUtil.isEmpty(recordList)) {
			return testResultList;
		}
		
		for (Record record : recordList) {
			TestResult testResult = new TestResult();
			Item item = testDao.getItemById(record.getItemId());
			if (item == null) {
				continue;
			}
			testResult.setItemName(item.getItemName());
			testResult.setTestTime(record.getCreatedAt());
			testResult.setResult(record.getResult());
			Test test = testDao.getTestItemByItemId(student.getAdminId(), record.getItemId());
			if (test == null) {
				testResult.setPoint(-1);
				testResultList.add(testResult);
				continue;
			}
			List<Evaluation> evalList = testDao.getEvaluationList(test.getTestId(), student.getGender());
			if (CommonUtil.isEmpty(evalList)) {
				testResult.setPoint(-1);
				testResultList.add(testResult);
				continue;
			}

			boolean isPointSet = false;
			for (Evaluation eval : evalList) {
				if (eval.getLowerBound() - DefaultValues.EPSILON < record.getResult()
						&& record.getResult() < eval.getUpperBound() - DefaultValues.EPSILON) {
					testResult.setPoint(eval.getPoint());
					isPointSet = true;
					break;
				}
			}

			if (!isPointSet) {
				testResult.setPoint(-1);
			}
			testResultList.add(testResult);
		}
		return testResultList;
	}

	public List<StudentTestResult> getTestResultByItem(String adminId, String schoolName, String className, int gender, String itemName,
			String startTime, String endTime) {
		Item item = testDao.getItemByName(itemName);
		if (item == null) {
			return null;
		}
		List<StudentTestResult> testResultList = studentDao.getTestRecordByItem(schoolName, className, gender, item, startTime, endTime);
		if(testResultList==null) {
			return null;
		}
		Test test = testDao.getTestItemByItemId(adminId, item.getItemId());
		List<Evaluation> evalList = null;
		if(test!=null) {
			evalList = testDao.getEvaluationList(test.getTestId(), gender);
		}
		
		if(test==null || CommonUtil.isEmpty(evalList)) {
			for(StudentTestResult result : testResultList)
				result.setPoint(-1);
		}
		
		for(StudentTestResult testResult : testResultList) {
			boolean isPointSet = false;
			for (Evaluation eval : evalList) {
				if (eval.getLowerBound() - DefaultValues.EPSILON < testResult.getResult()
						&& testResult.getResult() < eval.getUpperBound() - DefaultValues.EPSILON) {
					testResult.setPoint(eval.getPoint());
					isPointSet = true;
					break;
				}
			}
			
			if (!isPointSet) {
				testResult.setPoint(-1);
			}
		}
		return testResultList;
	}
}
