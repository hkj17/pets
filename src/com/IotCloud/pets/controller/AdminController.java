package com.IotCloud.pets.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.IotCloud.pets.constant.ParameterKeys;
import com.IotCloud.pets.model.Admin;
import com.IotCloud.pets.service.AdminService;
import com.IotCloud.pets.util.CommonUtil;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	private static Logger logger = Logger.getLogger(AdminController.class);

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
		res.put(ParameterKeys.ORG_NAME, admin.getOrgName());
		res.put(ParameterKeys.AREA_CODE, admin.getAreaCode());
		res.put(ParameterKeys.AUTHORITY, admin.getAuthority());
		return res;
	}

	// @RequestMapping("/")
	// public String loginView() {
	// return "login";
	// }

	@RequestMapping(value = "/root/addAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAdmin(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.USER) String userName,
			@RequestParam(value = ParameterKeys.PASSWORD) String password,
			@RequestParam(value = ParameterKeys.AUTHORITY) int authority,
			@RequestParam(value = ParameterKeys.ORG_NAME) String orgName,
			@RequestParam(value = ParameterKeys.AREA_CODE, required = false) String areaCode) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			boolean state = adminService.insertAdmin(CommonUtil.getSessionUser(request), userName, password, authority,
					orgName, areaCode);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加管理员异常", e);
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

	@RequestMapping(value = "/root/batchDeleteAdmin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchDeleteAdmin(HttpServletRequest request, @RequestBody List<Admin> adminList) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			boolean state = adminService.batchDeleteAdmin(adminList);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量删除管理员异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/root/getAdminList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAdminList(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			List<Admin> adminList = adminService.getAdminList(CommonUtil.getSessionUser(request));
			res.put(ParameterKeys.STATE, 0);
			res.put("adminList", adminList);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取管理员列表异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/admin/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePassword(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.PASSWORD) String password,
			@RequestParam(value = "newPasswd") String newPasswd) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			int state = adminService.updatePassword(CommonUtil.getSessionUser(request), password, newPasswd);
			res.put(ParameterKeys.STATE, state);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新管理员密码异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}
	
	@RequestMapping(value = "/admin/updateAdminInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAdminInfo(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.ORG_NAME, required = false) String orgName,
			@RequestParam(value = ParameterKeys.AREA_CODE, required = false) String areaCode){
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			boolean state = adminService.updateAdminInfo(CommonUtil.getSessionUser(request), orgName, areaCode);
			res.put(ParameterKeys.STATE, state ? 0 : 1);
			return res;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("更新管理员基本信息异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}
	}

	@RequestMapping(value = "/root/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassword(HttpServletRequest request,
			@RequestParam(value = ParameterKeys.USER) String userName) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			int state = adminService.resetPassword(userName);
			res.put(ParameterKeys.STATE, state);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重置管理员密码异常", e);
			res.put(ParameterKeys.STATE, 1);
			return res;
		}

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public void logout(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}