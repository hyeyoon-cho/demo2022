package com.sample.demo.common.exception;

public enum ErrorCode {
	
	// Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "METHOD_NOT_ALLOWED"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access Denied"),
    
    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),
    NOT_ALLOW_USER(401, "M003", "허가되지 않은 사용자"),
    
    FILE_IO_EXCEPTION(500, "F001", "File io Exception"),
    INVALID_EXCEL_DATA(500, "E001", "Invalid Excel Data"),
    CONFIG_INIT_FAIL(500, "E002", "config initialization failed"),
    
    ENGINE_EXCEPTION(500, "E001", "Engine Exception"),
    ALREADY_USED_EXCEPTION(500, "E002", "already used by another worker"),
    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
    
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

}
