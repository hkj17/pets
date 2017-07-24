package com.IotCloud.data;

import java.sql.Timestamp;

/**
 * 用来页面上展示学生个人成绩的Java类
 * 
 * @author 胡可及
 *
 */
public class TestResult {
	private String itemName;
	private double result;
	private double point;
	private Timestamp testTime;

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

	public Timestamp getTestTime() {
		return testTime;
	}
	
	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}
}
