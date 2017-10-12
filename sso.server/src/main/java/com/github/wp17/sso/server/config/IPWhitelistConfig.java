package com.github.wp17.sso.server.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IPWhitelistConfig {
	private IPWhitelistConfig(){}
	private static final IPWhitelistConfig instance = new IPWhitelistConfig();
	public static final IPWhitelistConfig getInstance(){
		return instance;
	}
	private List<String> ipList = new ArrayList<String>();
	public void init(String[] strings){
		Collections.addAll(ipList, strings);
	}
	
	public static void main(String[] args) {
		List<String> ipList = new ArrayList<String>();
		Collections.addAll(ipList, new String[]{"111", "222", "333"});
		
		for (String string : ipList) {
			System.out.println(string);
		}
	}
	
	public boolean contains(String host){
		return ipList.contains(host);
	}
}
