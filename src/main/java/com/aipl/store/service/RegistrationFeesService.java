package com.aipl.store.service;

import com.aipl.store.domain.AffiliateRegisterFees;

public interface RegistrationFeesService {

	AffiliateRegisterFees save(AffiliateRegisterFees registerFeesDetails);
	AffiliateRegisterFees findByEmail(String email);
}
