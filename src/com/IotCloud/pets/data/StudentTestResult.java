package com.IotCloud.pets.data;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StudentTestResult {
	private String studentName;
	private int gender;
	private long testerNo;
	private String schoolName;
	private String className;
	private int studentNo;
	
	private String itemName;
	private double result;
	private double point;
	private String unit;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp testTime;
	
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Timestamp getTestTime() {
		return testTime;
	}
	
	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}
}
