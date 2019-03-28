package com.yehuda.coupons.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yehuda.coupons.beans.UserLogin;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;
import com.yehuda.coupons.logic.CompanyController;
import com.yehuda.coupons.logic.CustomerController;

@RestController
@RequestMapping("/login")
public class LoginApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerController customerController;
	
	@Autowired
	private CompanyController companyController;
	

	@PostMapping
	public void login(@RequestBody UserLogin userLogin, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		long userId = -1;
		String userName = userLogin.getName();
		String userPassword = userLogin.getPassword();
		switch (userLogin.getUserType()) {
		case ADMIN:
			if (userName.equals("admin") && userPassword.equals("1234")) {
				userId = 1;
				verified(request, response, userLogin, userId);
				return;
			}
			throw new ApplicationException(ErrorType.LOGIN_ERROR);

		case CUSTOMER:
			userId = customerController.login(userName, userPassword);
			verified(request, response, userLogin, userId);
			return;
		case COMPANY:
			userId = companyController.login(userName, userPassword);
			verified(request, response, userLogin, userId);
			return;
		}
		throw new ApplicationException(ErrorType.USER_TYPE_ERROR);
	}

	private void verified(HttpServletRequest request, HttpServletResponse response, UserLogin userLogin, long id) {

		// create a session for 40 minutes
		request.getSession().setMaxInactiveInterval(60 * 40);
		Cookie userIdCookie = new Cookie("userId", String.valueOf(id));
		// create a cookie for 40 minutes
		userIdCookie.setMaxAge(60 * 40);
		response.addCookie(userIdCookie);
	}

}
