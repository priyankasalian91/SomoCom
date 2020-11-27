package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipl.store.domain.AffiliateRegisterFees;
import com.aipl.store.repository.RegistrationFeesRepository;
import com.aipl.store.service.RegistrationFeesService;

@Service
public class RegistrationFeesServiceImpl implements RegistrationFeesService{

	@Autowired
	private RegistrationFeesRepository registrationFeesRepository; 
	@Override
	public AffiliateRegisterFees save(AffiliateRegisterFees registerFeesDetails) {
		return registrationFeesRepository.save(registerFeesDetails);
	}
	@Override
	public AffiliateRegisterFees findByEmail(String email) {
		return registrationFeesRepository.findByEmail(email);
	}

}
