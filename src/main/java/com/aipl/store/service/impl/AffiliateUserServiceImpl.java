package com.aipl.store.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aipl.store.domain.AffiliateRole;
import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.User;
import com.aipl.store.domain.security.Role;
import com.aipl.store.domain.security.UserRole;
import com.aipl.store.dto.AffiliateUserRegistrationDto;
import com.aipl.store.repository.AffiliateUserRepository;
import com.aipl.store.service.AffiliateUserService;

import utility.SecurityUtility;

@Service
public class AffiliateUserServiceImpl implements AffiliateUserService {
	
	@Autowired
    private AffiliateUserRepository affiliateUserRepository;
    
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@Override
	public AffiliateUser findByUsername(String username) {
		return affiliateUserRepository.findByUsername(username);
	}
	
	@Override
	public AffiliateUser findByEmail(String email) {
        return affiliateUserRepository.findByEmail(email);
	}

    public AffiliateUser save(AffiliateUserRegistrationDto registration){
    	AffiliateUser user = new AffiliateUser();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setUsername(registration.getUsername());
        user.setPhone(registration.getPhone());
        user.setInviteCode(registration.getInviteCode());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new AffiliateRole("ROLE_USER")));
        user.setPincode(registration.getPincode());
        return affiliateUserRepository.save(user);
    }

	@Override
	public Optional<AffiliateUser> findById(Long id) {
		return affiliateUserRepository.findById(id);
	}

	@Override
	public AffiliateUser save(AffiliateUser affiliateUser) {
		
		return affiliateUserRepository.save(affiliateUser);
	}

}
