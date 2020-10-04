package com.vinay.ppmtool.exceptions;

public class InvalidLoginResponse {
	
	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		super();
		this.username = "username invalid";
		this.password = "password invalid";
	}
	
	public InvalidLoginResponse(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
