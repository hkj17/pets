package com.IotCloud.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.IotCloud.poi.PoiListRecord;
import com.IotCloud.poi.StudentXmlReader;
import com.IotCloud.util.CommonUtil;

@Component("studentService")
@Service
public class StudentService {

	@Autowired
	StudentDao studentDao;

	@Autowired
	TestDao testDao;
	
	private static Logger logger = Logger.getLogger(StudentService.class);

	public String loadStudentsFromXml(String adminId, InputStream input){
		StudentXmlReader reader = new StudentXmlReader();
		try {
			PoiListRecord<Student> recordList = reader.loadFromXml(adminId, input);
			if(recordList.getRecordList()!=null) {
				studentDao.addStudents(recordList.getRecordList());
			}
			return recordList.getMessage();
		}catch(IOException ioe) {
			ioe.printStackTrace();
			logger.error("IO异常", ioe);
			return "添加失败，IO异常";
		}
	}
	
	public boolean deleteStudents(String adminId, List<Student> studentList) {
		if(CommonUtil.isEmpty(studentList)) {
			return true;
		}
		
		Iterator<Student> iter = studentList.iterator();
		while (iter.hasNext()) {
			Student student = iter.next();
			if (student == null || student.getAdminId() == null || !student.getAdminId().equals(adminId)) {
				iter.remove();
			}else {
				List<Record> testRecordList = studentDao.getTestRecordByStudent(adminId, student.getTesterNo(), null, null);
				if(!CommonUtil.isEmpty(testRecordList)) {
					studentDao.deleteTestRecord(testRecordList);
				}
			}
		}
		return studentDao.deleteStudents(studentList);
	}

	public List<String> getSchoolNameListByAdminId(String adminId) {
		return studentDao.getSchoolNameListByAdminId(adminId);
	}

	public List<String> getClassNameListBySchool(String adminId, String schoolName) {
		return studentDao.getClassNameListBySchool(adminId, schoolName);
	}

	public List<Student> getStudentList(String adminId, String schoolName, String className, Integer gender) {
		return studentDao.getStudentList(adminId, schoolName, className, gender);
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
			testResult.setUnit(item.getUnit());
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
