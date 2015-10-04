package com.robert.jersey.rest.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	private String userName;
	private List<String> roles;

	private Map<String, Object> attribs = new HashMap<String, Object>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Map<String, Object> getAttribs() {
		return attribs;
	}

	public void getAttrib(String key) {
		this.attribs.get(key);
	}

	public void setAttribs(Map<String, Object> attribs) {
		this.attribs = attribs;
	}

	public void setAttrib(String key, Object value) {
		this.attribs.put(key, value);
	}
}
