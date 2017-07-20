package com.IotCloud.dao;

import java.util.List;

import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;

/**
 * 记录所有跟测试相关的DAO层
 * @author 胡可及
 *
 */
public interface TestDao {
	/**
	 * 返回项目列表
	 */
	public List<Item> getItemList();
	
	/**
	 * 添加项目，不对外开放，只是测试使用
	 */
	public boolean addItem(String itemName);
	
	/**
	 * 添加考试项目
	 */
	public int addTestItem(String adminId, String itemId, int type);
	
	/**
	 * 删除考试项目
	 */
	//public boolean deleteTestItem(String adminId, String itemId);
	
	/**
	 * 批量删除考试项目
	 */
	public boolean batchDeleteTestItem(List<Test> testList);
	
	/**
	 * 根据管理员id查找测试项目
	 */
	public List<Test> getTestItemList(String adminId);
	
	/**
	 * 修改考试项目类型
	 */
	public boolean editTestType(String adminId, String itemId, int type);
	
	/**
	 * 添加评分标准
	 */
	public boolean addEvaluation(List<Evaluation> evalList);
	
	
	/**
	 * 删除评分标准列表
	 */
	public boolean deleteEvaluation(List<Evaluation> evalList);
	
	/**
	 * 删除评分标准
	 */
	public boolean clearEvaluation(String adminId, String itemName, int gender);
	
	/**
	 * 根据考试项目获取评分标准列表
	 */
	public List<Evaluation> getEvaluationList(String testId);
	
	/**
	 * 按照id查找项目
	 */
	public Item getItemById(String itemId);
	
	/**
	 * 按照管理员id和项目id查找考试项目
	 */
	public Test getTestItemByAdminAndItemId(String adminId, String itemId);
	
	/**
	 * 按照id查找考试项目
	 */
	public Test getTestById(String testId);
	
	/**
	 * 按照项目名称查找考试项目
	 */
	public Test getTestItemByName(String adminId, String itemName);
}
