package com.yehuda.coupons.enums;

public enum DescriptionType {
	
	CUSTOMER_PURCHASE("customer purchased coupon"),
	COMPANY_CREATE_COUPON("company created coupon", 50),
	COMPANY_UPDATE_COUPON("company updated coupon" , 10);
	
	private String description;
	private double amount;
	
	private DescriptionType(String description, double amount) {
		this.description = description;
		this.amount = amount;
	}
	
	private DescriptionType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}
	
	

}
