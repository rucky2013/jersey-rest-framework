package com.robert.jersey.rest.comm;

public class BaseResult {
	private int status = 200;
	private String message = "OK";

	public BaseResult() {

	}

	public BaseResult(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}