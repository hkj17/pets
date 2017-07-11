package com.IotCloud.util;

import java.util.Collection;
import java.util.UUID;

public class CommonUtil {
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
	
    public static String generateRandomUUID(){
    	return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
}
