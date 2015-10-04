package com.robert.jersey.rest.comm;

public class DataResult extends BaseResult {
	private Object data;

	public DataResult(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}