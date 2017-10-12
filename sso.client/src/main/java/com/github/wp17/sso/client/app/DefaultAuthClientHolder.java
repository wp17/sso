package com.github.wp17.sso.client.app;

public class DefaultAuthClientHolder {
	private static final AuthClient defaultAuthClient = new AuthClient(new AuthInfo());
	public static final AuthClient getDefaultAuthClient(){
		return defaultAuthClient;
	}
}
