package com.robert.jersey.rest.auth;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthorizationRequestFilter implements
		ContainerRequestFilter, ApplicationContextAware {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected AbstractAuthorizationServiceImpl authorizationService;

	protected ApplicationContext applicationContext;

	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		String token = doRetrieveSessionKey(requestContext);

		log.info("AuthorizationRequestFilter: validate token {}", token);
		User user = authorizationService.validate(token);

		doSpecialValidation(requestContext, user);

		log.info("AuthorizationRequestFilter: validated token {}, user {}",
				token, user);
	}

	protected String doRetrieveSessionKey(ContainerRequestContext requestContext) {
		Cookie userCooker = requestContext.getCookies().get("Authentication");

		if (userCooker != null)
			return userCooker.getValue();

		return null;
	}

	protected void doSpecialValidation(ContainerRequestContext requestContext,
			User user) {
	}

	public AbstractAuthorizationServiceImpl getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(
			AbstractAuthorizationServiceImpl authorizationService) {
		this.authorizationService = authorizationService;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}