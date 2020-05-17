package com.luxoft.cddb.beans.structure;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.luxoft.cddb.beans.DefaultBean;

@Entity
@Table(name = "cd_components")
public class ComponentBean extends DefaultBean {

	public ComponentBean() {
		super();
	}

	public ComponentBean(String name) {
		super(name);
	}
	
	

}
