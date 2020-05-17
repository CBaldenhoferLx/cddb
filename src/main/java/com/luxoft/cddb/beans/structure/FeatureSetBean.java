package com.luxoft.cddb.beans.structure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.luxoft.cddb.beans.DefaultBean;
import com.luxoft.cddb.beans.user.UserBean;

@Entity
@Table(name="cd_featuresets")
public class FeatureSetBean extends DefaultBean {
	
	@ManyToOne(optional = false)
	private DomainBean domain;
	
	@ManyToOne(optional = true)
	private UserBean lockedBy;
	
	@ManyToOne(optional = false)
	private ProgramBean program;
	
	@ManyToOne(optional = false)
	private ComponentBean component;
	
	@OneToMany(mappedBy = "parentFeatureSet") // TODO: , cascade = CascadeType.REMOVE)
	private List<FeatureSetAssetBean> attachments = new ArrayList<>();
	
	public FeatureSetBean() {
		super();
	}

	public FeatureSetBean(String name) {
		super(name);
	}

	public DomainBean getDomain() {
		return domain;
	}

	public void setDomain(DomainBean domain) {
		this.domain = domain;
	}

	public UserBean getLockedBy() {
		return lockedBy;
	}

	public void setLockedBy(UserBean lockedBy) {
		this.lockedBy = lockedBy;
	}

	public ProgramBean getProgram() {
		return program;
	}

	public void setProgram(ProgramBean program) {
		this.program = program;
	}

	public ComponentBean getComponent() {
		return component;
	}

	public void setComponent(ComponentBean component) {
		this.component = component;
	}

	public List<FeatureSetAssetBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<FeatureSetAssetBean> attachments) {
		this.attachments = attachments;
	}
	
	

}
