package com.github.wp17.sso.client.app;

public class AuthInfo {
	private String scheme = "http";
	private String host = "localhost:8080";
	private String loginPath = "/sso.server/auth/login";
	private String verifyPath = "/sso.server/auth/verify";
	
	public AuthInfo() {
		super();
	}
	
	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLoginPath() {
		return loginPath;
	}

	public void setLoginPath(String loginPath) {
		this.loginPath = loginPath;
	}

	public String getVerifyPath() {
		return verifyPath;
	}

	public void setVerifyPath(String verifyPath) {
		this.verifyPath = verifyPath;
	}
}
