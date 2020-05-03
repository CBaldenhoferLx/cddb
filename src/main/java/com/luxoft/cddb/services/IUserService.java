package com.luxoft.cddb.services;

import java.util.List;
import java.util.Optional;

import com.luxoft.cddb.beans.UserBean;

public interface IUserService {
	
	public List<UserBean> findAll();
	public int getUserCount();
	
	public Optional<UserBean> findByUsername(String username);
	
	public List<UserBean> fetchUsers(int offset, int limit);
	public UserBean get(int id);
	public void save(UserBean user);
	
	public void addRole(UserBean user, String userRole);
	
	public String encodePassword(String clearPassword);

}
