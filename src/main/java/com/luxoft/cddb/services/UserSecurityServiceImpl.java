package com.luxoft.cddb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
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
		String username = null;
		
		if (auth instanceof OAuth2AuthenticationToken) {
			OAuth2User user = ((OAuth2AuthenticationToken)auth).getPrincipal();
			username = user.getAttribute("email");
		} else if (auth instanceof UsernamePasswordAuthenticationToken) {
			UserBean user = (UserBean)((UsernamePasswordAuthenticationToken)auth).getPrincipal();
			username = user.getUsername();
		}
		
		System.out.println(username);
		
		if (username!=null) {
			return userService.findByUsername(username);
		} else {
			return Optional.ofNullable(null);
		}
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
    public void listLoggedInUsers() {
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        
        System.out.println("PRINCIPALE: " + allPrincipals.size());

        for(final Object principal : allPrincipals) {
        	System.out.println(principal.getClass().getCanonicalName());
            if(principal instanceof UserBean) {
                final UserBean user = (UserBean) principal;

                // Do something with user
                System.out.println(user.getUsername());
            } else if (principal instanceof DefaultOidcUser) {
            	final DefaultOidcUser oidUser = (DefaultOidcUser)principal;
            	
            	System.out.println(oidUser.toString());
            }
        }
    }

}
