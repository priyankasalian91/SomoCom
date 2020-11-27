package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipl.store.domain.SomodeskProductLinkDetails;
import com.aipl.store.repository.SomodeskTest1Repository;
import com.aipl.store.service.SomodeskTest1Service;

@Service
public class SomodeskTest1ServiceImpl  implements SomodeskTest1Service{

	@Autowired
	SomodeskTest1Repository somodeskTest1Repository;
	
	@Override
	public SomodeskProductLinkDetails save(SomodeskProductLinkDetails details) {
		return somodeskTest1Repository.save(details);
	}

}
