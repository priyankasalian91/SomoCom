package com.aipl.store.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.aipl.store.domain.AffiliateRole;
import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.repository.AffiliateUserRepository;

public class AffiliateUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AffiliateUserRepository affiliateUserRepository;
    
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AffiliateUser affiUser = affiliateUserRepository.findByUsername(username);
        if (affiUser == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (AffiliateRole role : affiUser.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(affiUser.getUsername(), affiUser.getPassword(), grantedAuthorities);
    }

}
