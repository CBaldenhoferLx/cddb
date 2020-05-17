package com.luxoft.cddb.beans.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.luxoft.cddb.beans.DefaultBean;

@Entity
@Table(name="user_roles")
public class UserRoleBean extends DefaultBean {

	public static final String USER_ADMIN = "USER_ADMIN";
	public static final String DATA_READER = "DATA_READER";
	public static final String DATA_WRITER = "DATA_WRITER";
	public static final String DATA_ADMIN = "DATA_ADMIN";
	public static final String RELEASE_WRITER = "RELEASE_WRITER";
	public static final String RELEASE_ADMIN = "RELEASE_ADMIN";
	public static final String EXPORT_ADMIN = "EXPORT_ADMIN";
	
	public UserRoleBean() {
		super();
	}

}
