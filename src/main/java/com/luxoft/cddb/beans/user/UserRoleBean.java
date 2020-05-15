package com.luxoft.cddb.beans.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRoleBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2895305620948348260L;

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

	@Id
    @GeneratedValue
	private int id = 0;
    
    @Column(nullable = false)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
