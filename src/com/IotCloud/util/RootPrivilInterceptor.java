package com.IotCloud.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.IotCloud.constant.ParameterKeys;

import net.sf.json.JSONObject;

public class RootPrivilInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String adminId = (String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID);
		Object auth = request.getSession().getAttribute(ParameterKeys.AUTHORITY);
		if(CommonUtil.isNullOrEmpty(adminId) || auth == null || !auth.equals(0)) {
			JSONObject json = new JSONObject();
			json.put(ParameterKeys.STATE, 2);
			PrintWriter pw = response.getWriter();
			pw.write(json.toString());
			return false;
		}
		return true;
	}

}
