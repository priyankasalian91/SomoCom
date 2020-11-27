package com.aipl.store.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.InviteDetails;
import com.aipl.store.repository.InviteDetailsRepository;
import com.aipl.store.service.InviteDetailsService;

@Service
public class InviteDetailsServiceImpl  implements InviteDetailsService{
	
	@Autowired
	InviteDetailsRepository  inviteDetailsRepository;

	@Override
	public InviteDetails save(InviteDetails details) {
		
		return inviteDetailsRepository.save(details);
	}

	@Override
	public Optional<InviteDetails> findById(Long id) {
		return inviteDetailsRepository.findById(id);
	}



}
