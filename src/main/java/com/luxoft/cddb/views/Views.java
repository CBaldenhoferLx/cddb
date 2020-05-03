package com.luxoft.cddb.views;

public class Views {
	
	public static final String LOGIN_SUCCESS = "loginSuccess";
	public static final String LOGOUT_SUCCESS = "logoutSuccess";
	
	public static final String LOGIN_VIEW = "login";
	public static final String DASHBOARD_VIEW = "dashboard";
	
	public static final String USER_LIST_VIEW = "users";
	public static final String USER_DETAILS_VIEW = "userDetails";
	
	public static String withSlash(String url) {
		return "/" + url;
	}

}
