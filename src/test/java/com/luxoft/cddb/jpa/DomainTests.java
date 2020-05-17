package com.luxoft.cddb.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxoft.cddb.Application;
import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.repositories.IDomainRepository;
import com.luxoft.cddb.services.IDomainService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
public class DomainTests extends BasicTests<DomainBean, IDomainRepository, IDomainService> {
	
	@Override
	protected DomainBean createNewBean() {
		return new DomainBean();
	}

	@Override
	protected void fillMandatoryField(DomainBean bean) {
		bean.setName(getRandomString(10));
	}
	
	@Test
	public void parentTest() {
		DomainBean l1Domain = new DomainBean("level 1");
		service.save(l1Domain);
		
		assertTrue(l1Domain.getId()>0);
		int l1Id = l1Domain.getId();
	
		DomainBean l21Domain = new DomainBean("level 2.1");
		DomainBean l22Domain = new DomainBean("level 2.2");
		
		l21Domain.setParentDomain(l1Domain);
		l22Domain.setParentDomain(l1Domain);

		service.save(l21Domain);
		service.save(l22Domain);
		
		assertTrue(l21Domain.getId()>0);
		assertTrue(l22Domain.getId()>0);
		
		int l21Id = l21Domain.getId();
		int l22Id = l22Domain.getId();
		
		assertEquals(3, service.count());
		DomainBean l1DomainCheck = service.findById(l1Id);
		assertNull(l1DomainCheck.getParentDomain());
		assertNotNull(l1DomainCheck);
		assertEquals(2, l1DomainCheck.getSubDomains().size());

		DomainBean l21DomainCheck = service.findById(l21Id);
		assertNotNull(l21DomainCheck);
		assertEquals(l1DomainCheck.getId(), l21DomainCheck.getParentDomain().getId());
		
		DomainBean l22DomainCheck = service.findById(l22Id);
		assertNotNull(l22DomainCheck);
		assertEquals(l1DomainCheck.getId(), l22DomainCheck.getParentDomain().getId());
	}





}
