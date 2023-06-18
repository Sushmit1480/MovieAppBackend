package com.cts.MovieBookingApp.models;

public class LoginClass {
	private String email;
	private String password;
	
	public LoginClass() {
		super();
	}

	public LoginClass(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
}
