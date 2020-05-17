package com.luxoft.cddb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.luxoft.cddb.beans.Identifyable;

public interface IDefaultRepository<BEANTYPE extends Identifyable> extends CrudRepository<BEANTYPE, Integer> {

}
