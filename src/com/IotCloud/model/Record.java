package com.IotCloud.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "Record")
@Table(name = "record")
public class Record implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8844545432561065286L;
	
	@Id
	@Column(name = "record_id")
	private String recordId;

	@Column(name = "admin_id")
	private String adminId;
	
	@Column(name = "tester_no")
	private long testerNo;
	
	@Column(name = "item_id")
	private String itemId;
	
	@Column(name = "result")
	private double result;
	
	@Column(name = "created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp createdAt;
	
	public String getRecordId() {
		return recordId;
	}
	
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getAdminId() {
		return adminId;
	}
	
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	public long getTesterNo() {
		return testerNo;
	}
	
	public void setTesterNo(long testerNo) {
		this.testerNo = testerNo;
	}
	
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public double getResult() {
		return result;
	}
	
	public void setResult(double result) {
		this.result = result;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
