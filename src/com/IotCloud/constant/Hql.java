package com.IotCloud.constant;

public class Hql {
	public static final String GET_USER_BY_NAME = "from Admin a where a.userName = ?";
	
	public static final String ADD_ADMIN = "insert into admin (admin_id, user_name, user_passwd, authority, school_id) values (?,?,?,?,?)";
	
	public static final String DELETE_ADMIN_BY_USER_NAME="delete from admin where user_name=?";
}
