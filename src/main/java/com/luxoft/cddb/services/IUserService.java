package com.luxoft.cddb.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.luxoft.cddb.beans.user.UserBean;

public interface IUserService extends IDefaultService<UserBean>, UserDetailsService {
	
	public Optional<UserBean> findByName(String username);
	
	public void addRole(UserBean user, String userRole);
	
	public String encodePassword(String clearPassword);
	
	public void updateEmail(String username, String email);

}
