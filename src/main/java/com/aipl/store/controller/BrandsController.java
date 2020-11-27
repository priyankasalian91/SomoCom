package com.aipl.store.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BrandsController {
	
	
	@RequestMapping("/brands")
	public String brands(Model model) {		
		return "brand";
	}	
	
	@RequestMapping("/microsites-aipl")
	public String aiplmicrosite(Model model) {		
		return "aiplMicroSite";
	}
		
	@RequestMapping("/microsites-tapri")
	public String taprimicrositeHome(Model model) {		
		return "tapriMicroSite";
	}
	
	@RequestMapping("/microsites-tapri2")
	public String taprimicrositeSSS(Model model) {		
		return "tapriMicroSiteSSS";
	}
	
	@RequestMapping("/microsites-tapri3")
	public String taprimicrositeHP(Model model) {		
		return "tapriMicroSiteHP";
	}
	
	
	

	@RequestMapping("/tapri")
	public String tapri(Model model) {		
		return "tapri";
	}
}
