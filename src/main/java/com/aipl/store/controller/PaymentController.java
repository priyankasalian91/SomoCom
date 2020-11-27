package com.aipl.store.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;
import com.aipl.store.service.OrderService;
import com.aipl.store.service.PaymentService;
import com.aipl.store.service.ShoppingCartService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping("/show-orders")
	public String showOrder(Model model) {		
		List<Order> orderList = orderService.findAll();		
		model.addAttribute("orders", orderList);
		return "payment";
	}

	
	@RequestMapping("/update")
	public String updatePayment(@RequestParam("id") Long id, Model model) {	
		Order order = orderService.findOrderWithDetails(id);
		model.addAttribute("order", order);
		// fetch id of 
		Payment payment = paymentService.findByOrder(order);
		System.out.println("Payee name " + payment.getDraweename());
		//Payment payment = new Payment();
		//payment.setDraweename(" ");
		//payment.setChequeNo(" ");
		//payment.setDraweeifsc(" ");
		//payment.setDraweebank(" ");
		model.addAttribute("payment", payment);
		return "updatePayment";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updatePaymentPost(@ModelAttribute("order") Order order, HttpServletRequest request) {	
		System.out.println("Order ID " + order.getId());
		//Order norder = orderService.editOrder(order, order.getPayment());
		
		/* Order neworder = new Order();
		 neworder.setId(order.getId());
		 neworder.setOrderStatus("Payment Done");
		 orderService.updateOrder(neworder);*/	
		
		 orderService.updateOrder(order);
		return "redirect:show-orders";
	}
	
	
	/*@RequestMapping(value = "/abc", method = RequestMethod.POST)
	public String placeOrder(@ModelAttribute("shipping") Shipping shipping,
							@ModelAttribute("address") Address address,
							@ModelAttribute("payment") Payment payment,
							RedirectAttributes redirectAttributes, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);	
		if (!shoppingCart.isEmpty()) {
			shipping.setAddress(address);
			Order order = orderService.createOrder(shoppingCart, shipping, payment, user);		
			redirectAttributes.addFlashAttribute("order", order);
		}
		return "redirect:/order-submitted";
	}*/
	
	
}
