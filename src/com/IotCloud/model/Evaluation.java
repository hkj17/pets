package com.IotCloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@IdClass(EvalPKID.class)
@Entity(name = "Evaluation")
@Table(name = "evaluation")
public class Evaluation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7252173290339962798L;
	
	@Column(name = "eval_id")
	private String evalId;
	
	@Id
	@Column(name = "test_id")
	private String testId;

	@Id
	@Column(name = "type")
	private int type;
	
	@Column(name = "lower_bound")
	private double lowerBound;
	
	@Column(name = "upper_bound")
	private double upperBound;
	
	@Column(name = "unit")
	private String unit;
	
	@Id
	@Column(name = "point")
	private double point;
	
	public String getEvalId() {
		return evalId;
	}
	
	public void setEvalId(String evalId) {
		this.evalId = evalId;
	}
	
	public String getTestId() {
		return testId;
	}
	
	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}
	
	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	public double getUpperBound() {
		return upperBound;
	}
	
	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public double getPoint() {
		return point;
	}
	
	public void setPoint(double point) {
		this.point = point;
	}
	
}
