package com.luxoft.cddb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.beans.structure.FeatureSetBean;

@Repository
public interface IFeatureSetRepository extends IDefaultRepository<FeatureSetBean> {

	@Query("SELECT fs FROM FeatureSetBean fs WHERE fs.domain=?1")
	List<FeatureSetBean> findAll(DomainBean parentDomain);

}
