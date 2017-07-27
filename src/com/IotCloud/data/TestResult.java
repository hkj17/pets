package com.IotCloud.data;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String unit;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
