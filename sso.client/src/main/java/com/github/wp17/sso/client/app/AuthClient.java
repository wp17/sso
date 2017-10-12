package com.github.wp17.sso.client.app;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.sso.client.util.ExceptionUtils;

public class AuthClient {
	private AuthInfo authInfo;
	private CloseableHttpClient httpClient;
	private HttpClientContext httpContext;
	
	public AuthClient(AuthInfo authInfo){
		this.authInfo = authInfo;
		this.httpClient = DefaultCookieHttpClientBuilder.create().build();
		this.httpContext = HttpClientContext.adapt(new BasicHttpContext());
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		httpContext.setCookieStore(cookieStore);
	}
	
	public LoginResult login(String username, String pwd) {
		try {
			URI uri = new URIBuilder()
			.setScheme(authInfo.getScheme())
			.setHost(authInfo.getHost())
			.setPath(authInfo.getLoginPath())
			.setParameter("username", username)
			.setParameter("pwd", pwd)
			.build();
			
			JSONObject object = synRequest(uri);
			if (null != object) {
				int r = object.getIntValue("r");
		        String token = object.getString("token");
		        return new LoginResult(r, token, username);
			}
		} catch (Exception e) {
			ExceptionUtils.logException(e);
		}
		return new LoginResult();
    }
	
	public VerifyResult verify(String username, String token) {
		try {
			URI uri = new URIBuilder()
			.setScheme(authInfo.getScheme())
			.setHost(authInfo.getHost())
			.setPath(authInfo.getVerifyPath())
			.setParameter("username", username)
			.setParameter("token", token)
			.build();
			
			JSONObject object = synRequest(uri);
			if (null != object) {
				int r = object.getIntValue("r");
		        return new VerifyResult(r);
			}
		} catch (Exception e) {
			ExceptionUtils.logException(e);
		}
		return new VerifyResult(0);
	}
	
	private JSONObject synRequest(URI uri) throws Exception {
		HttpGet httpget = new HttpGet(uri);
		try(CloseableHttpResponse resp = httpClient.execute(httpget, httpContext)){
			HttpEntity entity = resp.getEntity();
			if (entity != null) {
			    byte[] bytes = new byte[(int) entity.getContentLength()];
		        entity.getContent().read(bytes);
		        return JSONObject.parseObject(new String(bytes, "UTF-8"));
			}
		}
		return null;
	}
	
	public void close() throws IOException{
		if (null != httpClient) {
			httpClient.close();
		}
	}
	
	public static void main(String[] args) {
		LoginResult loginResult = DefaultAuthClientHolder.getDefaultAuthClient().login("wang", "123");
		
		if (null != loginResult && loginResult.getR() == 1) {
			VerifyResult verifyResult = DefaultAuthClientHolder.getDefaultAuthClient().verify("wang", loginResult.getToken());
			System.out.println(verifyResult.getR() == 1);
		}
		
	}
}
