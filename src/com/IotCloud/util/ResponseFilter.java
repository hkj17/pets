package com.IotCloud.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.IotCloud.constant.ParameterKeys;

public class ResponseFilter {
	public static Map<String, Object> adminServiceFilter(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		Object adminId = request.getSession().getAttribute(ParameterKeys.ADMIN_ID);
		Object auth = request.getSession().getAttribute(ParameterKeys.AUTHORITY);
		if (adminId == null || auth == null || !auth.equals(0)) {
			res.put(ParameterKeys.STATE, 2);
			return res;
		}
		return res;
	}
	
	public static Map<String, Object> loginRequiredFilter(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		Object adminId = request.getSession().getAttribute(ParameterKeys.ADMIN_ID);
		if (adminId == null) {
			res.put(ParameterKeys.STATE, 2);
			return res;
		}
		return res;
	}
}
