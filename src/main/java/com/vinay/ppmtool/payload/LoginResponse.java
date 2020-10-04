package com.vinay.ppmtool.payload;

public class LoginResponse {

	private boolean success;

	private String token;

	public LoginResponse(boolean success, String token) {
		super();
		this.success = success;
		this.token = token;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponse [success=" + success + ", token=" + token + "]";
	}

}
