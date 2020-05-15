package com.luxoft.cddb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

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
	public int getCount() {
		return (int) repository.count();
	}

	@Override
	public BEANTYPE get(int id) {
		return repository.findById(id).get();
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
