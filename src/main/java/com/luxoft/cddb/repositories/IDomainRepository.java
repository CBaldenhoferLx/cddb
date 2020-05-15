package com.luxoft.cddb.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxoft.cddb.beans.structure.DomainBean;

@Repository
public interface IDomainRepository extends CrudRepository<DomainBean, Integer> {

}
