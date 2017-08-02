package com.IotCloud.pets.constant;

public class Hql {
	public static final String GET_USER_BY_NAME = "from Admin a where a.userName = ?";

	public static final String GET_USER_BY_ID = "from Admin a where a.adminId = ?";
	
	//不显示超级管理员
	public static final String GET_ADMIN_LIST = "from Admin a where a.createdBy = ? order by a.userName";

	public static final String GET_ITEM_LIST = "from Item";
	
	public static final String GET_ITEM_BY_ID = "from Item i where i.itemId = ?";
	
	public static final String GET_ITEM_BY_NAME = "from Item where itemName = ?";
	
	public static final String GET_UNADDED_ITEM_LIST = "from Item i where i.itemId not in (select t.item.itemId from Test t where t.adminId = ?)";
	
	public static final String GET_TEST_BY_ITEM_ID = "from Test t where t.adminId = ? and t.item.itemId = ?";
	
	public static final String GET_TEST_ITEM_BY_NAME = "from Test t where t.adminId = ? and t.item.itemName = ?";
	
	public static final String GET_TEST_BY_ID = "from Test t where t.testId = ?";
	
	public static final String GET_TEST_ITEM_LIST = "from Test t where t.adminId = ?";
	
	public static final String GET_EVAL_LIST_BY_TEST_ID = "from Evaluation e where e.testId = ?";
	
	public static final String GET_EVAL_LIST_BY_GENDER = "from Evaluation e where e.testId = ? and e.type = ? order by e.point";
	
	public static final String GET_DISTINCT_SCHOOL_NAME = "select distinct s.schoolName from Student s where s.adminId = ? order by s.schoolName";
	
	public static final String GET_DISTINCT_CLASS_NAME = "select distinct s.className from Student s where s.adminId = ? and s.schoolName = ? order by s.className";
	
	public static final String GET_STUDENT_BY_TESTER_NO = "from Student s where s.adminId = ? and s.testerNo = ?";
	
	public static final String GET_RECORD_LIST_BY_STUDENT = "from Record r where r.adminId = ? and r.testerNo = ?";
}
