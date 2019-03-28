package com.yehuda.coupons.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yehuda.coupons.exceptions.ApplicationException;

@RestController
@RequestMapping("/logout")
public class LogoutApi {

	@DeleteMapping
	public void logout(HttpServletRequest request) throws ApplicationException {
		
		request.getSession().invalidate();
		
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("uesrId")) {
				cookie.setMaxAge(0);
			}
		}

	}
}
