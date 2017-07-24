package com.IotCloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.IotCloud.model.Admin;
import com.IotCloud.service.AdminService;
import com.IotCloud.util.CommonUtil;
import com.IotCloud.util.ResponseFilter;
import com.IotCloud.constant.ParameterKeys;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.USER) String userName,
			@RequestParam(value = ParameterKeys.PASSWORD) String password) {
		Admin admin = adminService.validatePassword(userName, password);
		Map<String, Object> res = new HashMap<String, Object>();
		if (admin != null) {
			request.getSession().setAttribute(ParameterKeys.ADMIN_ID, admin.getAdminId());
			request.getSession().setAttribute(ParameterKeys.AUTHORITY, admin.getAuthority());
		} else {
			request.getSession().removeAttribute(ParameterKeys.ADMIN_ID);
			request.getSession().removeAttribute(ParameterKeys.AUTHORITY);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
		res.put(ParameterKeys.STATE, 0);
		res.put(ParameterKeys.ADMIN_ID, admin.getAdminId());
		res.put("orgName", admin.getOrgName());
		res.put(ParameterKeys.AUTHORITY, admin.getAuthority());
		// writeJsonToResponse(response, jsonObject);
		return res;
	}

	@RequestMapping("/")
	public String loginView() {
		return "login";
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAdmin(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.USER) String userName,
			@RequestParam(value = ParameterKeys.PASSWORD) String password,
			@RequestParam(value = ParameterKeys.AUTHORITY) int authority,
			@RequestParam(value = "orgName") String orgName, @RequestParam(value = "areaCode") String areaCode) {
		Map<String, Object> res = ResponseFilter.adminServiceFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			boolean state = adminService.insertAdmin(CommonUtil.getSessionUser(request), userName, password, authority,
					orgName, areaCode);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	// @RequestMapping(value = "/deleteAdmin", method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> deleteAdmin(HttpServletRequest request,
	// @RequestParam(value = ParameterKeys.USER) String userName) {
	// Map<String, Object> res = ResponseFilter.adminServiceFilter(request);
	// if (res.containsKey(ParameterKeys.STATE)) {
	// return res;
	// }
	//
	// try {
	// boolean state = adminService.deleteAdmin(userName);
	// res.put(ParameterKeys.STATE, state ? 0 : 1);
	// return res;
	// } catch (Exception e) {
	// e.printStackTrace();
	// res.put(ParameterKeys.STATE, 1);
	// return res;
	// }
	// }

	@RequestMapping(value = "/batchDeleteAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchDeleteAdmin(HttpServletRequest request, @RequestBody List<Admin> adminList) {
		Map<String, Object> res = ResponseFilter.adminServiceFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			boolean state = adminService.batchDeleteAdmin(adminList);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/getAdminList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAdminList(HttpServletRequest request) {
		Map<String, Object> res = ResponseFilter.adminServiceFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			List<Admin> adminList = adminService.getAdminList(CommonUtil.getSessionUser(request));
			res.put(ParameterKeys.STATE, 0);
			res.put("adminList", adminList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePassword(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.PASSWORD) String password,
			@RequestParam(value = "newPasswd") String newPasswd) {
		Map<String, Object> res = ResponseFilter.loginRequiredFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			int state = adminService.updatePassword(CommonUtil.getSessionUser(request), password, newPasswd);
			res.put(ParameterKeys.STATE, state);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassword(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.USER) String userName) {
		Map<String, Object> res = ResponseFilter.adminServiceFilter(request);
		if (res.containsKey(ParameterKeys.STATE)) {
			return res;
		}

		try {
			int state = adminService.resetPassword(userName);
			res.put(ParameterKeys.STATE, state);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			res.put(ParameterKeys.STATE, 1);
			return res;
		}

	}
}