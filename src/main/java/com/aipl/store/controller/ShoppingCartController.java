package com.aipl.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aipl.store.domain.Article;
import com.aipl.store.domain.BulkPurchase;
import com.aipl.store.domain.CartItem;
import com.aipl.store.domain.ShoppingCart;
import com.aipl.store.domain.User;
import com.aipl.store.service.ArticleService;
import com.aipl.store.service.BulkPurchaseService;
import com.aipl.store.service.ShoppingCartService;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {
		
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@RequestMapping("/cart")
	public String shoppingCart(Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);		
		model.addAttribute("cartItemList", shoppingCart.getCartItems());
		model.addAttribute("shoppingCart", shoppingCart);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "shoppingCart";
	}

	@RequestMapping("/add-item")
	public String addItem(@ModelAttribute("article") Article article, @RequestParam("qty") String qty,
						  @RequestParam("size") String size, RedirectAttributes attributes, Model model, Authentication authentication) {
		article = articleService.findArticleById(article.getId());				
		if (!article.hasStock(Integer.parseInt(qty))) {
			attributes.addFlashAttribute("notEnoughStock", true);
			return "redirect:/article-detail?id="+article.getId();
		}		
		User user = (User) authentication.getPrincipal();		
		shoppingCartService.addArticleToShoppingCart(article, user, Integer.parseInt(qty), size);
		attributes.addFlashAttribute("addArticleSuccess", true);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "redirect:/article-detail?id="+article.getId();
	}
	
	
	
	@RequestMapping("/update-item")
	public String updateItemQuantity(@RequestParam("id") Long cartItemId,
									 @RequestParam("qty") Integer qty, Model model) {		
		CartItem cartItem = shoppingCartService.findCartItemById(cartItemId);
		if (cartItem.canUpdateQty(qty)) {
			shoppingCartService.updateCartItem(cartItem, qty);
		}
		return "redirect:/shopping-cart/cart";
	}
	
	@RequestMapping("/remove-item")
	public String removeItem(@RequestParam("id") Long id) {		
		shoppingCartService.removeCartItem(shoppingCartService.findCartItemById(id));		
		return "redirect:/shopping-cart/cart";
	} 
}
