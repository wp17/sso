/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.github.wp17.sso.server.web.filter;

import java.io.IOException;

import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.sso.server.util.captcha.JCaptcha;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JCaptchaValidateFilter implements Filter{

    private boolean jcaptchaEbabled = true;//是否开启验证码支持

    private static final String jcaptchaParam = "jcaptchaCode";//前台提交的验证码参数名

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String temp = filterConfig.getInitParameter("jcaptchaEbabled");
		jcaptchaEbabled = temp == null ? false : Boolean.valueOf(temp);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (isAccessAllowed(request)) {
			chain.doFilter(request, response);
		}else {
			 JSONObject object = new JSONObject();
			 object.put("r", 2);
			 response.getWriter().write(object.toJSONString());
			 response.getWriter().flush();
			 response.getWriter().close();
		}
	}
	
	private boolean isAccessAllowed(ServletRequest request) {
        if (jcaptchaEbabled == false) {
            return true;
        }
        
        HttpServletRequest httpServletRequest = WebUtils.getNativeRequest(request, HttpServletRequest.class);
        
        if (httpServletRequest.getSession(false) == null) return false;
        
        String sessionId = httpServletRequest.getSession().getId();
        String captcha = httpServletRequest.getParameter(jcaptchaParam);
        
        return JCaptcha.validateResponse(sessionId, captcha);
    }
	
	@Override
	public void destroy() {
		
	}
}
