package com.aipl.store.service;


import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;

public interface PaymentService {

	Payment findByOrder(Order order);
	
	
	
}
