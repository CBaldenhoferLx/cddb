package com.luxoft.cddb.beans;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;

@MappedSuperclass
public class DefaultBean extends Identifyable {
	
	
	@Column(nullable = false, length = JpaConstants.TEXT_MID)
	@OrderBy
	private String name;
	
	public DefaultBean() {
		super();
	}
	
	public DefaultBean(String name) {
		super();
		this.name = name;
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
