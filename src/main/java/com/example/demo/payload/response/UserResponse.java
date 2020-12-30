package com.example.demo.payload.response;

import com.example.demo.model.User;

public class UserResponse {
	
	private String username;
	private String email;
	
	public UserResponse(User u) {
		username = u.getUserName();
		email = u.getEmail();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
		
}
