package com.IotCloud.util;

import javax.servlet.http.HttpServletRequest;

import com.IotCloud.constant.ParameterKeys;

import net.sf.json.JSONObject;

public class ResponseFilter {
	public static JSONObject adminServiceFilter(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Object adminId = request.getSession().getAttribute(ParameterKeys.ADMIN_ID);
		Object auth = request.getSession().getAttribute(ParameterKeys.AUTHORITY);
		if (adminId == null || auth == null || !auth.equals(0)) {
			jsonObject.put(ParameterKeys.STATE, 2);
			return jsonObject;
		}
		return jsonObject;
	}
	
	public static JSONObject loginRequiredFilter(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Object adminId = request.getSession().getAttribute(ParameterKeys.ADMIN_ID);
		Object auth = request.getSession().getAttribute(ParameterKeys.AUTHORITY);
		if (adminId == null) {
			jsonObject.put(ParameterKeys.STATE, 2);
			return jsonObject;
		}
		return jsonObject;
	}
}
