package com.aipl.store.service;

import java.util.List;
import java.util.Optional;

import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.User;
import com.aipl.store.dto.AffiliateUserRegistrationDto;


public interface AffiliateUserService {

    AffiliateUser findByEmail(String email);
    
    AffiliateUser findByUsername(String username);

    AffiliateUser save(AffiliateUserRegistrationDto registration);
    
    AffiliateUser save(AffiliateUser affiliateUser);
    
    Optional<AffiliateUser> findById(Long id);
    

}
