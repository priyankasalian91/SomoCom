package com.aipl.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;
import com.aipl.store.domain.User;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByUser(User user); 
	
	@EntityGraph(attributePaths = { "cartItems", "payment", "shipping" })
	Order findEagerById(Long id);
	
	List<Order> findAll(); 
	
	//Order updateOrder(Order order);
	
}
