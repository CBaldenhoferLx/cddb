package com.luxoft.cddb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.luxoft.cddb.SecurityUtils;
import com.luxoft.cddb.beans.UserBean;

@Service
public class UserSecurityServiceImpl implements IUserSecurityService {
	
	@Autowired
	private IUserService userService;
	
	@Override
	public void internalLogin(String username) {
		SecurityUtils.internalLogin(username);
	}

	@Override
	public Optional<UserBean> getCurrentUser() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	@Override
	public Optional<String> getCurrentUsername() {
		if (getCurrentUser().isPresent()) {
			return Optional.of(getCurrentUser().get().getUsername());
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public boolean hasRole(String role) {
		Optional<UserBean> user = getCurrentUser();
		
		if (user.isPresent()) {
			for (GrantedAuthority auth : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
				if (auth.getAuthority().equals(role)) return true;
			}
		}
		
		return false;
	}

}
