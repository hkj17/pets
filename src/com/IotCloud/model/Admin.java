package com.IotCloud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Admin")
@Table(name = "admin")
public class Admin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8800917509339578421L;

	@Id
	@Column(name = "admin_id")
	private String adminId;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_passwd")
	private String userPasswd;

	@Column(name = "authority")
	private int authority;

	@Column(name = "school_id")
	private String schoolId;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

}
