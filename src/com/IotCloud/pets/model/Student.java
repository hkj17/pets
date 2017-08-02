package com.IotCloud.pets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@IdClass(StudentPKID.class)
@Entity(name = "Student")
@Table(name = "student")
public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2530349094660061829L;
	
	@Column(name = "student_id")
	private String studentId;
	
	@Column(name = "student_name")
	private String studentName;
	
	@Column(name = "gender")
	private int gender;
	
	@Id
	@Column(name = "tester_no")
	private long testerNo;
	
	@Column(name = "school_name")
	private String schoolName;
	
	@Column(name = "class_name")
	private String className;
	
	@Column(name = "student_no")
	private int studentNo;
	
	@Id
	@Column(name = "admin_id")
	private String adminId;
	
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public int getGender() {
		return gender;
	}
	
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public long getTesterNo() {
		return testerNo;
	}
	
	public void setTesterNo(long testerNo) {
		this.testerNo = testerNo;
	}
	
	public String getSchoolName() {
		return schoolName;
	}
	
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public int getStudentNo() {
		return studentNo;
	}
	
	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}
	
	public String getAdminId() {
		return adminId;
	}
	
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
}
