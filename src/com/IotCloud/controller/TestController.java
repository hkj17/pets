package com.IotCloud.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.IotCloud.constant.ParameterKeys;
import com.IotCloud.model.Evaluation;
import com.IotCloud.model.Item;
import com.IotCloud.model.Test;
import com.IotCloud.service.TestService;
import com.IotCloud.util.CommonUtil;

@RestController
public class TestController {

	@Autowired
	TestService testService;

	private static Logger logger = Logger.getLogger(TestController.class);

	@RequestMapping(value = "/admin/getItemList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getItemList(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			List<Item> itemList = testService.getItemList();
			res.put(ParameterKeys.STATE, 0);
			res.put("itemList", itemList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取项目列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/getUnaddedItemList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUnaddedItemList(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			List<Item> unaddedItemList = testService.getUnaddedItemList(CommonUtil.getSessionUser(request));
			res.put(ParameterKeys.STATE, 0);
			res.put("unaddedItemList", unaddedItemList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取未添加为考试项目的项目列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/superadmin/addItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addItem(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ITEM_NAME) String itemName) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			boolean state = testService.addItem(itemName);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加项目异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/addTestItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTestItem(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ITEM_ID) String itemId,
			@RequestParam(value = ParameterKeys.TYPE) int type) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			int state = testService.addTestItem(CommonUtil.getSessionUser(request), itemId, type);
			res.put(ParameterKeys.STATE, state);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加考试项目异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	// @RequestMapping(value = "/deleteTestItem", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> deleteTestItem(HttpServletRequest request,
	// @RequestParam(value = ParameterKeys.ITEM_ID) String itemId) {
	// Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
	// if (res.containsKey(ParameterKeys.STATE)) {
	// return res;
	// }
	//
	// try {
	// boolean state =
	// testService.deleteTestItem(CommonUtil.getSessionUser(request), itemId);
	// res.put(ParameterKeys.STATE, state ? 0 : 1);
	// return res;
	// } catch (Exception e) {
	// e.printStackTrace();
	// res.put(ParameterKeys.STATE, 1);
	// return res;
	// }
	// }

	@RequestMapping(value = "/admin/batchDeleteTestItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchDeleteTestItem(HttpServletRequest request, @RequestBody List<Test> testList) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			boolean state = testService.batchDeleteTestItem(CommonUtil.getSessionUser(request), testList);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量删除考试项目异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/getTestItemList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTestItemList(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.TYPE, required = false) Integer type) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			List<Test> itemList = testService.getTestItemList(CommonUtil.getSessionUser(request), type);
			res.put(ParameterKeys.STATE, 0);
			res.put("testItemList", itemList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取考试项目列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/editTestType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editTestType(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ITEM_ID) String itemId,
			@RequestParam(value = ParameterKeys.TYPE) int type) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			boolean state = testService.editTestType(CommonUtil.getSessionUser(request), itemId, type);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑考试类型异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/loadEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadEvaluation(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			MultipartHttpServletRequest re = resolver.resolveMultipart(request);

			MultipartFile fileM = re.getFile("filename");
			CommonsMultipartFile cf = (CommonsMultipartFile) fileM;
			InputStream inputStream = cf.getInputStream();
			String message = testService.loadEvalFromXml(CommonUtil.getSessionUser(request), inputStream);
			res.put(ParameterKeys.STATE, 0);
			res.put(ParameterKeys.MESSAGE, message);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量导入评分标准异常", e);
			res.put(ParameterKeys.STATE, 1);
			res.put(ParameterKeys.MESSAGE, "参数格式错误");
			return res;
		}
	}

	@RequestMapping(value = "/admin/clearEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clearEvaluation(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ITEM_NAME) String itemName,
			@RequestParam(value = ParameterKeys.TYPE) int type) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			boolean state = testService.clearEvaluation(CommonUtil.getSessionUser(request), itemName, type);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除评分标准错误", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/getEvaluationList/{testId}/type/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEvaluationList(HttpServletRequest request,
			@PathVariable(ParameterKeys.TEST_ID) String testId, @PathVariable(ParameterKeys.TYPE) int type) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			Test test = testService.getTestById(testId);
			if (test == null) {
				// 没有该考试项目
				res.put(ParameterKeys.STATE, 11);
				return res;
			}

			List<Evaluation> evalList = testService.getEvaluationList(CommonUtil.getSessionUser(request), test, type);
			// 考试项目不属于该账户
			if (evalList == null) {
				res.put(ParameterKeys.STATE, 12);
				return res;
			}

			String unit = testService.getUnitByTestItem(test);
			res.put(ParameterKeys.STATE, 0);
			res.put("evalList", evalList);
			res.put("unit", unit);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取评分标准列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	// @RequestMapping(value = "/loadView")
	// public String loadView() {
	// return "loadTest";
	// }
}
