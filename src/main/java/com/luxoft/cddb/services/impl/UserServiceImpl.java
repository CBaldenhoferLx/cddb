package com.luxoft.cddb.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.user.UserBean;
import com.luxoft.cddb.beans.user.UserRoleBean;
import com.luxoft.cddb.repositories.IUserRepository;
import com.luxoft.cddb.services.IUserRoleService;
import com.luxoft.cddb.services.IUserService;

@Service
public class UserServiceImpl extends DefaultServiceImpl<UserBean, IUserRepository> implements IUserService {
	
	@Autowired
	private IUserRoleService userRoleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl() {
	}
	
	@Override
	public void addRole(UserBean user, String userRole) {
		if (!user.hasRole(userRole)) {
			Optional<UserRoleBean> ur = userRoleService.findByName(userRole);
			if (ur.isPresent()) {
				user.addRole(ur.get());
			} else {
				System.out.println("Cannot find role");
			}
		} else {
			System.out.println("Role already added");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserBean> result = findByName(username);
		
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	@Override
	public String encodePassword(String clearPassword) {
		return passwordEncoder.encode(clearPassword);
	}

	@Override
	public Optional<UserBean> findByName(String username) {
		for (UserBean u : repository.findAll()) {
			if (u.getUsername().equals(username)) {
				return Optional.of(u);
			}
		}
		
		return Optional.ofNullable(null);
	}

	@Override
	public void updateEmail(String username, String email) {
		Optional<UserBean> userOpt = findByName(username);
		
		if (userOpt.isPresent()) {
			UserBean user = userOpt.get();
			if (user.getEmail()==null || (user.getEmail()!=null && !user.getEmail().equals(email))) {
				System.out.println("Updating email of user " + user.getUsername());
				user.setEmail(email);
				save(user);
			}
		}
	}

}
