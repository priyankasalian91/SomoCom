package com.aipl.store.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import com.aipl.store.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
		
	@EntityGraph(value = "UserComplete", type=EntityGraphType.FETCH)
	User findByUsername(String username);
	@EntityGraph(value = "UserComplete", type=EntityGraphType.FETCH)		
	User findByEmail(String email);

}
