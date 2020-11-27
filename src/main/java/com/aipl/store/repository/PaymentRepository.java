package com.aipl.store.repository;


import org.springframework.data.repository.CrudRepository;
import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Payment findByOrder(Order order); 
	
	
}
