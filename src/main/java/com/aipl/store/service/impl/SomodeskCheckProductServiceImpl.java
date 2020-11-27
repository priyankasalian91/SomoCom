package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipl.store.domain.SomodeskCheckProduct;
import com.aipl.store.repository.SomodeskCheckProductRepository;
import com.aipl.store.service.SomodeskCheckProductService;

@Service
public class SomodeskCheckProductServiceImpl implements SomodeskCheckProductService {

	@Autowired
	SomodeskCheckProductRepository somodeskCheckProductRepository;
	@Override
	public SomodeskCheckProduct save(SomodeskCheckProduct details) {
		return somodeskCheckProductRepository.save(details);
	}

}
