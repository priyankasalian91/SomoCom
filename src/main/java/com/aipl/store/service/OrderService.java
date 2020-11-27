package com.aipl.store.service;

import java.util.List;

import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;
import com.aipl.store.domain.Shipping;
import com.aipl.store.domain.ShoppingCart;
import com.aipl.store.domain.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, Shipping shippingAddress, Payment payment, User user);
	
	List<Order> findByUser(User user);
	
	Order findOrderWithDetails(Long id);
	
	List<Order> findAll();
	
	Order updateOrder(Order order);
	
}
