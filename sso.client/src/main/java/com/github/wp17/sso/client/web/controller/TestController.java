package com.github.wp17.sso.client.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.wp17.sso.client.app.DefaultAuthClientHolder;
import com.github.wp17.sso.client.app.VerifyResult;
import com.github.wp17.sso.client.util.ExceptionUtils;

@Controller
@RequestMapping("/client")
public class TestController {
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		if (permissible(request)) {
			return new ModelAndView("index");
		}
		
		if (verify(request)) {
			request.getSession().setAttribute("status", 1);
			return new ModelAndView("index");
		}

		try {
			String redirectUrl = request.getRequestURL().toString();
			response.sendRedirect("http://localhost:8080/sso.server/auth/loginPage?redirectUrl="+redirectUrl);
		} catch (IOException e) {
			ExceptionUtils.logException(e);
		}
		
		return new ModelAndView("error");
	}
	
	private boolean permissible(HttpServletRequest request){
		Object status = request.getSession().getAttribute("status");
		int flag =  null == status ? 0 : (int) status;
		return flag == 1;
	}
	
	private boolean verify(HttpServletRequest request) {
		String username = request.getParameter("username");
		String token = request.getParameter("token");
		
		VerifyResult result = DefaultAuthClientHolder.getDefaultAuthClient().verify(username, token);
		return result.getR() == 1;
	}
}
