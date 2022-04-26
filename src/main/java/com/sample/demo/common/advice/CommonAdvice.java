package com.sample.demo.common.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sample.demo.common.exception.CommonException;

@ControllerAdvice
public class CommonAdvice {
	
	@ExceptionHandler(CommonException.class)
	public ResponseEntity<Map<String, Object>> handler(CommonException e){
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("msg", e.getMessage());
		body.put("errorCode", e.getErrorCode());
		body.put("status", e.getStatusCode());
		
		return new ResponseEntity<>(body, e.getStatus());
	}

}
