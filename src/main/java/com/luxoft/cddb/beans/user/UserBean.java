package com.luxoft.cddb.beans.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.luxoft.cddb.beans.Identifyable;

@SuppressWarnings("serial")
@Entity
@Table(name="users")
public class UserBean extends Identifyable implements UserDetails {

	public enum UserType {
		INTERNAL,
		LUXOFT,
		GOOGLE
	}
	
    public UserBean() {
		super();
	}
    
    public UserBean(String username, UserType type) {
		super();
		this.username = username;
		this.type = type;
	}

	@Column(nullable = false, unique = true)
	private String username;
    
    @Column(nullable = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserType type = UserType.INTERNAL;
    
    @Column
    private String passwordHash;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleBean> userRoles = new ArrayList<>();
	
	@Override
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public List<UserRoleBean> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRoleBean> userRoles) {
		this.userRoles = userRoles;
	}

	public void setUserRoles(Set<UserRoleBean> selectedItems) {
		this.userRoles = new ArrayList<UserRoleBean>(selectedItems);
	}

	public boolean hasRole(String userRole) {
		for (UserRoleBean ur : userRoles) {
			if (ur.getName().equals(userRole)) return true;
		}
		
		return false;
	}
	
	public void clearRoles() {
		this.userRoles.clear();
	}

	public boolean hasRole(UserRoleBean userRole) {
		return hasRole(userRole.getName());
	}
	
	public void addRole(UserRoleBean userRole) {
		if (!hasRole(userRole)) {
			userRoles.add(userRole);
		}
	}
	
	public static List<String> getTypeNames() {
		List<String> returnTypes = new ArrayList<>();
		
		for (UserType t : UserType.values()) {
			returnTypes.add(convertTypeName(t));
		}
		
		return returnTypes;
	}
	
	public static String convertTypeName(UserType type) {
		return type.name();
	}
	
	public static UserType convertTypeName(String type) {
		for (UserType t : UserType.values()) {
			if (convertTypeName(t).equals(type)) return t;
		}
		
		return UserType.INTERNAL;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		
		for (UserRoleBean ur : userRoles) {
			auths.add(new SimpleGrantedAuthority(ur.getName()));
		}
		
		return auths;
	}
	@Override
	public String getPassword() {
		return getPasswordHash();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
