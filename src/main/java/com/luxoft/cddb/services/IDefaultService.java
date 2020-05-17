package com.luxoft.cddb.services;

import java.util.List;

public interface IDefaultService<BEANTYPE> {
	
	public List<BEANTYPE> findAll();
	public List<BEANTYPE> findAll(int offset, int limit);
	
	public int count();

	public BEANTYPE findById(int id);
	public void save(BEANTYPE bean);
	public void delete(BEANTYPE bean);

}
