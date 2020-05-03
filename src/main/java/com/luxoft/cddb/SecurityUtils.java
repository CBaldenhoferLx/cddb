package com.luxoft.cddb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.luxoft.cddb.beans.UserBean;
import com.luxoft.cddb.beans.UserRoleBean;
import com.luxoft.cddb.services.IUserService;
import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;

public final class SecurityUtils {

    private SecurityUtils() {
        // Util methods only
    }

    static boolean isFrameworkInternalRequest(HttpServletRequest request) { 
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
            && Stream.of(ServletHelper.RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    static boolean isUserLoggedIn() { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
            && !(authentication instanceof AnonymousAuthenticationToken)
            && authentication.isAuthenticated();
    }
    
    public static boolean isAccessGranted(Class<?> securedClass) {
        // Allow if no roles are required.
        Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
        if (secured == null) {
            return true; // 
        }

        // lookup needed role in user roles
        List<String> allowedRoles = Arrays.asList(secured.value());
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        return userAuthentication.getAuthorities().stream() // 
                .map(GrantedAuthority::getAuthority)
                .anyMatch(allowedRoles::contains);
    }
    
    public static void logoutUser() {
		SecurityContextHolder.clearContext();
    }    
    
    public static void oauth2Login(IUserService userService, String username) {
    	System.out.println("OAUTH2 LOGIN " + username);

    	Optional<UserBean> user = userService.findByUsername(username);
    	Authentication newAuth = new UsernamePasswordAuthenticationToken(user.get().getUsername(), null, user.get().getAuthorities());
    	SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public static void internalLogin(String username) {
    	System.out.println("INTERNAL LOGIN " + username);
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	
    	authorities.add(new SimpleGrantedAuthority(UserRoleBean.USER_ADMIN));
    	
    	Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
    			
    	SecurityContextHolder.getContext().setAuthentication(auth);
    }
}