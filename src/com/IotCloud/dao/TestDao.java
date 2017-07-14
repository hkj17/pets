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
	public boolean deleteTestItem(String adminId, String itemId);
	
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
	public String addEvaluation(String testId, int gender, double lowerBound, double upperBound, String unit, double point);
	
	/**
	 * 删除评分标准
	 */
	public boolean deleteEvaluation(String testId, int gender, double point);
	
	/**
	 * 修改评分标准
	 */
	public boolean editEvaluation(String evalId, double lowerBound, double upperBound, double point);
	
	/**
	 * 根据性别获取评分标准列表
	 */
	public List<Evaluation> getEvaluationList(String testId, int gender);
	
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
}
