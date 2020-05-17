package com.luxoft.cddb.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.luxoft.cddb.services.IDefaultService;

public class DefaultServiceImpl<BEANTYPE, REPOSITORYTYPE extends CrudRepository<BEANTYPE, Integer>> implements IDefaultService<BEANTYPE> {
	
	@Autowired
	protected REPOSITORYTYPE repository;

	@Override
	public List<BEANTYPE> findAll() {
		return (List<BEANTYPE>) repository.findAll();
	}

	@Override
	public List<BEANTYPE> findAll(int offset, int limit) {
		return findAll();
	}

	@Override
	public int count() {
		return (int) repository.count();
	}

	@Override
	public BEANTYPE findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void save(BEANTYPE bean) {
		repository.save(bean);
	}

	@Override
	public void delete(BEANTYPE bean) {
		repository.delete(bean);
	}

}
