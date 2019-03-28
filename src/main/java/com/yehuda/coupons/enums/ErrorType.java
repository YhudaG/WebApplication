package com.yehuda.coupons.enums;

public enum ErrorType {

	// errors of system
	STOP_SYSTEM_ERROR(601, null, "there is a problem with the start system"),
	START_SYSTEM_ERROR(602, null, "there is a problem with the stop system"),

	SQL_EXCEPTION(612, "the operation failed, please try again later", "sql exception"),
	CONNECTION_POOL_ERROR(613, "general error", "there is a probloem with the connection pool"),
	CONNECTIONS_ERROR(614, "general error", "there is a probloem with the functions of connection pool"),

	// errors that depended of client
	COUPON_ID_NOT_EXIST(655, "coupon not found"), COMPANY_ID_NOT_EXIST(655, "company not found "),
	CUSTOMER_ID_NOT_EXIST(655, "customer not found"), LOGIN_ERROR(656, "incorrect name or password"),
	USER_TYPE_ERROR(656, "incorrect user type"),
	NAME_EXIST_ERROR(657, "this name already exist, please choose another name"),
	COUPON_TITLE_EXIST_ERROR(657, "the title of already exist, please choose another title"),
	COUPOM_FINISHED_ERROR(658, "we are sorry, but this coupon is finished"),
	PURCHASE_AGAIN_ERROR(658, "we are sorry, but you cann't buy the same coupon again"),
	SYSTEM_CLOSE_ERROR(660, "we are sorry, but the system is closed"),
	COUPON_START_DATE_ERROR(657, "incorrect start date"), COUPON_END_DATE_ERROR(657, "incorrect end date"),
	COUPON_AMOUNT_ERRROR(657, "incorrect amount"), COUPON_PRICE_ERRROR(657, "incorrect price"),
	PARAMETER_NULL_ERROR(657, "one of the parameters is missed"),
	PASSWORD_SHORT_ERROR(657, "minimum 4 characters for password"),
	TITLE_WRONG_ERROR(657, "wrong input, please check the title not start with a space"),
	WRONG_INPUT_ERROR(657, "wrong input, please check the one of the parameter not start with a space"),
	COMPANY_COUPON_ERROR(658, "this coupon is not of your company");

	private int errorCode;
	private String internalMessage;
	private String externalMessage;

	private ErrorType(int errorCode, String externalMessage) {
		this.errorCode = errorCode;
		this.externalMessage = externalMessage;
	}

	private ErrorType(int errorCode, String externalMessage, String internalMessage) {
		this.errorCode = errorCode;
		this.externalMessage = externalMessage;
		this.internalMessage = internalMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public String getExternalMessage() {
		return externalMessage;
	}
}
