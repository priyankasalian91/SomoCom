package com.aipl.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.aipl.store.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByName(String name);

}
