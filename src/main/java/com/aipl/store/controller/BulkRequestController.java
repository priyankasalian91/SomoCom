package com.aipl.store.controller;

import java.sql.Timestamp;
import java.util.*;

import javax.websocket.server.PathParam;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aipl.store.common.ReadSettingsFile;
import com.aipl.store.domain.Article;
import com.aipl.store.domain.Brand;
import com.aipl.store.domain.BulkPurchase;
import com.aipl.store.domain.Category;
import com.aipl.store.domain.User;
import com.aipl.store.form.ArticleFilterForm;
import com.aipl.store.service.ArticleService;
import com.aipl.store.service.BulkPurchaseService;
import com.aipl.store.service.ShoppingCartService;
import com.aipl.store.type.SortFilter;
import com.aipl.store.utility.MailConstructor;

@Controller
public class BulkRequestController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private BulkPurchaseService bulkPurchaseService;
	
	@Autowired
    private MailConstructor mailConstructor;
	
    @Autowired
    private JavaMailSender mailSender;
	
	@RequestMapping("/bulk-purchase")
	public String bulkPurchase(@ModelAttribute("article") Article article, @ModelAttribute("bulkpurchase") BulkPurchase bulkrequest ,  RedirectAttributes attributes, Model model, Authentication authentication) {
		article = articleService.findArticleById(article.getId());		
		Timestamp requestedon = new Timestamp(System.currentTimeMillis());
		System.out.println(article.getId() + " " + article.getDescription() + " " + article.getTitle() );
		BulkPurchase bulkpurchase = new BulkPurchase();
		bulkpurchase.setArticle(article);
		bulkpurchase.setBulkquantity(bulkrequest.getBulkquantity());
		bulkpurchase.setMailsent(true);
		bulkpurchase.setEmail(bulkrequest.getEmail());
		bulkpurchase.setPhone(bulkrequest.getPhone());
		bulkpurchase.setFullname(bulkrequest.getFullname());
		bulkpurchase.setRequestedon(requestedon);
		
		// add to shopping cart
		// shoppingCartService.addArticleToShoppingCart(article, user, Integer.parseInt(bulkrequest.bulkquantity), size);
		// also add in the BulkPurchase Request table 
		//bulkPurchaseService.saveBulkPurchase(bulkrequest.getBulkquantity(), bulkrequest.getEmail() , article, userid, false);
		bulkPurchaseService.save(bulkpurchase);
		
		// send a mail 		
		String sendTo = new ReadSettingsFile().getValueFromParam("bulkrequestto").toString();
		String subject = "Somodesk.com - Bulk Purchase Negotiation Request for " + article.getTitle();
		String bodyMessage = bodyMessage(bulkrequest , article);
		SimpleMailMessage newEmail = mailConstructor.sendEmail(sendTo, subject, bodyMessage);
	    mailSender.send(newEmail);
	    
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "redirect:/article-detail?id="+article.getId();
	}
	
	public String bodyMessage(BulkPurchase bulkrequest, Article article) {
    	String customMsg ="\nDear Sir/Madam,\n\n" + bulkrequest.getFullname() + " has requested for negotiating for " + bulkrequest.getBulkquantity() + " quantity of " +
	                      article.getTitle() + ".\nKindly get in touch with the requester on the below mentioned contact details - \n\n" +
    			          "Email ID   : " + bulkrequest.getEmail() +  "\n" +
    	                  "Contact no : " + bulkrequest.getPhone() + "\n\n";
    	String thankMsg = "\nThanks,\nSomodesk Customer Service";   	
    	String str = customMsg + thankMsg;
    	return str;
    }
	
	
}
