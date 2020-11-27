package com.aipl.store.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.RefernceIdStore;
import com.aipl.store.domain.Url;
import com.aipl.store.domain.Visitor;
import com.aipl.store.dto.InviteDetailsDTO;
import com.aipl.store.dto.Link;
import com.aipl.store.dto.LinkValidation;
import com.aipl.store.dto.LinkValidationStatus;
import com.aipl.store.exception.LinkNotFoundException;
import com.aipl.store.repository.LinkRepository;
import com.aipl.store.service.AffiliateUserService;
import com.aipl.store.service.InviteDetailsService;
import com.aipl.store.service.VisitorService;

import utility.Couple;
import utility.RandomCharacter;


@Controller
public class AffilateController {

	private final static String newLinkNotProceed = "{\"msg\":\"link registration is not proceed cause of not validation\"}";

    @Autowired
    LinkRepository linkRepository;
    
    @Autowired
    AffiliateUserService affiService;
    
    @Autowired
   private InviteDetailsService inviteDetailsService;
    
    @Autowired
    VisitorService visitorService;
    


    @PostMapping(value = "/v/add", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String addNewUrl(@Valid @RequestBody Link link, BindingResult bindingResult) {
        System.out.println("binding Result:" + bindingResult.hasErrors() + "|" + (bindingResult.hasErrors() ? bindingResult.getAllErrors().get(0).toString() : "no error detected!"));
        System.out.println("link in add:" + link);
        if (bindingResult.hasErrors() || link.getCustomLink() == null) {
            return newLinkNotProceed;
        } else {
            Url url = new Url();
            String[] affiId = link.getCustomLink().split("_");
            String[] refId = affiId[0].toString().split("=");
            url.setDate(System.currentTimeMillis());
            url.setLink(link.getCustomLink());
            url.setUrl(link.getUrl());
            url.setAffilateUser(refId[1].toString());
            url = linkRepository.saveAndFlush(url);
            if (url != null) {
                return "{\"code\":1,\"msg\":\"url is registered\",\"shortLink\":\""+url.getLink()+"\"}";
            }
        }
        return newLinkNotProceed;
    }

    @PostMapping(value = "/v/new", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String checkNewUrl(@Valid @RequestBody Link link, BindingResult bindingResult) throws IOException {
        System.out.println("binding Result:" + bindingResult.hasErrors() + "|" + (bindingResult.hasErrors() ? bindingResult.getAllErrors().get(0).toString() : "no error detected!"));
        System.out.println("link:" + link);
        if (bindingResult.hasErrors()) {
            return newLinkNotProceed;
        } else {
            LinkValidation linkValidation = new LinkValidation();
            linkValidation.setUrl(link.getUrl());
            linkValidation.setShortLink(link.getCustomLink());

            boolean isNeedNewShortLink = false;
            if (link.getCustomLink() != null) {
                Integer urlId = linkRepository.getUrlIdByLink(link.getCustomLink());
                if (urlId == null) {
                    linkValidation.setLinkValidationStatus(LinkValidationStatus.FREE);
                } else {
                    linkValidation.setLinkValidationStatus(LinkValidationStatus.EXIST_AND_CHOOSE_ANOTHER);
                    isNeedNewShortLink = true;
                }
            }

            if (link.getCustomLink() == null || isNeedNewShortLink) {
                boolean isShortLinkFree = false;
                String shortLink;
                do {
                    shortLink = RandomCharacter.getRandomString(5);
                    isShortLinkFree = linkRepository.getUrlIdByLink(shortLink) == null;
                    System.out.println("new Short Link : " + shortLink);
                } while (!isShortLinkFree);
                linkValidation.setShortLink(shortLink);
                linkValidation.setLinkValidationStatus(
                        isNeedNewShortLink ?
                                LinkValidationStatus.EXIST_AND_NEW :
                                LinkValidationStatus.NEW);
            }
            System.out.println("linkValidation.toJson : " + linkValidation.toJson());
            return linkValidation.toJson();
        }
    }

    @GetMapping("/af/{link}")
    public String redirectLinkToUrl(@PathVariable String link, HttpServletRequest request) throws Throwable {
        System.out.println("### redirectLinkToUrl : " + link);
        String urlAddressByLink = null;
        if(link.startsWith("ref")) {
	        urlAddressByLink = linkRepository.getUrlAddressByLink(link);
	        System.out.println("Original URL is : "+ urlAddressByLink);
	        String[] prodId = urlAddressByLink.split("=");
	        System.out.println("Product Id is :"+ prodId[1].toString());
	        String[] finalStr = link.split("_");
	        String[] refId = finalStr[0].toString().split("=");
	        
	        System.out.println(Integer.parseInt(refId[1].toString()));
	        
	        //Setting reference Id for Affiliate User
	        RefernceIdStore rf = new RefernceIdStore();
	        rf.setRefId(Integer.parseInt(refId[1].toString()));
	        rf.setProdId(prodId[1].toString());
	        
	        AccountController acc = new AccountController();
	        acc.CheckVal(rf);
	        CheckoutControler cc = new CheckoutControler();
	        cc.CheckVal(rf);
	        
	        //new logic start
	          int refAid = Integer.parseInt(refId[1].toString());
	    	  InviteDetailsDTO inv = new InviteDetailsDTO();
	    	  inv.setRefAid((long) Integer.valueOf(refAid));
	    	  inv.setProdId(prodId[1].toString());
	    	 // SomodeskIn sm = new SomodeskIn();
	    	  //sm.sendvalue(inv);
	        
	        Long id = (long) Integer.parseInt(refId[1].toString());
	        
	        Optional<AffiliateUser> val = affiService.findById(id);      
	        if (val.isPresent()) {
	        	AffiliateUser existingAffiUser = val.get();
	            //System.out.println(val.get());
	            String refAffiUser = existingAffiUser.getUsername();
	            String refAffiUserEmail = existingAffiUser.getEmail();
	            System.out.println("Affilate Short Link Created By : "+ refAffiUser);
	            System.out.println("Affilate User email :" +refAffiUserEmail);
	        } else {
	            System.out.printf("No Affilieate User found with id %d%n", id);
	        }
	
	        
	        if (urlAddressByLink != null && !urlAddressByLink.isEmpty()) {
	        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	        	Date date = new Date(System.currentTimeMillis());
	        	System.out.println(formatter.format(date));
	        	Visitor visitor = new Visitor();
	            visitor.setDate(formatter.format(date));
	            visitor.setIp(request.getRemoteAddr());
	            visitorService.insertVisitor(link, visitor);
	            Couple<Url, Integer> linkStatistics = visitorService.getLinkStatistics(link);
	            if(linkStatistics != null && linkStatistics.isCoupleNotNull()) {
	            	System.out.println("Url is : "+ linkStatistics.getFirst().getUrl() );
	            	System.out.println("Visitor total Clicked : "+ linkStatistics.getSecond());
	            }
	        }else {
	        	
	            throw new LinkNotFoundException("link not found!");
	        }
      }else {
    	  	System.out.println("URL is not valid..Please check URL Creation");
    	  	//return "redirect:http://localhost:9991/register";
    	  
      }
        return "redirect:" + urlAddressByLink;
       
    }
  
}

