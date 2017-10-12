package com.github.wp17.sso.client.app;

public class LoginResult {
	private int r = 0;
	private String token;
	private String username;
	
	public LoginResult() {
		super();
	}

	public LoginResult(int r, String token, String username) {
		super();
		this.r = r;
		this.token = token;
		this.username = username;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
