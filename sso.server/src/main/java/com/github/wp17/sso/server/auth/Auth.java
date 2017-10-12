package com.github.wp17.sso.server.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.github.wp17.sso.server.util.StringUtil;

public class Auth {
	static{
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
	    SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);
	}
//	
//	private Auth(){}
//	private static final Auth instance = new Auth();
//	public static Auth getInstance(){
//		return instance;
//	}
	
	public static int login(String username, String pwd){
	    Subject subject = SecurityUtils.getSubject();
	    UsernamePasswordToken upToken = new UsernamePasswordToken(username, pwd);
	    try {
	    	subject.login(upToken);
	    }catch(Exception e){
	    	e.printStackTrace();
		    return 0;
	    }
	    return 1;
	}
	
	public static int verify(String username, String token){
		if (StringUtil.isEmpty(token)) {
			return 0;
		}
		
		User user = (User) AuthContext.getInstance().getUser(username);
		if (null == user) {
			return 0;
		}
		
		if (token.equals(user.getAccessToken())) {
			return 1;
		}else {
			return 0;
		}
	}
}
