package com.luxoft.cddb.jpa;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.repositories.IDomainRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DomainTests {
	
	@Autowired
	private IDomainRepository domainRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void basicTest() {
		assertEquals(0, domainRepo.count());
		
		DomainBean l1Domain = new DomainBean("level 1");
		domainRepo.save(l1Domain);
		
		entityManager.persistFlushFind(l1Domain);
		
		assertTrue(l1Domain.getId()>0);
		int l1Id = l1Domain.getId();
	
		DomainBean l21Domain = new DomainBean("level 2.1");
		DomainBean l22Domain = new DomainBean("level 2.2");
		
		l21Domain.setParentDomain(l1Domain);
		l22Domain.setParentDomain(l1Domain);

		domainRepo.save(l21Domain);
		domainRepo.save(l22Domain);
		
		assertTrue(l21Domain.getId()>0);
		assertTrue(l22Domain.getId()>0);
		
		int l21Id = l21Domain.getId();
		int l22Id = l22Domain.getId();
		
		assertEquals(3, domainRepo.count());
		Optional<DomainBean> l1DomainCheck = domainRepo.findById(l1Id);
		assertNull(l1DomainCheck.get().getParentDomain());
		assertTrue(l1DomainCheck.isPresent());
		assertEquals(2, l1DomainCheck.get().getSubDomains().size());

		Optional<DomainBean> l21DomainCheck = domainRepo.findById(l21Id);
		assertTrue(l21DomainCheck.isPresent());
		assertEquals(l1DomainCheck.get().getId(), l21DomainCheck.get().getParentDomain().getId());
		
		Optional<DomainBean> l22DomainCheck = domainRepo.findById(l22Id);
		assertTrue(l22DomainCheck.isPresent());
		assertEquals(l1DomainCheck.get().getId(), l22DomainCheck.get().getParentDomain().getId());
	}

}
