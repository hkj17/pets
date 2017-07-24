package com.IotCloud.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EvalPKID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5253881659325888062L;
	private String testId;
	private int type;
	private double point;

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

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EvalPKID)) {
			return false;
		}
		EvalPKID pkid = (EvalPKID) obj;
		return testId != null && testId.equals(pkid.getTestId()) && type == pkid.getType()
				&& Math.abs(point - pkid.getPoint()) < 0.00001;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + testId == null ? 0 : testId.hashCode();
		result = result * 31 + type;
		long l = Double.doubleToLongBits(point);
		result = result * 31 + (int) (l ^ (l >>> 32));
		//System.out.println("hashcode value: " + result);
		return result;
	}
}
