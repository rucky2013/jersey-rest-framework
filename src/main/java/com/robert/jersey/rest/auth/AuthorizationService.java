package com.robert.jersey.rest.auth;

public interface AuthorizationService {
	public User validate(String token);
}
