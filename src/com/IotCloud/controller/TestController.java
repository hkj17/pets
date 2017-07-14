package com.IotCloud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.IotCloud.constant.ParameterKeys;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;
import com.IotCloud.service.TestService;
import com.IotCloud.util.ResponseFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class TestController {

	@Autowired
	TestService testService;

	@RequestMapping(value = "/getItemList", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getItemList(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			List<Item> itemList = testService.getItemList();
			JSONArray jsonArray = JSONArray.fromObject(itemList);
			jsonObject.put(ParameterKeys.STATE, 0);
			jsonObject.put("itemList", jsonArray);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addItem(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			boolean state = testService.addItem(request.getParameter("itemName"));
			jsonObject.put(ParameterKeys.STATE, state ? 0 : 1);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/addTestItem", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addTestItem(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			int state = testService.addTestItem((String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID),
					request.getParameter(ParameterKeys.ITEM_ID),
					Integer.parseInt(request.getParameter(ParameterKeys.TYPE)));
			jsonObject.put(ParameterKeys.STATE, state);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/deleteTestItem", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteTestItem(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			boolean state = testService.deleteTestItem(
					(String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID),
					request.getParameter(ParameterKeys.ITEM_ID));
			jsonObject.put(ParameterKeys.STATE, state ? 0 : 1);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/getTestItemList", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getTestItemList(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			List<Test> itemList = testService
					.getTestItemList((String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID));
			JSONArray jsonArray = JSONArray.fromObject(itemList);
			jsonObject.put(ParameterKeys.STATE, 0);
			jsonObject.put("testItemList", jsonArray);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/editTestType", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject editTestType(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			boolean state = testService.editTestType((String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID),
					request.getParameter(ParameterKeys.ITEM_ID),
					Integer.parseInt(request.getParameter(ParameterKeys.TYPE)));
			jsonObject.put(ParameterKeys.STATE, state ? 0 : 1);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/getEvaluationList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getEvaluationList(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.loginRequiredFilter(request);
		if (jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			List<Evaluation> evalList = testService.getEvaluationList(
					(String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID),
					request.getParameter(ParameterKeys.TEST_ID),
					Integer.parseInt(request.getParameter(ParameterKeys.TYPE)));
			//考试项目不属于该账户
			if(evalList==null) {
				jsonObject.put(ParameterKeys.STATE, 11);
				return jsonObject;
			}
			JSONArray jsonArray = JSONArray.fromObject(evalList);
			jsonObject.put(ParameterKeys.STATE, 0);
			jsonObject.put("evalList", jsonArray);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}
}
