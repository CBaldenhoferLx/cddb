package com.luxoft.cddb.beans.structure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.luxoft.cddb.beans.DefaultBean;

@Entity
@Table(name="cd_domains")
public class DomainBean extends DefaultBean {
	
	public DomainBean() {
	}
	
	public DomainBean(String name) {
		super(name);
	}

	@ManyToOne(optional = true)
	private DomainBean parentDomain;

	@OneToMany//(fetch = FetchType.EAGER)
	@JoinColumn(name="parent_domain_id")
	@OrderBy("name")
	private List<DomainBean> subDomains = new ArrayList<>();

	public DomainBean getParentDomain() {
		return parentDomain;
	}

	public void setParentDomain(DomainBean parentDomain) {
		this.parentDomain = parentDomain;
	}

	public List<DomainBean> getSubDomains() {
		return subDomains;
	}

	public void setSubDomains(List<DomainBean> subDomains) {
		this.subDomains = subDomains;
	}
	
	
	
	
	
}
