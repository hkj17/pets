package com.IotCloud.pets.util;

import java.util.Collection;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.IotCloud.pets.constant.ParameterKeys;

public class CommonUtil {
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
    public static String generateRandomUUID(){
    	return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
    
    public static String getSessionUser(HttpServletRequest request) {
    	return (String) request.getSession().getAttribute(ParameterKeys.ADMIN_ID);
    }
}
