package com.luxoft.cddb.services;

import org.springframework.stereotype.Service;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.repositories.IDomainRepository;

@Service
public class DomainServiceImpl extends DefaultServiceImpl<DomainBean, IDomainRepository> implements IDomainService {
	
	public DomainServiceImpl() {
	}
	
}
