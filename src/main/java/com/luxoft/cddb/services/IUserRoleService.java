package com.luxoft.cddb.services;

import java.util.List;
import java.util.Optional;

import com.luxoft.cddb.beans.UserRoleBean;

public interface IUserRoleService {
	
	public List<UserRoleBean> findAll();
	public int getUserRoleCount();

	Optional<UserRoleBean> findByName(String name);

}
