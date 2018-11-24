package com.jarmel.basic.webapp.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jarmel.basic.webapp.entity.security.User;
import com.jarmel.basic.webapp.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    
    private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		logger.info("\nIn customAuthenticationSuccessHandler\n");

		logger.info("\n========>>>>>>>>>>> Authentication token = " + authentication + "\n");
		logger.info("\n========>>>>>>>>>>> authentication.getPrincipal() = " + authentication.getPrincipal() + "\n");
		logger.info("\n========>>>>>>>>>>> authentication.getAuthorities() = " + authentication.getAuthorities() + "\n");
		logger.info("\n========>>>>>>>>>>> authentication.getCredentials() = " + authentication.getCredentials() + "\n");
		logger.info("\n========>>>>>>>>>>> request.getSession().getAttributeNames() = " + request.getSession().getAttributeNames() + "\n");

		String userName = authentication.getName();
		
		User theUser = userService.findByUserName(userName);

		logger.info("Successfully Authenticated user is: " + theUser);

		// now place in the session
		HttpSession sessions = request.getSession();
		sessions.setAttribute("user", theUser);

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		logger.info("ipAddress: " + ipAddress);

		// forward to home page
		logger.info("Sending " + theUser.getUserName() + " to the home page!");

		response.sendRedirect(request.getContextPath() + "/");
	}

	private void logUserAccess(String userName, String remoteHostAddress ) {

	}
}
