package com.aipl.store.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aipl.store.domain.Article;
import com.aipl.store.domain.CartItem;
import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;
import com.aipl.store.domain.Shipping;
import com.aipl.store.domain.ShoppingCart;
import com.aipl.store.domain.User;
import com.aipl.store.repository.ArticleRepository;
import com.aipl.store.repository.CartItemRepository;
import com.aipl.store.repository.OrderRepository;
import com.aipl.store.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CartItemRepository cartItemRepository;
	
	@Autowired
	ArticleRepository articleRepository;
			
	@Override
	@Transactional
	@CacheEvict(value = "itemcount", allEntries = true)
	public synchronized Order createOrder(ShoppingCart shoppingCart, Shipping shipping, Payment payment, User user) {
		Order order = new Order();
		order.setUser(user);
		order.setPayment(payment);
		order.setShipping(shipping);
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shipping.setOrder(order);
		payment.setOrder(order);			
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate = today.plusDays(5);				
		order.setOrderDate(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setShippingDate(Date.from(estimatedDeliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		order.setOrderStatus("In Progress");
		
		order = orderRepository.save(order);
		
		List<CartItem> cartItems = shoppingCart.getCartItems();
		for (CartItem item : cartItems) {
			Article article = item.getArticle();
			article.decreaseStock(item.getQty());
			articleRepository.save(article);
			item.setOrder(order);
			cartItemRepository.save(item); 	
		}		
		return order;	
	}
	
	@Override
	public Order findOrderWithDetails(Long id) {
		return orderRepository.findEagerById(id);
	}	

	public List<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	/*public Order updateOrder(Order order) {
		return orderRepository.save(order);
	}*/
	
    @Override
    public Order updateOrder(Order order) {
        Optional<Order> orderDb = this.orderRepository.findById(order.getId());
		
       // if (orderDb.isPresent()) {
        	Order orderUpdate = orderDb.get();
        	System.out.println("ID is " + order.getId());
        	orderUpdate.setId(order.getId());
        	orderUpdate.setOrderStatus("Payment Done");
            orderRepository.save(orderUpdate);
            return orderUpdate;
       // } 
       
		
	 }
	
	
}
