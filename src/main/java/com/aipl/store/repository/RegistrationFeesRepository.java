package com.aipl.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipl.store.domain.AffiliateRegisterFees;
import com.aipl.store.domain.SomodeskProductLinkDetails;

@Repository
public interface RegistrationFeesRepository  extends JpaRepository<AffiliateRegisterFees, Long>{

	AffiliateRegisterFees findByEmail(String email);
}
