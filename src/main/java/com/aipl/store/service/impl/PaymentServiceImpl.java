package com.aipl.store.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aipl.store.domain.Payment;
import com.aipl.store.domain.Order;
import com.aipl.store.repository.PaymentRepository;
import com.aipl.store.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository paymentRepository;
	
	public Payment findByOrder(Order order) {
		return paymentRepository.findByOrder(order);
	}

	
}
