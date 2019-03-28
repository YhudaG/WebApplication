package com.yehuda.coupons.exceptions;

import com.yehuda.coupons.enums.ErrorType;

public class ApplicationException extends Exception {


	private static final long serialVersionUID = 1L;

	private ErrorType errorType;
	
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(ErrorType errorType) {
		super();
		this.errorType = errorType;
	}

	public ApplicationException(ErrorType errorType, Throwable cause) {
		super(cause);
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

}
