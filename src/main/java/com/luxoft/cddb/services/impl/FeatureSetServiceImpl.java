package com.luxoft.cddb.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.beans.structure.FeatureSetBean;
import com.luxoft.cddb.repositories.IFeatureSetRepository;
import com.luxoft.cddb.services.IFeatureSetService;

@Service
public class FeatureSetServiceImpl extends DefaultServiceImpl<FeatureSetBean, IFeatureSetRepository> implements IFeatureSetService {

	@Override
	public List<FeatureSetBean> findAll(DomainBean parentDomain) {
		return repository.findAll(parentDomain);
	}


}
