package com.luxoft.cddb.views;

public class Views {
	
	public static final String LOGIN_SUCCESS = "loginSuccess";
	public static final String LOGOUT_EXECUTE = "executeLogout";
	
	public static final String LOGIN_VIEW = "login";
	public static final String DASHBOARD_VIEW = "dashboard";
	
	public static final String USER_LIST_VIEW = "users";
	public static final String USER_DETAILS_VIEW = "userDetails";
	
	public static final String DOMAIN_LIST_VIEW = "domains";
	public static final String DOMAIN_DETAILS_VIEW = "domainDetails";

	public static final String CD_EDITOR_VIEW = "cdEditor";

	public static String withSlash(String url) {
		return "/" + url;
	}

}
