package com.yehuda.coupons.beans;


import org.springframework.stereotype.Component;

import com.yehuda.coupons.enums.UserType;

@Component
public class UserLogin {
	
	private String name;
	private String password;
	private UserType userType;
	
	public UserLogin(String name, String password, UserType userType) {
		super();
		this.name = name;
		this.password = password;
		this.userType = userType;
	}

	public UserLogin() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	
	
	

}
