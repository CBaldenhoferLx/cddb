package com.luxoft.cddb.services;

import java.util.List;

import com.luxoft.cddb.beans.structure.DomainBean;

public interface IDomainService extends IDefaultService<DomainBean>{

	public List<DomainBean> getRootItems();

}
