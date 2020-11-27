package com.aipl.store.controller;

import java.util.*;

import javax.websocket.server.PathParam;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aipl.store.common.ReadSettingsFile;
import com.aipl.store.domain.Article;
import com.aipl.store.domain.Brand;
import com.aipl.store.domain.Category;
import com.aipl.store.form.ArticleFilterForm;
import com.aipl.store.service.ArticleService;
import com.aipl.store.type.SortFilter;

@Controller
public class StoreController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/store")
	public String store(@ModelAttribute("filters") ArticleFilterForm filters, Model model) {
		
		Integer page = filters.getPage();			
		int pagenumber = (page == null ||  page <= 0) ? 0 : page-1;
		SortFilter sortFilter = new SortFilter(filters.getSort());	
		Page<Article> pageresult = articleService.findArticlesByCriteria(PageRequest.of(pagenumber,9, sortFilter.getSortType()), 
																filters.getPricelow(), filters.getPricehigh(), 
																filters.getSize(), filters.getCategory(), filters.getBrand(), filters.getSearch());	
		if(pageresult.getTotalElements() == 0)
		{
			model.addAttribute("productNotFound", true);
		}
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("allBrands", articleService.getAllBrands());
		model.addAttribute("allSizes", articleService.getAllSizes());
		model.addAttribute("articles", pageresult.getContent());
		model.addAttribute("totalitems", pageresult.getTotalElements());
		model.addAttribute("itemsperpage", 9);
		 model.addAttribute("createLink", "actual");
		return "store";
	}
	
	@RequestMapping("/store/{aid}")
	public String storeLink(@ModelAttribute("filters") ArticleFilterForm filters, Model model, @PathVariable("aid")String aid) {
		System.out.println("Affiliate Id is: "+aid);
		String[] aid1 = aid.split("=");
        model.addAttribute("aid", aid1[1].toString());
        model.addAttribute("createLink", "link");
        
		Integer page = filters.getPage();			
		int pagenumber = (page == null ||  page <= 0) ? 0 : page-1;
		SortFilter sortFilter = new SortFilter(filters.getSort());	
		Page<Article> pageresult = articleService.findArticlesByCriteria(PageRequest.of(pagenumber,9, sortFilter.getSortType()), 
																filters.getPricelow(), filters.getPricehigh(), 
																filters.getSize(), filters.getCategory(), filters.getBrand(), filters.getSearch());	
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("allBrands", articleService.getAllBrands());
		model.addAttribute("allSizes", articleService.getAllSizes());
		model.addAttribute("articles", pageresult.getContent());
		model.addAttribute("totalitems", pageresult.getTotalElements());
		model.addAttribute("itemsperpage", 9);
		return "store";
	}
	
	
	
	@RequestMapping("/articleList/{productCategory}")
	public String articleList(@ModelAttribute("filters") ArticleFilterForm filters, Model model, @PathVariable("productCategory")String productCategory) {
		System.out.println(productCategory);
		
//		String productIs = "";
//        if (productCategory.contains("_")){
//            System.out.println(true);
//            String[] checkUrl = productCategory.split("_");
//            System.out.println("Link = "+ checkUrl[1].toString());
//            String[] refId = checkUrl[1].split("=");
//            System.out.println("Link = "+ refId[1].toString());
//            productIs=checkUrl[0].toString();
//            model.addAttribute("createLink", refId[0].toString());
//            model.addAttribute("refId", refId[1].toString());
//
//        }else {
//        	productIs=productCategory;
//        	System.out.println("in else");
//            model.addAttribute("createLink", "test");
//
//        }
		
		
		Integer page = filters.getPage();			
		int pagenumber = (page == null ||  page <= 0) ? 0 : page-1;
		SortFilter sortFilter = new SortFilter(filters.getSort());	
		
		Integer priceLow = null;
		Integer priceHigh = null; 
		List<String> sizes = null;
		List<String> brands = null;
		String search = null;	
		List<String> catList = new ArrayList<String>();
		catList.add(productCategory);
		
		Page<Article> pageresult = articleService.findArticlesByCriteria(PageRequest.of(pagenumber,9, sortFilter.getSortType()), 
				priceLow , priceHigh, sizes, catList , brands, search);		
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("allBrands", articleService.getAllBrands());
		model.addAttribute("allSizes", articleService.getAllSizes());
		model.addAttribute("articles", pageresult.getContent());
		model.addAttribute("totalitems", pageresult.getTotalElements());
		model.addAttribute("itemsperpage", 9);
		return "store";
	}
	
	
	@Transactional
	@RequestMapping("/article-detail")
	public String articleDetail(@PathParam("id") Long id, Model model) {
		Article article = articleService.findArticleById(id);
	    int stockthreshold = Integer.parseInt(new ReadSettingsFile().getValueFromParam("stockthreshold").toString());
	    Boolean stockshow = (article.getStock() <= stockthreshold) ? true : false;
	    double savedAmount = findSaveAmount(article);
	    Boolean showDiscount = (savedAmount == 0) ? false : true;
	    String preselectedBrands = "";
	  		for (Brand brand : article.getBrands()) {
	  			preselectedBrands = (brand.getName());
	  		}
	  	model.addAttribute("preselectedBrands", preselectedBrands);
	    model.addAttribute("showDiscount", showDiscount);
	    model.addAttribute("savedAmount", savedAmount);
	    model.addAttribute("stockshow", stockshow);
	    model.addAttribute("article", article);
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("notEnoughStock", model.asMap().get("notEnoughStock"));
		model.addAttribute("addArticleSuccess", model.asMap().get("addArticleSuccess"));
		return "articleDetail";
	}
	
	@Transactional
	@RequestMapping("/article-detail-aff")
	public String articleDetailAff(@PathParam("id") String id, Model model) {
		System.out.println("####Affiliate Id is: "+id);
		String[] checkId = id.split("_");
		System.out.println("id is "+ checkId[0]);
		String[] checkRefId =  checkId[1].split("=");
        model.addAttribute("aid", checkRefId[1]);
		Article article = articleService.findArticleById((long) Integer.valueOf(checkId[0]));
		int stockthreshold = Integer.parseInt(new ReadSettingsFile().getValueFromParam("stockthreshold").toString());
	    Boolean stockshow = (article.getStock() <= stockthreshold) ? true : false;
	    double savedAmount = findSaveAmount(article);
	    Boolean showDiscount = (savedAmount == 0) ? false : true;
	    String preselectedBrands = "";
		for (Brand brand : article.getBrands()) {
			preselectedBrands = (brand.getName());
		}
		model.addAttribute("preselectedBrands", preselectedBrands);
	    model.addAttribute("showDiscount", showDiscount);
	    model.addAttribute("savedAmount", savedAmount);
	    model.addAttribute("stockshow", stockshow);
		model.addAttribute("article", article);
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("notEnoughStock", model.asMap().get("notEnoughStock"));
		model.addAttribute("addArticleSuccess", model.asMap().get("addArticleSuccess"));
		model.addAttribute("createLink", "link");
		return "articleDetail";
	}
	
	public double findSaveAmount(Article article)
	{
		double savedAmt = 0;
		if(article.getMrpPrice() != 0.0 || article.getMrpPrice() != 0)
		{
			savedAmt = article.getMrpPrice() - article.getPrice();
		}
		
		return savedAmt;
	}

}
