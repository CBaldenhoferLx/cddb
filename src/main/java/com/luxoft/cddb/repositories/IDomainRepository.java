package com.luxoft.cddb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luxoft.cddb.beans.structure.DomainBean;

@Repository
public interface IDomainRepository extends CrudRepository<DomainBean, Integer> {
	
	@Query("SELECT d FROM DomainBean d WHERE d.parentDomain IS NULL")
	List<DomainBean> findRoots();
	
}
