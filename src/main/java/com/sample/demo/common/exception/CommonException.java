package com.sample.demo.common.exception;

public class CommonException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int statusCode = -1;
	private String code;
	private ErrorCode errorCode;
	
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
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	public int getStatusCode() {
		return this.statusCode;
	}
}
