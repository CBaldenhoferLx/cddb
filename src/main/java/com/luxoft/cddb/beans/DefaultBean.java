package com.luxoft.cddb.beans;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

@MappedSuperclass
public class DefaultBean {
	
	@Id
	@GeneratedValue
	private int id = 0;
	
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
