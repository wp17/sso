package com.github.wp17.sso.client.app;

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONObject;

public class DefaultCookieHttpClientBuilder extends HttpClientBuilder {
	private static final CookieStore COOKIE_STORE = new BasicCookieStore();
	
	public static HttpClientBuilder create(){
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultCookieStore(COOKIE_STORE);
		return builder;
	}
	
	public static void main(String[] args) throws Exception {
		URI login_uri = new URIBuilder()
		.setScheme("http")
		.setHost("localhost:8080")
		.setPath("/sso.server/auth/login")
		.setParameter("username", "wang")
		.setParameter("pwd", "123")
		.build();
		
		HttpGet httpget = new HttpGet(login_uri);
		
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		CloseableHttpResponse resp = httpclient.execute(httpget);
		
		String setCookie = resp.getFirstHeader("Set-Cookie").getValue();
		
		for (Header header : resp.getAllHeaders()) {
			System.out.println(header.getName() +"***"+header.getValue());
		}
		
		String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
		System.out.println("JSESSIONID:" + JSESSIONID);
		
		String accessToken = "";
		HttpEntity entity = resp.getEntity();
		if (entity != null) {
		    byte[] bytes = new byte[(int) entity.getContentLength()];
	        entity.getContent().read(bytes);
	      
	        String res = new String(bytes, "UTF-8");
	        System.out.println(res);
	        JSONObject jsonObject = JSONObject.parseObject(res);
	        accessToken = jsonObject.getString("token");
		}
		
		URI verify_uri = new URIBuilder()
		.setScheme("http")
		.setHost("localhost:8080")
		.setPath("/sso.server/auth/verify")
		.setParameter("username", "wang")
		.setParameter("token", accessToken)
		.build();
		
		HttpGet httpget1 = new HttpGet(verify_uri);
		
		CloseableHttpResponse resp1 = httpclient.execute(httpget1);
		
		HttpEntity entity1 = resp1.getEntity();
		if (entity1 != null) {
		    byte[] bytes = new byte[(int) entity1.getContentLength()];
	        entity1.getContent().read(bytes);
	      
	        String res = new String(bytes, "UTF-8");
	        System.out.println(res);
		}
		
		httpclient.close();
		System.out.println(httpclient);
	}
}
