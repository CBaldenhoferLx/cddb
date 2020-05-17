package com.luxoft.cddb.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.cddb.beans.Identifyable;
import com.luxoft.cddb.repositories.IDefaultRepository;
import com.luxoft.cddb.services.IDefaultService;

public abstract class BasicTests<BEANTYPE extends Identifyable, REPOTYPE extends IDefaultRepository<BEANTYPE>, SERVICETYPE extends IDefaultService<BEANTYPE>> {

	@Autowired
	protected REPOTYPE repo;

	@Autowired
	protected SERVICETYPE service;
	
	/*
	@Autowired
	protected TestEntityManager entityManager;
	*/
	
	protected abstract BEANTYPE createNewBean();
	protected abstract void fillMandatoryField(BEANTYPE bean);
	
	public String getRandomString(int length) {
	    byte[] array = new byte[length]; // length is bounded by 7
	    new Random().nextBytes(array);
	    return new String(array, Charset.forName("UTF-8"));
	}
	
	@Test
	public void createDeleteTest() {
		BEANTYPE obj = createNewBean();
		fillMandatoryField(obj);
		
		service.save(obj);
		
		int id = obj.getId();
		
		BEANTYPE checkObj = service.findById(id);
		assertNotNull(checkObj);
		assertEquals(id, checkObj.getId());
		
		service.delete(checkObj);
		
		BEANTYPE checkObj2 = service.findById(id);
		assertNull(checkObj2);
	}

}
