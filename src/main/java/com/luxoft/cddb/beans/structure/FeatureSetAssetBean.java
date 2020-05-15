package com.luxoft.cddb.beans.structure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.luxoft.cddb.beans.FileLinkBean;
import com.luxoft.cddb.beans.user.UserBean;

@Entity
@Table(name = "cd_featureset_assets")
public class FeatureSetAssetBean extends FileLinkBean {
	
	@Override
	protected String getFolderName() {
		return "cd_featureset_assets";
	}

	@ManyToOne(optional = false)
	private UserBean uploadedUser;
	
	@ManyToOne(optional = false)
	private FeatureSetBean parentFeatureSet;
	
	@Column(nullable = true)
	private int version;

}
