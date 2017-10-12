package com.github.wp17.sso.server.auth;

import java.util.ArrayList;
import java.util.List;

public class User {
	private boolean isLogin;
	private String username;
	private String accessToken;
	private List<String> registered = new ArrayList<String>();
	
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public List<String> getRegistered() {
		return registered;
	}
	public void setRegistered(List<String> registered) {
		this.registered = registered;
	}
}
