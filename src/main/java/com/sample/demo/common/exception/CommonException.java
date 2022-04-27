package com.sample.demo.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int statusCode = -1;
	private String code;
	
	@JsonIgnore
	private ErrorCode errorCode;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String errorMsg;
	
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

	/**
	 * @param statusCode
	 * @param code
	 * @param errorCode
	 * @param errorMsg 사용자 정의 메세지
	 */
	public CommonException(int statusCode, ErrorCode errorCode, String errorMsg) {
		this.statusCode = statusCode;
		this.code = errorCode.getCode();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public CommonException(int statusCode, ErrorCode errorCode) {
		this.statusCode = statusCode;
		this.code = errorCode.getCode();
		this.errorCode = errorCode;
	}

	
	/**
	 * jackson ignore 를 위해 오버라이딩함
	 */
	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return super.getStackTrace();
	}

	@Override
	@JsonIgnore
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return super.getLocalizedMessage();
	}

	@Override
	@JsonIgnore
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
}
