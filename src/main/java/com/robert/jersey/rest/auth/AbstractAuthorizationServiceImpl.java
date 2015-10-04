package com.robert.jersey.rest.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.robert.jersey.rest.excep.JerseyRestFrameworkException;

@Service
public abstract class AbstractAuthorizationServiceImpl implements
		AuthorizationService {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	protected ThreadLocal<User> userContext = new ThreadLocal<User>();

	public User validate(String token) {
		log.info("Validate the user token {}.", token);

		User user = doValidate(token);

		userContext.set(user);

		return user;
	}

	protected User doValidate(String token) {
		throw JerseyRestFrameworkException.JERSEY_REST_FRAMEWORK_EXCEP_NOT_AUTHORIZED;
	}

	public User getUser() {
		return userContext.get();
	}
}
