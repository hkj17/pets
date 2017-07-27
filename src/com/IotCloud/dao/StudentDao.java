package com.IotCloud.dao;

import java.util.List;

import com.IotCloud.data.StudentTestResult;
import com.IotCloud.model.Item;
import com.IotCloud.model.Record;
import com.IotCloud.model.Student;

/**
 * 所有跟学生和学生成绩查询有关的DAO层
 * @author 胡可及
 *
 */
public interface StudentDao {
	
	/**
	 * 批量添加学生
	 */
	public boolean addStudents(List<Student> studentList);
	
	/**
	 * 批量删除学生
	 */
	public boolean deleteStudents(List<Student> studentList);
	
	/**
	 * 返回一个管理员下面所有学校名称
	 */
	public List<String> getSchoolNameListByAdminId(String adminId);
	
	/**
	 * 删除学生的考试信息
	 */
	public boolean deleteTestResult(List<Record> recordList);
	
	/**
	 * 返回一个学校下面所有的班级名称
	 */
	public List<String> getClassNameListBySchool(String adminId, String schoolName);
	
	/**
	 * 返回学生列表
	 */
	List<Student> getStudentList(String adminId, String schoolName, String className, Integer gender);
	
	/**
	 * 返回一个学校下面所有的学生
	 */
	List<Student> getStudentListBySchool(String adminId, String schoolName);
	
	/**
	 * 返回一个班级下面所有的学生
	 */
	List<Student> getStudentListByClass(String adminId, String schoolName, String className);
	
	/**
	 * 按照考号查找学生
	 */
	Student getStudentByTesterNo(String adminId, long testerNo);
	
	/**
	 * 返回一个学生所有的考试记录
	 */
	List<Record> getTestRecordByStudent(String adminId, long testerNo, String startTime, String endTime);
	
	/**
	 * 按项目查询考生的成绩和评分
	 */
	List<StudentTestResult> getTestRecordByItem(String schoolName, String className, int gender, Item item, String startTime, String endTime);
}
