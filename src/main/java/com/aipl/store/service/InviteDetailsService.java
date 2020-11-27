package com.aipl.store.service;

import java.util.Optional;

import com.aipl.store.domain.InviteDetails;

public interface InviteDetailsService {
	
	InviteDetails save(InviteDetails details);
	Optional<InviteDetails> findById(Long id);

}
