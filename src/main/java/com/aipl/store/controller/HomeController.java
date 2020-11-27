package com.aipl.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aipl.store.domain.Article;
import com.aipl.store.service.ArticleService;
import com.aipl.store.service.VisitorService;

@Controller
public class HomeController {
		
	@Autowired
	private ArticleService articleService;
    @Autowired
    VisitorService visitorService;

	@RequestMapping("/")
	public String index(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "index";
	}
	
	@RequestMapping("/aboutus")
	public String aboutus(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "aboutus";
	}
	
	@RequestMapping("/privacy-policy")
	public String privacypolicy(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "privacypolicy";
	}
	
	@RequestMapping("/cookie-policy")
	public String cookiepolicy(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "cookiePolicy";
	}
	
	@RequestMapping("/terms")
	public String termsandconditions(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "terms";
	}
	
	@RequestMapping("/shipping-policy")
	public String shippingpolicy(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "shippingPolicy";
	}
	
	@RequestMapping("/return-policy")
	public String returnpolicy(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "returnPolicy";
	}

	@RequestMapping("/wip")
	public String workinprogress(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "workinprogress";
	}
	
	
	@RequestMapping("/2")
	public String index2(Model model) {		
		List<Article> articles = articleService.findFirstArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "index_old";
	}
		
		@RequestMapping("/1")
		public String index1(Model model) {		
			List<Article> articles = articleService.findFirstArticles();
			model.addAttribute("articles", articles);
			model.addAttribute("allCategories", articleService.getAllCategories());
			return "index1";
	}
	
		@RequestMapping("/ab")
		public String indexab(Model model) {		
			List<Article> articles = articleService.findFirstArticles();
			model.addAttribute("articles", articles);
			model.addAttribute("allCategories", articleService.getAllCategories());
			return "ab";
	}
	
		@RequestMapping("/a")
		public String indexa(Model model) {		
			List<Article> articles = articleService.findFirstArticles();
			model.addAttribute("articles", articles);
			model.addAttribute("allCategories", articleService.getAllCategories());
			return "a";
	}
		
		

		@RequestMapping("/test")
		public String test(Model model) {		
			return "test";
		}
}
