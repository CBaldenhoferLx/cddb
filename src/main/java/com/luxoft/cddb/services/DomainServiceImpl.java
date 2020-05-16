package com.luxoft.cddb.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.repositories.IDomainRepository;

@Service
public class DomainServiceImpl extends DefaultServiceImpl<DomainBean, IDomainRepository> implements IDomainService {
	
	public DomainServiceImpl() {
	}

	@Override
	public List<DomainBean> getRootItems() {
		return repository.findRoots();
	}
	
}
