package com.luxoft.cddb.jpa;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxoft.cddb.Application;
import com.luxoft.cddb.beans.structure.ComponentBean;
import com.luxoft.cddb.beans.structure.DomainBean;
import com.luxoft.cddb.beans.structure.FeatureSetBean;
import com.luxoft.cddb.beans.structure.ProgramBean;
import com.luxoft.cddb.repositories.IComponentRepository;
import com.luxoft.cddb.repositories.IDomainRepository;
import com.luxoft.cddb.repositories.IFeatureSetRepository;
import com.luxoft.cddb.repositories.IProgramRepository;
import com.luxoft.cddb.services.IFeatureSetService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
public class FeatureSetTests extends BasicTests<FeatureSetBean, IFeatureSetRepository, IFeatureSetService> {
	
	@Autowired IDomainRepository domainRepo;
	
	@Autowired IComponentRepository componentRepo;
	
	@Autowired IProgramRepository programRepo;

	@Override
	protected FeatureSetBean createNewBean() {
		return new FeatureSetBean();
	}

	@Override
	protected void fillMandatoryField(FeatureSetBean bean) {
		bean.setName(getRandomString(10));
		
		addDummyDomain(bean);
		addDummyComponent(bean);
		addDummyProgram(bean);
	}
	
	protected void addDummyDomain(FeatureSetBean bean) {
		DomainBean domain = new DomainBean(getRandomString(5));
		bean.setDomain(domain);
		domainRepo.save(domain);
	}
	
	protected void addDummyComponent(FeatureSetBean bean) {
		ComponentBean component = new ComponentBean(getRandomString(5));
		bean.setComponent(component);
		componentRepo.save(component);
	}
	
	protected void addDummyProgram(FeatureSetBean bean) {
		ProgramBean program = new ProgramBean(getRandomString(5));
		bean.setProgram(program);
		programRepo.save(program);
	}
	
	protected FeatureSetBean createDummyFs(String name, DomainBean parentDomain) {
		FeatureSetBean fs = new FeatureSetBean();
		
		fillMandatoryField(fs);
		
		fs.setName(name);
		fs.setDomain(parentDomain);		
		
		return fs;
	}
	
	@Test
	public void listDomainFeatureSetsTest() {
		
		DomainBean domain = new DomainBean("ParentDomain");
		domainRepo.save(domain);
		
		int domainId = domain.getId();
		assertTrue(domainId>0);
		
		FeatureSetBean fs1 = createDummyFs("testfs1", domain);
		FeatureSetBean fs2 = createDummyFs("testfs2", domain);
		
		service.save(fs1);
		service.save(fs2);
		
		List<FeatureSetBean> sets = service.findAll(domain);
		assertEquals(2, sets.size());
	}
	

}
