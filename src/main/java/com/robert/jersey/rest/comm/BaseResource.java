package com.robert.jersey.rest.comm;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.robert.jersey.rest.auth.AbstractAuthorizationServiceImpl;
import com.robert.jersey.rest.auth.User;

public abstract class BaseResource implements ApplicationContextAware {
	protected ApplicationContext applicationContext;

	@Autowired
	protected AbstractAuthorizationServiceImpl authorizationService;

	protected User getUser() {
		return authorizationService.getUser();
	}

	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		applicationContext = ac;
	}
}
