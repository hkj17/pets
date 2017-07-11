package com.IotCloud.util;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoginRequiredInterceptor implements MethodInterceptor {


	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//		if (methodInvocation.getMethod().isAnnotationPresent(
//				LoginRequired.class)) {
//			Object[] args = methodInvocation.getArguments();
//			if (args.length != 0) {
//				for (Object obj : args) {
//					if (obj instanceof HttpServletRequest) {
//						HttpServletRequest request = (HttpServletRequest)obj;
//						if(request.getSession().getAttribute(ParameterKeys.SESSION_USER) == null){
//							return ResponseFactory.response(Response.Status.OK, ResponseCode.NOT_LOGIN, null);
//						}
//					}
//				}
//			}
//		}
		return methodInvocation.proceed();
		
	}
}
