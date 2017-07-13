package com.IotCloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="用户未登陆")
public class UserNotLoggedInException extends RuntimeException{

}
