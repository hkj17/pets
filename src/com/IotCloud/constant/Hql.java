package com.IotCloud.constant;

public class Hql {
	public static final String GET_USER_BY_NAME = "from Admin a where a.userName = ?";

	//不显示超级管理员
	public static final String GET_ADMIN_LIST = "from Admin a where a.authority != 0";

	public static final String GET_ITEM_LIST = "from Item";
	
	public static final String GET_TEST_BY_ADMIN_AND_ITEM_ID = "from Test t where t.adminId = ? and t.item.itemId = ?";
	
	public static final String GET_TEST_BY_ID = "from Test t where t.testId = ?";
	
	public static final String GET_TEST_ITEM_LIST = "from Test t where t.adminId = ?";
	
	public static final String GET_ITEM_BY_ID = "from Item i where i.itemId = ?";
	
	public static final String GET_EVAL_LIST_BY_TEST_ID = "from Evaluation e where e.testId = ? and e.type = ?";
}
