package com.luxoft.cddb.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.UserBean;
import com.luxoft.cddb.beans.UserRoleBean;
import com.vaadin.flow.server.VaadinSession;

@Service
public class UserSecurityServiceImpl implements IUserSecurityService {
	
    @Autowired
    private SessionRegistry sessionRegistry;
    
	@Autowired
	private IUserService userService;
	
	@Override
	@Deprecated
	public void internalLogin(String username) {
    	System.out.println("INTERNAL LOGIN " + username);
    	SecurityContext context = SecurityContextHolder.createEmptyContext();
    	
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority(UserRoleBean.USER_ADMIN));
    	
    	Authentication auth = new TestingAuthenticationToken(username, "", authorities);
    	
    	sessionRegistry.registerNewSession(VaadinSession.getCurrent().getSession().getId(), auth.getPrincipal());
    	
		SecurityContextHolder.setContext(context);
	}

	@Override
	public Optional<UserBean> getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return getUserFromPrincipal(auth.getPrincipal());
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
	
	@Override
    public Set<UserBean> listLoggedInUsers() {
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        
        System.out.println("PRINCIPALE: " + allPrincipals.size());
        
        Set<UserBean> users = new HashSet<>();
        Set<String> usernames = new HashSet<>();

        for(final Object principal : allPrincipals) {
        	Optional<UserBean> user = getUserFromPrincipal(principal);
        	if (user.isPresent()) {
        		if (!usernames.contains(user.get().getUsername())) {
            		users.add(user.get());
            		usernames.add(user.get().getUsername());
        		} else {
        			System.out.println("User " + user.get().getUsername() + " already listed - seems to have multiple sessions");
        		}
        	}
        }
        
        return users;
    }

	@Override
	public Optional<UserBean> getUserFromPrincipal(Object principal) {
		if (principal instanceof OAuth2User) {
			return userService.findByUsername(((OAuth2User)principal).getAttribute("email"));
		} else if (principal instanceof UserBean) {
			return Optional.of((UserBean)principal);
		}
		
		return Optional.ofNullable(null);
	}

}
