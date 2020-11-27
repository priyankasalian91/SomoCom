package com.aipl.store.service;


import java.util.List;

import com.aipl.store.domain.User;

public interface UserService {
	
	User findById(Long id);
	
	User findByUsername(String username);
		
	User findByEmail(String email);
		
	void save(User user);
	
	User createUser(String username,String email,  String password, List<String> roles,String pincode);
	User createUser(String username, String firstname,String lastname);


}
