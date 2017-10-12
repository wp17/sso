package com.github.wp17.sso.server.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.sso.server.auth.Auth;
import com.github.wp17.sso.server.auth.AuthContext;
import com.github.wp17.sso.server.auth.User;
import com.github.wp17.sso.server.util.AppUtils;
import com.github.wp17.sso.server.util.StringUtil;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@RequestMapping("reg")
	public void register(){
		
	}

	@RequestMapping("/loginPage")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) String redirectUrl){
		Object loggedObj = request.getSession().getAttribute("logged");
		boolean logged = null == loggedObj ? false : (boolean) loggedObj;
		if (logged) {
			ModelAndView mav = new ModelAndView("loggedTipPage");
			Object username = request.getSession().getAttribute("username");
			if (null != username) {
				User user = AuthContext.getInstance().getUser(username.toString());
				mav.addObject("username", user.getUsername());
			}
			return mav;
		}
		
		ModelAndView mav = new ModelAndView("loginPage");
		mav.addObject("redirectUrl", redirectUrl);
		return mav;
	}
	
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String redirectUrl = request.getParameter("redirectUrl");
		
	    int r = Auth.login(username, pwd);
	    JSONObject object = new JSONObject();
	    if (r == 1) {
	    	request.getSession().setAttribute("logged", true);
	    	request.getSession().setAttribute("username", username);
	    	
	    	String token = AppUtils.genToken(username, ""+System.currentTimeMillis());
	    	object.put("token", token);
	    	
	    	User user = new User();
	    	user.setLogin(true);
	    	user.setUsername(username);
	    	user.getRegistered().add(redirectUrl);
	    	user.setAccessToken(token);
	    	AuthContext.getInstance().addUser(user);
		}
	    
		object.put("r", r);
	    object.put("redirectUrl", redirectUrl);
	    object.put("username", username);
	    response.setContentType("text.json; charset=uft-8"); 
    	
	    respWrite(response, object.toJSONString());
	}
	
	@RequestMapping("/verify")
	public void verify(HttpServletRequest request, HttpServletResponse response){
		String token = request.getParameter("token");
		String username = request.getParameter("username");
		if (StringUtil.isEmpty(username)) {
			Object temp = request.getSession().getAttribute("username");
			if (null != temp) {
				username = temp.toString();
			}
		}
		
		int r = Auth.verify(username, token);
		JSONObject object = new JSONObject();
		object.put("r", r);
		
		respWrite(response, object.toJSONString());
	}
	
	private void respWrite(HttpServletResponse resp, String content){
		try {
			resp.getWriter().write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response){
		
	}
}
