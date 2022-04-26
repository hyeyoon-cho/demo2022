package com.sample.demo.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int statusCode = -1;
	private String code;
	private ErrorCode errorCode;
	private HttpStatus status = HttpStatus.BAD_REQUEST;
	
	public CommonException() {
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CommonException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.statusCode = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
	
	public CommonException(ErrorCode errorCode, String message) {
		super(message);
		this.statusCode = errorCode.getStatus();
		this.code = errorCode.getCode();
	}
	
	public CommonException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.statusCode = errorCode.getStatus();
		this.code = errorCode.getCode();
	}

	public CommonException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
	
}
