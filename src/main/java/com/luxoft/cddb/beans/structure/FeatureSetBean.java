package com.luxoft.cddb.beans.structure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

}
