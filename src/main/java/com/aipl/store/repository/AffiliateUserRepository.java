package com.aipl.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aipl.store.domain.AffiliateUser;


@Repository
public interface AffiliateUserRepository extends JpaRepository<AffiliateUser, Long> {
	AffiliateUser findByEmail(String email);
	AffiliateUser findByUsername(String username);
	Optional<AffiliateUser> findById(Long id);

}
