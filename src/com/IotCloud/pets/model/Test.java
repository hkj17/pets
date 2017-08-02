package com.IotCloud.pets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity(name = "Test")
@Table(name = "test")
public class Test implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1280438393588508699L;
	
	@Id
	@Column(name = "test_id")
	private String testId;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	@NotFound(action=NotFoundAction.IGNORE) 
	private Item item;
	
	@Column(name = "admin_id")
	private String adminId;

	@Column(name = "type")
	private int type;
	
	public String getTestId() {
		return testId;
	}
	
	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public String getAdminId() {
		return adminId;
	}
	
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}
