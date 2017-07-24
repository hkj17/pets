package com.IotCloud.model;

import java.io.Serializable;

public class StudentPKID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3886920855698550963L;
	private String adminId;
	private long testerNo;

	public long getTesterNo() {
		return testerNo;
	}

	public void setTesterNo(long testerNo) {
		this.testerNo = testerNo;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StudentPKID)) {
			return false;
		}
		StudentPKID pkid = (StudentPKID) obj;
		return adminId != null && adminId.equals(pkid.getAdminId()) && testerNo == pkid.getTesterNo();
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + adminId == null ? 0 : adminId.hashCode();
		result = result * 31 + (int) (testerNo ^ (testerNo >>> 32));
		return result;
	}
}
