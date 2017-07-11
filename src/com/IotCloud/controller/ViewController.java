package com.IotCloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.service.AdminService;

@Controller
public class ViewController {

	@Autowired
	AdminDao adminDao;

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		// String path = request.getParameter("path") + "";
		// ModelAndView mav = new ModelAndView();
		// Admin admin = adminDao.getAdminByUserName("root");
		boolean state = adminService.insertAdmin("admin1", "admin1", 3, null);
		ModelAndView modelAndView = new ModelAndView("index");
		//ModelAndView.addObject("isAdded", state);
		modelAndView.addObject("isAdded", state);
		return modelAndView;
		// mav.setViewName(path);
		//return "index";
	}
}