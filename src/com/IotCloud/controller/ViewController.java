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
import com.IotCloud.exception.UserNotLoggedInException;
import com.IotCloud.model.Admin;
import com.IotCloud.service.AdminService;

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
		String userName = request.getParameter("user");
		String password = request.getParameter("passwd");
		ModelAndView modelAndView = new ModelAndView();
		JSONObject jsonObject = adminService.validatePassword(userName, password);
		if (jsonObject.getInt("state") == 0) {
			modelAndView.setViewName("success");
			request.getSession().setAttribute("adminId", jsonObject.getString("adminId"));
			request.getSession().setAttribute("auth", jsonObject.getInt("auth"));
		} else {
			modelAndView.setViewName("fail");
		}
		writeJsonToResponse(response, jsonObject);
		return modelAndView;
	}

	@RequestMapping("/")
	public String loginView() {
		return "login";
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String addAdmin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			Object adminId = request.getSession().getAttribute("adminId");
			Object auth = request.getSession().getAttribute("auth");
			if (adminId == null || auth == null || !auth.equals(0)) {
				jsonObject.put("state", 2);
				return jsonObject.toString();
			}
			boolean state = adminService.insertAdmin(request.getParameter("user"), request.getParameter("passwd"),
					Integer.parseInt(request.getParameter("auth")), request.getParameter("orgName"),
					request.getParameter("areaCode"));
			jsonObject.put("state", state ? 0 : 1);
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("state", 1);
			return jsonObject.toString();
		}
	}

	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAdmin(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			Object adminId = request.getSession().getAttribute("adminId");
			Object auth = request.getSession().getAttribute("auth");
			if (adminId == null || auth == null || !auth.equals(0)) {
				jsonObject.put("state", 2);
				return jsonObject.toString();
			}
			boolean state = adminService.deleteAdmin(request.getParameter("user"));
			jsonObject.put("state", state ? 0 : 1);
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("state", 1);
			// writeJsonToResponse(response, jsonObject);
			return jsonObject.toString();
		}
	}

	@RequestMapping(value = "/getAdminList", method = RequestMethod.POST)
	@ResponseBody
	public List<Admin> getAdminList() {
		return adminService.getAdminList();
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String updatePassword(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		try {
			Object adminId = request.getSession().getAttribute("adminId");
			Object auth = request.getSession().getAttribute("auth");
			if (adminId == null || auth == null || !auth.equals(0)) {
				jsonObject.put("state", 2);
				return jsonObject.toString();
			}
			int state = adminService.updatePassword(request.getParameter("user"), request.getParameter("oldPasswd"),
					request.getParameter("newPasswd"));
			jsonObject.put("state", state);
			return jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("state", 1);
			return jsonObject.toString();
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