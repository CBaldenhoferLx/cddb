package com.luxoft.cddb.services;

import java.util.Optional;
import java.util.Set;

import com.luxoft.cddb.beans.UserBean;

public interface IUserSecurityService {

	public Optional<UserBean> getCurrentUser();
	public Optional<String> getCurrentUsername();
	
	public boolean hasRole(String role);
	
	public void internalLogin(String username);
	
	public Set<UserBean> listLoggedInUsers();
	
	public Optional<UserBean> getUserFromPrincipal(Object principal);

}
