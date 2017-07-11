package com.IotCloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.service.AdminService;

@Controller
public class ViewController {

	@Autowired
	AdminDao adminDao;

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String view(HttpServletRequest request, Model model) {
		// String path = request.getParameter("path") + "";
		// ModelAndView mav = new ModelAndView();
		// Admin admin = adminDao.getAdminByUserName("root");

		model.addAttribute("isDeleted", adminService.insertAdmin("admin1", "admin1", 3, null));
		// mav.setViewName(path);
		return "index";
	}
}