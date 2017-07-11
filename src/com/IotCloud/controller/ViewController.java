package com.IotCloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.IotCloud.dao.AdminDao;
import com.IotCloud.service.AdminService;

@RestController
public class ViewController {

	@Autowired
	AdminDao adminDao;

	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("user");
		String password = request.getParameter("passwd");
		ModelAndView modelAndView = new ModelAndView();
		boolean state = adminService.validatePassword(userName, password);
		if(state) {
			modelAndView.setViewName("success");
		}else {
			modelAndView.addObject("password", password);
			modelAndView.setViewName("fail");
		}
		return modelAndView;
	}
	
	  @RequestMapping("/")
	  public String loginView(){
		  return "login";
	  }
}