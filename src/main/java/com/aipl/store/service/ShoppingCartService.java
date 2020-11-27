package com.aipl.store.service;

import com.aipl.store.domain.Article;
import com.aipl.store.domain.CartItem;
import com.aipl.store.domain.ShoppingCart;
import com.aipl.store.domain.User;


public interface ShoppingCartService {

	ShoppingCart getShoppingCart(User user);
	
	int getItemsNumber(User user);
	
	CartItem findCartItemById(Long cartItemId);
	
	CartItem addArticleToShoppingCart(Article article, User user, int qty, String size);
		
	void clearShoppingCart(User user);
	
	void updateCartItem(CartItem cartItem, Integer qty);

	void removeCartItem(CartItem cartItem);
	
}
