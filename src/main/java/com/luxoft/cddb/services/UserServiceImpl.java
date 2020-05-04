package com.luxoft.cddb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.UserBean;
import com.luxoft.cddb.beans.UserRoleBean;
import com.luxoft.cddb.repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
	
	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private IUserRoleService userRoleService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl() {
	}
	
	public List<UserBean> findAll() {
		return (List<UserBean>) repository.findAll();
	}
	
	public int getUserCount() {
		return (int)repository.count();
	}
	
	public List<UserBean> fetchUsers(int offset, int limit) {
		return findAll();
	}
	
	public UserBean get(int id) {
		return repository.findById(id).get();
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
	public void save(UserBean user) {
		repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserBean> result = findByUsername(username);
		
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
	public Optional<UserBean> findByUsername(String username) {
		for (UserBean u : repository.findAll()) {
			if (u.getUsername().equals(username)) {
				return Optional.of(u);
			}
		}
		
		return Optional.ofNullable(null);
	}

	@Override
	public void updateEmail(String username, String email) {
		Optional<UserBean> userOpt = findByUsername(username);
		
		if (userOpt.isPresent()) {
			UserBean user = userOpt.get();
			if (user.getEmail()==null || (user.getEmail()!=null && !user.getEmail().equals(email))) {
				System.out.println("Updating email of user " + user.getUsername());
				user.setEmail(email);
				save(user);
			}
		}
	}

	@Override
	public void delete(UserBean user) {
		repository.delete(user);
	}

}
