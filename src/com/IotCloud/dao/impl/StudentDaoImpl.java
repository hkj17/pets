package com.IotCloud.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.IotCloud.constant.Hql;
import com.IotCloud.dao.BaseDao;
import com.IotCloud.dao.StudentDao;
import com.IotCloud.data.StudentTestResult;
import com.IotCloud.model.Item;
import com.IotCloud.model.Record;
import com.IotCloud.model.Student;
import com.IotCloud.util.CommonUtil;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private BaseDao<Student> studentBaseDao;

	@Autowired
	private BaseDao<Record> recordBaseDao;

	@Autowired
	private BaseDao<String> stringBaseDao;

	@Override
	public boolean addStudents(List<Student> studentList) {
		studentBaseDao.batchAdd(studentList);
		return true;
	}

	@Override
	public boolean deleteStudents(List<Student> studentList) {
		studentBaseDao.batchDelete(studentList);
		return true;
	}

	@Override
	public List<String> getSchoolNameListByAdminId(String adminId) {
		return stringBaseDao.findByHql(Hql.GET_DISTINCT_SCHOOL_NAME, adminId);
	}

	@Override
	public List<String> getClassNameListBySchool(String adminId, String schoolName) {
		return stringBaseDao.findByHql(Hql.GET_DISTINCT_CLASS_NAME, adminId, schoolName);
	}

	@Override
	public List<Student> getStudentList(String adminId, String schoolName, String className, Integer gender) {
		String sql = "from Student s where s.adminId = ?";
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(0, adminId);
		int i = 1;
		if (!CommonUtil.isNullOrEmpty(schoolName)) {
			sql += " and s.schoolName = ?";
			map.put(i++, schoolName);
		}
		if (!CommonUtil.isNullOrEmpty(className)) {
			sql += " and s.className = ?";
			map.put(i++, className);
		}
		if (gender != null) {
			sql += " and s.gender = ?";
			map.put(i++, gender);
		}
		sql += " order by s.testerNo";
		
		Query query = studentBaseDao.getSession().createQuery(sql);
		for (int p = 0; p < map.size(); p++) {
			query.setParameter(p, map.get(p));
		}
		List<Student> list = query.list();
		return list;
	}

	@Override
	public List<Student> getStudentListBySchool(String adminId, String schoolName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudentListByClass(String adminId, String schoolName, String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student getStudentByTesterNo(String adminId, long testerNo) {
		return studentBaseDao.getByHql(Hql.GET_STUDENT_BY_TESTER_NO, adminId, testerNo);
	}

	@Override
	public List<Record> getTestRecordByStudent(String adminId, long testerNo, String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(0, adminId);
		map.put(1, testerNo);
		int i = 2;
		String sql = Hql.GET_RECORD_LIST_BY_STUDENT;
		if (!CommonUtil.isNullOrEmpty(startTime)) {
			startTime = startTime + " 00:00:00";
			sql += " and r.createdAt>=?";
			try {
				map.put(i++, sdf.parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (!CommonUtil.isNullOrEmpty(endTime)) {
			endTime = endTime + " 23:59:59";
			sql += " and r.createdAt<=?";
			try {
				map.put(i++, sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		Query query = recordBaseDao.getSession().createQuery(sql);
		for (int p = 0; p < map.size(); p++) {
			query.setParameter(p, map.get(p));
		}
		List<Record> list = query.list();
		return list;
	}

	@Override
	public List<StudentTestResult> getTestRecordByItem(String schoolName, String className, int gender, Item item,
			String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select s.studentName, s.schoolName, s.className, s.studentNo, s.testerNo, r.result, r.createdAt from Student s, Record r "
				+ "where s.adminId=r.adminId and s.testerNo=r.testerNo and s.gender=? and r.itemId=?";
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(0, gender);
		map.put(1, item.getItemId());
		int i = 2;
		if (!CommonUtil.isNullOrEmpty(schoolName)) {
			sql += " and s.schoolName=?";
			map.put(i++, schoolName);
		}
		if (!CommonUtil.isNullOrEmpty(className)) {
			sql += " and s.className=?";
			map.put(i++, className);
		}

		if (!CommonUtil.isNullOrEmpty(startTime)) {
			startTime = startTime + " 00:00:00";
			sql += " and r.createdAt>=?";
			try {
				map.put(i++, sdf.parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (!CommonUtil.isNullOrEmpty(endTime)) {
			endTime = endTime + " 23:59:59";
			sql += " and r.createdAt<=?";
			try {
				map.put(i++, sdf.parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		sql += " order by s.testerNo";

		Query query = recordBaseDao.getSession().createQuery(sql);
		for (int p = 0; p < map.size(); p++) {
			query.setParameter(p, map.get(p));
		}

		List<Object[]> list = query.list();
		List<StudentTestResult> studentTestResultList = new ArrayList<StudentTestResult>();
		for (int p = 0; p < list.size(); p++) {
			StudentTestResult result = new StudentTestResult();
			Object[] record = list.get(p);
			result.setStudentName((String) record[0]);
			result.setSchoolName((String) record[1]);
			result.setClassName((String) record[2]);
			result.setStudentNo((Integer) record[3]);
			result.setTesterNo((Long) record[4]);
			result.setResult((Double) record[5]);
			result.setTestTime((Timestamp) record[6]);
			result.setGender(gender);
			result.setItemName(item.getItemName());
			result.setUnit(item.getUnit());
			studentTestResultList.add(result);
		}
		return studentTestResultList;
	}

	@Override
	public boolean deleteTestRecord(List<Record> recordList) {
		recordBaseDao.batchDelete(recordList);
		return true;
	}

}
