package com.luxoft.cddb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxoft.cddb.beans.master.ElementBean;

@Repository
public interface IElementRepository extends CrudRepository<ElementBean, Integer> {

}
