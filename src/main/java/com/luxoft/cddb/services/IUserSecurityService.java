package com.luxoft.cddb.services;

import java.util.Optional;

import com.luxoft.cddb.beans.UserBean;

public interface IUserSecurityService {

	public Optional<UserBean> getCurrentUser();
	public Optional<String> getCurrentUsername();
	
	public boolean hasRole(String role);
	
	public void internalLogin(String username);

}
