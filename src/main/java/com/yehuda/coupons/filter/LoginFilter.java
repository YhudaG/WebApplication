package com.yehuda.coupons.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//check if the user have a session and if not return error Unauthorized 
		HttpSession session = httpRequest.getSession(false);
		
		//if the user want to login he doesn't need a session 
		String pageRequested = httpRequest.getRequestURL().toString();

		if (session != null || pageRequested.endsWith("/login")) {
			chain.doFilter(request, response);
			return;
		}
		httpResponse.setStatus(401);
		httpResponse.setHeader("Not authorized", "Please login");

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}


}
