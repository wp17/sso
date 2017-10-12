package com.github.wp17.sso.server.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthContext {
	private AuthContext(){}
	private static final AuthContext instance = new AuthContext();
	public static AuthContext getInstance(){
		return instance;
	}
	
	private final Map<String, User> map = new ConcurrentHashMap<String, User>();
	
	public void addUser(User user){
		map.put(user.getUsername(), user);
	}
	
	public User getUser(String username){
		return map.get(username);
	}
}
