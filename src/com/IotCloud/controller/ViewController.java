package com.IotCloud.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.model.Admin;
import com.IotCloud.service.AdminService;
import com.IotCloud.util.ResponseFilter;
import com.IotCloud.constant.ParameterKeys;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
public class ViewController {

	@Autowired
	AdminDao adminDao;

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter(ParameterKeys.USER);
		String password = request.getParameter(ParameterKeys.PASSWORD);
		ModelAndView modelAndView = new ModelAndView();
		Admin admin = adminService.validatePassword(userName, password);
		JSONObject jsonObject = new JSONObject();
		if (admin!=null) {
			modelAndView.setViewName("success");
			request.getSession().setAttribute(ParameterKeys.ADMIN_ID, admin.getAdminId());
			request.getSession().setAttribute(ParameterKeys.AUTHORITY, admin.getAuthority());
		} else {
			modelAndView.setViewName("fail");
		}
		jsonObject.put(ParameterKeys.STATE, 0);
		jsonObject.put(ParameterKeys.ADMIN_ID, admin.getAdminId());
		jsonObject.put("orgName", admin.getOrgName());
		jsonObject.put(ParameterKeys.AUTHORITY, admin.getAuthority());
		writeJsonToResponse(response, jsonObject);
		return modelAndView;
	}

	@RequestMapping("/")
	public String loginView() {
		return "login";
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addAdmin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.adminServiceFilter(request);
		if(jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			boolean state = adminService.insertAdmin(request.getParameter(ParameterKeys.USER), request.getParameter(ParameterKeys.PASSWORD),
					Integer.parseInt(request.getParameter(ParameterKeys.AUTHORITY)), request.getParameter("orgName"),
					request.getParameter("areaCode"));
			jsonObject.put(ParameterKeys.STATE, state ? 0 : 1);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteAdmin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.adminServiceFilter(request);
		if(jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			boolean state = adminService.deleteAdmin(request.getParameter(ParameterKeys.USER));
			jsonObject.put(ParameterKeys.STATE, state ? 0 : 1);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/getAdminList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAdminList(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.adminServiceFilter(request);
		if(jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			List<Admin> adminList = adminService.getAdminList();
			JSONArray jsonArray = JSONArray.fromObject(adminList);
			jsonObject.put(ParameterKeys.STATE, 0);
			jsonObject.put("adminList", jsonArray);
			return jsonObject;
		}catch(Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updatePassword(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.adminServiceFilter(request);
		if(jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			int state = adminService.updatePassword(request.getParameter(ParameterKeys.USER), request.getParameter(ParameterKeys.PASSWORD),
					request.getParameter("newPasswd"));
			jsonObject.put(ParameterKeys.STATE, state);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject resetPassword(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = ResponseFilter.adminServiceFilter(request);
		if(jsonObject.containsKey(ParameterKeys.STATE)) {
			return jsonObject;
		}
		try {
			int state = adminService.resetPassword(request.getParameter(ParameterKeys.USER));
			jsonObject.put(ParameterKeys.STATE, state);
			return jsonObject;
		}catch (Exception e) {
			e.printStackTrace();
			jsonObject.put(ParameterKeys.STATE, 1);
			return jsonObject;
		}
		
	}

	private void writeJsonToResponse(HttpServletResponse response, JSONObject json) throws IOException {
		PrintWriter writer = response.getWriter();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		writer.print(json);
		writer.flush();
		writer.close();
	}
}