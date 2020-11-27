package com.aipl.store.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.aipl.store.domain.AffiliateRegisterFees;
import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.Order;
import com.aipl.store.domain.SomodeskInviteUser;
import com.aipl.store.domain.SomodeskProductLinkDetails;
import com.aipl.store.domain.User;
import com.aipl.store.dto.AffiliateUserRegistrationDto;
import com.aipl.store.dto.InviteDetailsDTO;
import com.aipl.store.dto.SendMailDTO;
import com.aipl.store.repository.AffiliateUserRepository;
import com.aipl.store.service.AffiliateUserService;
import com.aipl.store.service.ArticleService;
import com.aipl.store.service.OrderService;
import com.aipl.store.service.RegistrationFeesService;
import com.aipl.store.service.SomodeskTest1Service;
import com.aipl.store.service.SomodeskTest2Service;
import com.aipl.store.service.UserService;
import com.aipl.store.service.VisitorService;
import com.aipl.store.service.impl.UserSecurityService;
import com.aipl.store.utility.MailConstructor;


@Controller
@RequestMapping("somodesk.in")
public class SomodeskIn {

	public static Long refAffiUserID = null;
	public static String prodID = null;
	public static String subject = "Somodesk - Registered Through your Product Refeeral Link";
	public static String subject1 = "Somodesk - Registered Through your Invite Link";
	public static String inviteBodyMessage = "\nDear Sir/Madam,\n\nSomeomne used your Invite Code to register himself.\nYou will get commission when he will buy any products.\n\n\nThanks,\nSomodesk Customer Service\"; \r\n" + 
			"";

  
    @Autowired
    VisitorService visitorService;
    @Autowired
    private AffiliateUserRepository affiliateUserRepository;
    @Autowired
    private AffiliateUserService affiliateUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSecurityService userSecurityService;
	@Autowired
	private ArticleService articleService;
    @Autowired
    private MailConstructor mailConstructor;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    AffiliateUserService affiService;
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SomodeskTest1Service somodeskTest1Service;
	
	@Autowired
	private SomodeskTest2Service somodeskTest2Service;
	
	@Autowired
	private RegistrationFeesService registrationFeesService;

    

//	//Post method
//	@PostMapping("/register")
//    public String registerUserAccount(@ModelAttribute("affiliateuser") @Valid AffiliateUserRegistrationDto userDto,
//    								RedirectAttributes redirectAttributes,BindingResult result){
//		boolean invalidFields = false;
//        if (result.hasErrors()){
//        	return "redirect:/register";
//        }
//        if (affiliateUserService.findByEmail(userDto.getEmail()) != null){
//            result.rejectValue("email", null, "There is already an account registered with that email");
//            invalidFields = true;
//        }
//
//        if(affiliateUserService.findByUsername(userDto.getUsername())!= null) {
//			redirectAttributes.addFlashAttribute("usernameExists", true);
//			invalidFields = true;
//        }
//		if (invalidFields) {
//			return "redirect:/register";
//		}
//		
//		
//		Random rand = new Random(); //instance of random class
//	    int upperbound = 999999;
//		int int_random = rand.nextInt(upperbound); 
//		//String  inviteCode="INVSOMO-"+  String.valueOf(int_random);
//		char ch1 = Character.toUpperCase(userDto.getFirstName().charAt(0));
//		char ch2 = Character.toUpperCase(userDto.getLastName().charAt(0));
//		String Str=new StringBuilder().append(ch1).append(ch2).toString();
//		String  inviteCode = Str + userDto.getPincode() + String.valueOf(int_random);
//		System.out.println("Invition code : "+ inviteCode);
//		
//		AffiliateUser affiliateUser = affiliateUserService.save(userDto);
//		Long affiliateID = affiliateUser.getId();
//		User user = userService.createUser(userDto.getUsername(),userDto.getPassword(),  userDto.getEmail(), Arrays.asList("ROLE_USER"),userDto.getPincode() );
//		Long userId = user.getId();
//		//payment Logic come
////        if (registrationFeesService.findByEmail(userDto.getEmail()) != null){
////        	AffiliateRegisterFees affiliateRegisterFees = registrationFeesService.findByEmail(userDto.getEmail());
////        	if(affiliateRegisterFees.getUserID() == null && affiliateRegisterFees.getAffiliateID() == null) {
////        		affiliateRegisterFees.setAffiliateID(affiliateID);
////        		affiliateRegisterFees.setUserID(userId);
////        		registrationFeesService.save(affiliateRegisterFees);
////        	}
////        }
//		userSecurityService.authenticateUser(user.getEmail());
//		Long newAffiliateID = null;
//		
//		if(affiliateUser.getInviteCode() == null) {
//			affiliateUser.setInviteCode(inviteCode);
//			affiliateUserService.save(affiliateUser);
//			newAffiliateID = affiliateUser.getId();
//		}
//		
////			Logic for user register through invite code
//		if(refAffiUserID != null  && prodID != null) {
//			System.out.println("@@@@@@ Condition 1 for New User register through product link #########");
//        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//        	Date date = new Date(System.currentTimeMillis());
//			String registerDate = formatter.format(date);
//			SomodeskProductLinkDetails details = new SomodeskProductLinkDetails();
//			details.setUserId(userId);
//			details.setAffiliateId(newAffiliateID);
//			details.setProdId(prodID);
//			details.setRefAid(refAffiUserID);
//			details.setDate(registerDate);
//			somodeskTest1Service.save(details);
//			
//			String bodyMessage = bodyMessage();
//			Optional<AffiliateUser> val = affiService.findById(refAffiUserID);
//			if (val.isPresent()) {
//	        	AffiliateUser existingAffiUser = val.get();
//	            String refAffiUserEmail = existingAffiUser.getEmail();
//	            System.out.println("##### Referral Affilate User email id is :" + refAffiUserEmail);
//	            try {
//					SimpleMailMessage newEmail = mailConstructor.sendEmail(refAffiUserEmail,subject, bodyMessage);
//				    mailSender.send(newEmail);
//				    //one time done so Session should null
//				    refAffiUserID = null;
//				    prodID = null;
//				    	         	
//	            }catch(Exception e) {
//	            	System.out.println("Exception Occured "+ e);
//	            	throw e;
//	            }
//
//			}
//			return "redirect:/shopping-cart/add-item";
//
//		}else if(refAffiUserID != null && prodID == null) {
//			System.out.println("@@@@@@ Condition 3 -> New User register through Invite Code #########");
//        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//        	Date date = new Date(System.currentTimeMillis());
//			String registerDate = formatter.format(date);
//
//        	SomodeskInviteUser details = new SomodeskInviteUser();
//        	details.setAffiliateId(newAffiliateID);
//        	details.setUserId(userId);
//        	details.setDate(registerDate);
//        	details.setInviteAid(refAffiUserID);
//        	somodeskTest2Service.save(details);
//        	
//			Optional<AffiliateUser> val = affiService.findById(refAffiUserID);
//			if (val.isPresent()) {
//	        	AffiliateUser existingAffiUser = val.get();
//	            String refAffiUserEmail = existingAffiUser.getEmail();
//	            System.out.println("##### Invite code Affilate User email id is :" + refAffiUserEmail);
//	            try {
//					SimpleMailMessage newEmail = mailConstructor.sendEmail(refAffiUserEmail,subject1, inviteBodyMessage);
//				    mailSender.send(newEmail);
//				    //one time done so Session should null
//				    refAffiUserID = null;				    	         	
//	            }catch(Exception e) {
//	            	System.out.println("Exception Occured "+ e);
//	            	throw e;
//	            }
//			}
//	        return "redirect:/home";
//		}else {
//			System.out.println("@@@@@@ Direct User ###########");
//	        return "redirect:/home";
//
//		}
//		
//
//       
//    }
//	//_________________________Register finished______________________________________//
//	
//	//Affiliate Dashboard
//	@RequestMapping("/home")
//    public String affiliateHomePage(Model model) {
//    	try {
//    		String username = getLoggedInUser();
//    		int userId = visitorService.getAffiliateUserID(username);
//    	    System.out.println("Affiliate User Id is: "+ userId);
//    	    int count= visitorService.getUrlCount(userId);
//    	    System.out.println("Affiliate Link counts : "+ count);	    
//    	    List<Map<String, Object>> featureServices = visitorService.getList(userId);
//    	    System.out.println("List data is : "+featureServices);
//    	    int totalCount = 0;
//    	    for (Map<String, Object> featureService : featureServices) {
//    	        for (Map.Entry<String, Object> entry : featureService.entrySet()) {
//    	           System.out.println(entry.getKey() + ": " + entry.getValue());
//    	           if("urlid".equals(entry.getKey())) {
//    	        	   int totClick = visitorService.getTotalClick((int) entry.getValue());
//    	        	   totalCount += totClick;
//    	         	   System.out.println("Total Click Count is : "+ totalCount );
//
//    	           }
//    	           //totalCount +=entry.getValue()
//    	        }
//    	    }
//
//    	    model.addAttribute("affClick", totalCount);    
//    		model.addAttribute("affCount", count);
//    		model.addAttribute("linkDeta", featureServices);
//    		model.addAttribute("username", username);
//            return "affi_dashboard";
//    		
//    	}catch(Exception e) {
//    		System.out.println("Query execution failed" + e);
//    		return "redirect:/somodesk.in/register";
//    	}
//				
//    }
//	//_____________________Dashboard finished______________________________________//
//	
//    
//    @RequestMapping("/commissions")
//    public String afCommission(Model model) {
//    	String username = getLoggedInUser();
//    	int affiliateId = visitorService.getAffiliateUserID(username);
//    	List<Map<String, Object>> getUserIDSMT1 = visitorService.getUserListIDSMT1(affiliateId);    	
//    	List<Object> newList = new ArrayList<>();
//    	List<Object> combined = new ArrayList<Object>();
//    	for(Map<String, Object> s : getUserIDSMT1) {
//    		newList.add(s.get("user_id"));
//    	}
//    	List<Map<String, Object>> getUserIDSMT2 = visitorService.getUserListIDSMT2(affiliateId);
//    	for(Map<String, Object> s : getUserIDSMT2) {
//    		newList.add(s.get("user_id"));
//    	}
//    	for(int i=0; i < newList.size(); i++) {
//    		Long id = (Long)newList.get(i);
//    		List<Map<String, Object>> newSaleList = visitorService.commissionSalesReport(id);
//    		combined.addAll(newSaleList);
//    	} 
//    	System.out.println("sale report is : "+combined);	
// 	    model.addAttribute("salesReport", combined);
//    	model.addAttribute("username", username);   	
//        return "affi_commissions";
//    }
//    
//    
//    @RequestMapping("/payment")
//    public String afPayment(Model model) {
//    	String username = getLoggedInUser();
//    	model.addAttribute("username", username);
//        return "affi_payment";
//    }
//    
//    @RequestMapping("/invite")
//    public String afInvite(Model model) {
//    	String username = getLoggedInUser();
//    	model.addAttribute("username", username);
//        return "affi_invite";
//    }
//    
//    @RequestMapping("/profile")
//    public String afProfile(Model model) {
//    	String username = getLoggedInUser();
//    	model.addAttribute("username", username);
//        return "affi_profile";
//    }
//    
//    @RequestMapping("/network")
//    public String afNetwork(Model model) {
//    	String username = getLoggedInUser();
//    	int affiliateId = visitorService.getAffiliateUserID(username);
//    	
//    	//List<Map<String, Object>> listData = visitorService.fetchListData(affiliateId);
//    	List<Map<String, Object>> listDataSMT1 = visitorService.listST1(affiliateId);
//    	List<Map<String, Object>> listDataSMT2 = visitorService.listST2(affiliateId);
//    	//Adding into one
//    	List<Object> combined = new ArrayList<Object>();
//    	combined.addAll(listDataSMT1);
//    	combined.addAll(listDataSMT2);
//    	
//    	System.out.println("My network people : "+combined.toString());
// 	    model.addAttribute("mynetwork", combined);
//    	model.addAttribute("username", username);
//        return "affi_Network";
//    }
//    @RequestMapping("/order")
//    public String afOrder(Model model, Authentication authentication) {
//    	String username = getLoggedInUser();
//    	model.addAttribute("username", username);
//		User user = (User) authentication.getPrincipal();
//		model.addAttribute("user", user);
//		List<Order> orders = orderService.findByUser(user);
//		model.addAttribute("orders", orders);
//        return "affi_orders";
//    }
//    
//    @RequestMapping("/createLink")
//    public String afGEnerateLink(Model model) {
//		
//		String username = getLoggedInUser();
//	    int userId = visitorService.getAffiliateUserID(username);
//	    String inviteCod = visitorService.getInviteCode(username);;
//	    List<Map<String, Object>> featureServices = visitorService.getList(userId);
//		model.addAttribute("affId", userId);
//		model.addAttribute("allCategories", articleService.getAllCategories());
//		model.addAttribute("linkDeta", featureServices);
//		model.addAttribute("username", username);
//		model.addAttribute("inviteCod", inviteCod);
//		model.addAttribute("sendMailDto", new SendMailDTO());
//        return "affi_generateLink";
//    }
//    
//	@RequestMapping("/af/affi_login")
//    public String affiLoginCheck(Model model) {
//		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//		    String currentUserName = authentication.getName();
//		    System.out.println("#####Current Logged in User is : "+ currentUserName);
//	        AffiliateUser affiUser = affiliateUserRepository.findByUsername(currentUserName);
//	        if (affiUser == null) {
//	        	return "redirect:/somodesk.in/register";
//	        }else {
//	        	return "redirect:/somodesk.in/home";
//	        }
//		    
//		}else {
//			return "redirect:/somodesk.in/login";
//		}
//		//User user = (User) authentication.getPrincipal();
//    }
//	
//    @PostMapping("/sendEmail")
//	public String sendMail(@ModelAttribute("SendMailDTO") SendMailDTO sendMail) {
//    	String toEmail =sendMail.getEmail();
//    	String subject = sendMail.getSubject();
//    	String bodyMessage= sendMail.getbodyMSg();
//		SimpleMailMessage newEmail = mailConstructor.sendEmail(toEmail,subject, bodyMessage);
//	    mailSender.send(newEmail);
//	    
//		return "redirect://createLink";
//	}
//    
//    @PostMapping(value ="/getProducts")
//    public @ResponseBody String  getListPRoducts(@RequestParam(value = "val", required = true) int val) {
//    	String jsonData = null;
//    	System.out.println("Affiliate id is : "+ val);
//    	List<Map<String, Object>> listProductsId = visitorService.getListProductId(val);
//    	List<Object> newList = new ArrayList<>();
//    	List<Object> combined = new ArrayList<>();
//    	for(Map<String, Object> s : listProductsId) { newList.add(s.get("prod_id")); }
//    	System.out.println("All Product ID : "+ newList.toString());
//    	List<Map<String, Object>> prodName = null;
//    	for(int i=0; i < newList.size(); i++) {
//    		String multiple = (String)newList.get(i);
//    		if(multiple.contains(",")) {
//    	    	String[] id = multiple.split(",");
//    	    	for(String iu : id) {
//            		prodName = visitorService.getListProductList(Integer.valueOf(iu));
//            		combined.addAll(prodName);
//    	    	}
//    		}else {
//        		 prodName = visitorService.getListProductList(Integer.valueOf(multiple));
//         		 combined.addAll(prodName);
//    		}
//    	}
//    	System.out.println("All Product Name : "+ combined.toString());
//    	jsonData = new Gson().toJson(combined);
//    	return jsonData;
//    }
//    
//    
//    
	//Logged in User method
//	public String getLoggedInUser() {
//		String loggedInUser = null;
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//			loggedInUser = authentication.getName();
//		    System.out.println("Current Logged In user is : "+ loggedInUser);
//		  }
//		
//		return loggedInUser;
//		
//	}
//	public void sendvalue(InviteDetailsDTO inv) {
//		 refAffiUserID = inv.getRefAid();
//		 prodID = inv.getProdId();
//		
//	}
////	
//    public String bodyMessage() {
//    	String customMsg ="\nDear Sir/Madam,\n\nSomeomne used your Product Referral Link to register himself.\nYou will get commission when he will buy that product or any other products.\n\n";
//    	String thankMsg = "\nThanks,\nSomodesk Customer Service";   	
//    	String str = customMsg + thankMsg;
//    	return str;
//    }
}
