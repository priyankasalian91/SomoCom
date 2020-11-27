package com.aipl.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aipl.store.domain.User;
import com.aipl.store.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = userRepository.findByEmail(email);

//		User user = userRepository.findByUsername(username);		
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}	
		return user;
	}
	
	public void authenticateUser(String email) {
		UserDetails userDetails = loadUserByUsername(email);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
