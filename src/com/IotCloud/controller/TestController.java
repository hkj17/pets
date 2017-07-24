package com.IotCloud.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.IotCloud.util.ResponseFilter;

@RestController
public class TestController {

	@Autowired
	TestService testService;

	@RequestMapping(value = "/getItemList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getItemList(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<Item> itemList = testService.getItemList();
			res.put(ParameterKeys.STATE, 0);
			res.put("itemList", itemList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getUnaddedItemList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUnaddedItemList(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<Item> unaddedItemList = testService.getUnaddedItemList(CommonUtil.getSessionUser(request));
			res.put(ParameterKeys.STATE, 0);
			res.put("unaddedItemList", unaddedItemList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addItem(HttpServletRequest request, @RequestParam(value = "itemName") String itemName) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			boolean state = testService.addItem(itemName);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/addTestItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTestItem(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ITEM_ID) String itemId,
			@RequestParam(value = ParameterKeys.TYPE) int type) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			int state = testService.addTestItem(CommonUtil.getSessionUser(request), itemId, type);
			res.put(ParameterKeys.STATE, state);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
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

	@RequestMapping(value = "/batchDeleteTestItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchDeleteTestItem(HttpServletRequest request, @RequestBody List<Test> testList) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}
		try {
			boolean state = testService.batchDeleteTestItem(CommonUtil.getSessionUser(request), testList);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getTestItemList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTestItemList(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<Test> itemList = testService.getTestItemList(CommonUtil.getSessionUser(request));
			res.put(ParameterKeys.STATE, 0);
			res.put("testItemList", itemList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/editTestType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editTestType(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ITEM_ID) String itemId,
			@RequestParam(value = ParameterKeys.TYPE) int type) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			boolean state = testService.editTestType(CommonUtil.getSessionUser(request), itemId, type);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/loadEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadEvaluation(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			MultipartHttpServletRequest re = resolver.resolveMultipart(request);

			MultipartFile fileM = re.getFile("filename");
			CommonsMultipartFile cf = (CommonsMultipartFile) fileM;
			InputStream inputStream = cf.getInputStream();
			String message = testService.loadEvalFromXml(CommonUtil.getSessionUser(request), inputStream);
			res.put(ParameterKeys.STATE, 0);
			res.put("message", message);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			res.put("message", "参数格式错误");
			return res;
		}
	}

	@RequestMapping(value = "/clearEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clearEvaluation(HttpServletRequest request,
			@RequestParam(value = "itemName") String itemName, @RequestParam(value = ParameterKeys.TYPE) int type) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}
		try {
			boolean state = testService.clearEvaluation(CommonUtil.getSessionUser(request), itemName, type);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getEvaluationList/{testId}/type/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEvaluationList(HttpServletRequest request,
			@PathVariable(ParameterKeys.TEST_ID) String testId, @PathVariable(ParameterKeys.TYPE) int type) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<Evaluation> evalList = testService.getEvaluationList(CommonUtil.getSessionUser(request), testId, type);
			// 考试项目不属于该账户
			if (evalList == null) {
				res.put(ParameterKeys.STATE, 11);
				return res;
			}

			res.put(ParameterKeys.STATE, 0);
			res.put("evalList", evalList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/loadView")
	public String loadView() {
		return "loadTest";
	}
}
