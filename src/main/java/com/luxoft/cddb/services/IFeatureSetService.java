package com.luxoft.cddb.services;

import java.util.List;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.beans.structure.FeatureSetBean;

public interface IFeatureSetService extends IDefaultService<FeatureSetBean> {

	List<FeatureSetBean> findAll(DomainBean parentDomain);
	
}
