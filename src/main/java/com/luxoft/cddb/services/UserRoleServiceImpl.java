package com.luxoft.cddb.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.user.UserRoleBean;
import com.luxoft.cddb.repositories.IUserRoleRepository;

@Service
public class UserRoleServiceImpl implements IUserRoleService {

	@Autowired
	private IUserRoleRepository repository;
	
	public UserRoleServiceImpl() {
	}
	
	@PostConstruct
	void setupRoles() {
		checkCreateRole(UserRoleBean.USER_ADMIN);
		checkCreateRole(UserRoleBean.DATA_READER);
		checkCreateRole(UserRoleBean.DATA_WRITER);
		checkCreateRole(UserRoleBean.DATA_ADMIN);
		checkCreateRole(UserRoleBean.RELEASE_WRITER);
		checkCreateRole(UserRoleBean.RELEASE_ADMIN);
		checkCreateRole(UserRoleBean.EXPORT_ADMIN);
	}
	
	private void checkCreateRole(String role) {
		findByName(role).ifPresentOrElse(
		(value) ->{
			System.out.println(value.getName() + " is present");}
		, 
		() ->
		{
			System.out.println("Creating role " + role);
			UserRoleBean ur = new UserRoleBean();
			ur.setId(0);
			ur.setName(role);
			repository.save(ur);
		});
	}

	@Override
	public Optional<UserRoleBean> findByName(String name) {
		for (UserRoleBean ur : repository.findAll()) {
			if (ur.getName().equals(name)) return Optional.of(ur);
		}
		
		return Optional.ofNullable(null);
	}

	@Override
	public List<UserRoleBean> findAll() {
		return (List<UserRoleBean>) repository.findAll();
	}

	@Override
	public int getUserRoleCount() {
		return (int) repository.count();
	}

}
