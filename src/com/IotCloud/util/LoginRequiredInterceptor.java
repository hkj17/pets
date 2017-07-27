package com.IotCloud.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.IotCloud.constant.ParameterKeys;

public class LoginRequiredInterceptor implements MethodInterceptor {


	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (methodInvocation.getMethod().isAnnotationPresent(
				LoginRequired.class)) {
			Object[] args = methodInvocation.getArguments();
			if (args.length != 0) {
				for (Object obj : args) {
					if (obj instanceof HttpServletRequest) {
						HttpServletRequest request = (HttpServletRequest)obj;
						if(request.getSession().getAttribute(ParameterKeys.ADMIN_ID) == null){
							System.out.println("Intercepter");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put(ParameterKeys.STATE, 2);
							return map;
						}
					}
				}
			}
		}
		return methodInvocation.proceed();
		
	}
}
