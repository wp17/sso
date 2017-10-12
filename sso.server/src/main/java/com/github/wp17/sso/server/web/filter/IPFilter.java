package com.github.wp17.sso.server.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.github.wp17.sso.server.config.IPWhitelistConfig;
import com.github.wp17.sso.server.util.StringUtil;

public class IPFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String whiteliststString = filterConfig.getInitParameter("whitelists");
		if (StringUtil.isNotEmpty(whiteliststString)) {
			String[] whitelists = whiteliststString.split(",");
			IPWhitelistConfig.getInstance().init(whitelists);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String host = request.getRemoteHost();
		if (IPWhitelistConfig.getInstance().contains(host)) {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}
}
