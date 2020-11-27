package com.aipl.store.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aipl.store.domain.Address;
import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.Order;
import com.aipl.store.domain.RefernceIdStore;
import com.aipl.store.domain.SomodeskProductLinkDetails;
import com.aipl.store.domain.User;
import com.aipl.store.dto.AffiliateUserRegistrationDto;
import com.aipl.store.service.AffiliateUserService;
import com.aipl.store.service.ArticleService;
import com.aipl.store.service.OrderService;
import com.aipl.store.service.SomodeskTest1Service;
import com.aipl.store.service.UserService;
import com.aipl.store.service.VisitorService;
import com.aipl.store.service.impl.UserSecurityService;
import com.aipl.store.utility.MailConstructor;

import utility.SecurityUtility;
import com.aipl.store.common.*;

@Controller
public class AccountController {
	
	public static String subject = "SomodeskCOM - Registered Through Product Refeeral Link";
	public static String refValue = null;
	public static String prodID  = null;
	String welcomSubject = "Welcome to Somodesk!";
	String welcomeBodyMessage = "\nDear Sir/Madam,\n\nWe're so happy you decided to join us.Here at Somodesk,we think of ourselved and our customer as on big family.And that means helping you get the products you deserve."
								+ "\n\n Somodesk is an Affiliate Network where you will be able to buy as well as sell interesting products and much more. You can promote it among your family & friends and earn when they are on Somodesk.\n\n ";
    String welcomeThankMsg = "Thanks,\nSomodesk Customer Service";

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private OrderService orderService;
	
    @Autowired
    VisitorService visitorService;
    
	@Autowired
	private ArticleService articleService;
    
	
	@Autowired
    private MailConstructor mailConstructor;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    AffiliateUserService affiService;
	@Autowired
	private SomodeskTest1Service somodeskTest1Service;
    @Autowired
    private AffiliateUserService affiliateUserService;
    
    
	
	@RequestMapping("/ecom_login")
	public String login1(Model model) {
		model.addAttribute("usernameExists", model.asMap().get("usernameExists"));
		model.addAttribute("emailExists", model.asMap().get("emailExists"));
		System.out.println("Sesssion Through Checking refValue : " + refValue);
		System.out.println("Sesssion Through Checking prodID : " + prodID);
		boolean showSignup = (refValue != null) ? true : false;
		model.addAttribute("showSignup", showSignup);
		model.addAttribute("refValue", refValue);
		model.addAttribute("prodID", prodID);
		return "login";
	}
	
	@RequestMapping("/ecom_register")
	public String register(Model model) {
		model.addAttribute("usernameExists", model.asMap().get("usernameExists"));
		model.addAttribute("emailExists", model.asMap().get("emailExists"));
		return "register";
	}
	
	@RequestMapping("/my-profile")
	public String myProfile(Model model, Authentication authentication) {				
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "myProfile";
	}
	
	@RequestMapping("/my-orders")
	public String myOrders(Model model, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		List<Order> orders = orderService.findByUser(user);
		model.addAttribute("orders", orders);
		return "myOrders";
	}
	
	@RequestMapping("/my-address")
	public String myAddress(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "myAddress";
	}
	
	@RequestMapping("/reset-password")
	public String resetPassword(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "resetPassword";
	}   
	
	@RequestMapping(value="/update-user-address", method=RequestMethod.POST)
	public String updateUserAddress(@ModelAttribute("address") Address address, 
			Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		currentUser.setAddress(address);
		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);				
		userSecurityService.authenticateUser(currentUser.getEmail());	
		// return "redirect:/my-address";
		return "myAddress";
	}
	
	
	@RequestMapping("/affiliate")
	public String affiliate(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "becomeAffiliate";
	}
	
	@RequestMapping(value="/becomeAffiliate", method=RequestMethod.POST)
	public String becomeAffiliate(Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		 /*
		 * User became a Affiliate member logic
		 * 
		 */
		//AffiliateUser checkAffiliateUser = affiliateUserService.findByUsername(getUsername());
//		boolean checkId = visitorService.getAffiliateId(getUsername());
//		if(checkId) {
//			User user = userService.findByUsername(principal.getName());
//			model.addAttribute("user", user);
//			System.out.println("checking for user already affiliate member or not!!!");
//			model.addAttribute("affiliateAlreadyMember", true);
//		 	return "becomeAffiliate";
//		
//		}else 
		if(visitorService.checkNull(currentUser.getId()) != null) {
			User user = userService.findByUsername(principal.getName());
			model.addAttribute("user", user);
			System.out.println("User already Affiliate Memeber..");
			model.addAttribute("affiliateAlreadyMember", true);
		 	return "becomeAffiliate";
			
		}else {
			Random rand = new Random(); //instance of random class
		    int upperbound = 999999;
			int int_random = rand.nextInt(upperbound); 
			char ch1 = Character.toUpperCase(currentUser.getFirstName().charAt(0));
			char ch2 = Character.toUpperCase(currentUser.getLastName().charAt(0));
			String Str=new StringBuilder().append(ch1).append(ch2).toString();
			String  inviteCode = Str + currentUser.getPincode() + String.valueOf(int_random);
			System.out.println("Invition code : "+ inviteCode);
			AffiliateUserRegistrationDto userDto =new AffiliateUserRegistrationDto();
			userDto.setFirstName(currentUser.getFirstName());
			userDto.setLastName(currentUser.getLastName());
			userDto.setEmail(currentUser.getEmail());
			userDto.setPhone(currentUser.getPhone());
			userDto.setPincode(currentUser.getPincode());
			userDto.setUsername(currentUser.getUsername());
			userDto.setInviteCode(inviteCode);
			userDto.setPassword(currentUser.getPassword());
			
			AffiliateUser affiliateUser = affiliateUserService.save(userDto);
			Long affiliateID = affiliateUser.getId();
			System.out.println("User id is:"+ currentUser.getId());
			visitorService.somodeskTest1Update(affiliateID,currentUser.getId());
			System.out.println("User became an Affiliate Member...");
			User user = userService.findByUsername(principal.getName());
			model.addAttribute("user", user);
			model.addAttribute("AffiliateUpdateSuccess", true);
		 	return "becomeAffiliate";
		}
	}
	
	
	
	@RequestMapping(value="/new-user", method=RequestMethod.POST)
	public String newUserPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResults,
							  @ModelAttribute("new-password") String password, 
							  RedirectAttributes redirectAttributes, Model model) {
		
		model.addAttribute("email", user.getEmail());
		model.addAttribute("username", user.getUsername());	
		boolean invalidFields = false;
		if (bindingResults.hasErrors()) {
			return "redirect:/ecom_register";
		}		
		if (userService.findByUsername(user.getUsername()) != null) {
			redirectAttributes.addFlashAttribute("usernameExists", true);
			invalidFields = true;
		}
		if (userService.findByEmail(user.getEmail()) != null) {
			redirectAttributes.addFlashAttribute("emailExists", true);
			invalidFields = true;
		}
		/* if(new Email_Checker().isAddressValid(user.getEmail()) == true)
		 {
			    redirectAttributes.addFlashAttribute("email", true);
				invalidFields = true;
		 }*/
		
		if (checkPassPolicy(password) == false) {
			redirectAttributes.addFlashAttribute("passwordMessage", true);
			invalidFields = true;
		}	
		if (invalidFields) {
			return "redirect:/ecom_register";
		}
		
		String firstName = user.getFirstName();
		String lastNAme = user.getLastName();
		String phone = user.getPhone();
		String pincode = user.getPincode();
		user = userService.createUser(user.getUsername(), password, user.getEmail(), Arrays.asList("ROLE_USER"),user.getPincode());
		Long userId = user.getId();
		user.setFirstName(firstName);
		user.setLastName(lastNAme);
		user.setPhone(phone);
		user.setPincode(pincode);
		userService.save(user);
		userSecurityService.authenticateUser(user.getEmail());
	/*
	 *  Same session product link click by user
	 *  Registration will happen and mail will be send to Affiliate user
	 *  New User Registered himself As Customer Only..Not Affiliate Member
	 */
		
		if(refValue != null  && prodID != null) {
			System.out.println("Step:1 --> New User Register Through Product URL.");
        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        	Date date = new Date(System.currentTimeMillis());
			String registerDate = formatter.format(date);
			SomodeskProductLinkDetails details = new SomodeskProductLinkDetails();
			details.setUserId(userId);
			//details.setAffiliateId(newAffiliateID); 
			details.setProdId(prodID);
			details.setRefAid(Long.valueOf(refValue));
			details.setDate(registerDate);
			somodeskTest1Service.save(details);
			
		/*
		 * Mail functionality Started
		 * Automatically email will send to Affiliate User
		 * 
		 */
			String bodyMessage = bodyMessage();
			Optional<AffiliateUser> val = affiService.findById(Long.valueOf(refValue));
			if (val.isPresent()) {
	        	AffiliateUser existingAffiUser = val.get();
	            String refAffiUserEmail = existingAffiUser.getEmail();
	            System.out.println("Referral Affilate User email id is :" + refAffiUserEmail);
	            try {
					SimpleMailMessage newEmail = mailConstructor.sendEmail(refAffiUserEmail, subject, bodyMessage); 
				    mailSender.send(newEmail);
					SimpleMailMessage welcomEmail = mailConstructor.sendEmail(user.getEmail(),welcomSubject, welcomeBodyMessage+welcomeThankMsg);
				    mailSender.send(welcomEmail);
				    //Session should null
				    refValue = null;
				    prodID = null;
					
				    	         	
	            }catch(Exception e) {
	            	System.out.println("Exception Occured "+ e);
	            	throw e;
	            }

			}
			return "redirect:/shopping-cart/add-item";
		}else {
			System.out.println("Normal Registraion...");
			return "redirect:/my-profile";  
		}
		
	}
	
	
	/*
	  	@RequestMapping(value="/update-user-info", method=RequestMethod.POST)
	public String updateUserInfo( @ModelAttribute("user") User user,
			  @RequestParam("newPassword") String newPassword,
			  Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
		throw new Exception ("User not found");
		}
		//check username already exists
		User existingUser = userService.findByUsername(user.getUsername());
		if (existingUser != null && !existingUser.getId().equals(currentUser.getId()))  {
		model.addAttribute("usernameExists", true);
		return "myProfile";
		}	
		//check email already exists
		existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null && !existingUser.getId().equals(currentUser.getId()))  {
		model.addAttribute("emailExists", true);
		return "myProfile";
		}			
		//update password
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		String dbPassword = currentUser.getPassword();
		if(passwordEncoder.matches(user.getPassword(), dbPassword)){
		currentUser.setPassword(passwordEncoder.encode(newPassword));
		} else {
		model.addAttribute("incorrectPassword", true);
		return "myProfile";
		}
		}		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());		
		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);				
		userSecurityService.authenticateUser(currentUser.getEmail());		
		return "myProfile";
		}
		
	   */
	
	
	@RequestMapping(value="/update-user-info", method=RequestMethod.POST)
	public String updateUserInfo( @ModelAttribute("user") User user,
								  Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
			throw new Exception ("User not found");
		}

	    currentUser.setPassword(currentUser.getPassword());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(currentUser.getUsername());
		currentUser.setEmail(currentUser.getEmail());		
		userService.save(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);				
		userSecurityService.authenticateUser(currentUser.getEmail());		
		return "myProfile";
	}
	
	
	@RequestMapping(value="/update-pass-info", method=RequestMethod.POST)
	public String updatePasswordInfo( @ModelAttribute("user") User user,
			  @RequestParam("newPassword") String newPassword,
			  Model model, Principal principal) throws Exception {
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser == null) {
		throw new Exception ("User not found");
		}
		
		if (checkPassPolicy(newPassword) == false) {
			model.addAttribute("passwordMessage", true);
			return "resetPassword";
		}	
		
		/*update password*/
		BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
		if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")){
			String dbPassword = currentUser.getPassword();
			if(passwordEncoder.matches(user.getPassword(), dbPassword)){
				currentUser.setPassword(passwordEncoder.encode(newPassword));
			} 
			else {
				model.addAttribute("incorrectPassword", true);
				return "resetPassword";
			}
		}		
		currentUser.setFirstName(currentUser.getFirstName());
		currentUser.setLastName(currentUser.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhone(currentUser.getPhone());	
		currentUser.setPincode(currentUser.getPincode());	
		userService.save(currentUser);
		/* update password of affiliate_user*/
		AffiliateUser currentUserAff = affiliateUserService.findByUsername(principal.getName());
		if(currentUserAff != null)
		{
		System.out.println("af_User existing record id " + currentUserAff.getId() + " username " +  currentUserAff.getUsername());
		currentUserAff.setPassword(passwordEncoder.encode(newPassword));
		currentUserAff.setFirstName(currentUserAff.getFirstName());
		currentUserAff.setLastName(currentUserAff.getLastName());
		currentUserAff.setUsername(currentUserAff.getUsername());
		currentUserAff.setEmail(currentUserAff.getEmail());		
		currentUserAff.setPhone(currentUserAff.getPhone());	
		currentUserAff.setPincode(currentUserAff.getPincode());	
		currentUserAff.setInviteCode(currentUserAff.getInviteCode());
		affiliateUserService.save(currentUserAff);
		System.out.println("af_User update AFTER SUCCESS ");
		}
		model.addAttribute("updateSuccess", true);
		model.addAttribute("user", currentUser);				
		userSecurityService.authenticateUser(currentUser.getEmail());		
		return "resetPassword";
		}
		

	
	
	
	@RequestMapping("/order-detail")
	public String orderDetail(@RequestParam("order") Long id, Model model) {
		Order order = orderService.findOrderWithDetails(id);
		model.addAttribute("allCategories", articleService.getAllCategories());
		model.addAttribute("order", order);
		return "orderDetails";
	}
	
	@RequestMapping("/admin-order-detail")
	public String adminorderDetail(@RequestParam("order") Long id, Model model) {
		model.addAttribute("allCategories", articleService.getAllCategories());
		Order order = orderService.findOrderWithDetails(id);
		model.addAttribute("order", order);
		return "adminorderDetails";
	}

	public void CheckVal(RefernceIdStore rf) {
		System.out.println("Same Session Referral Afiiliate id is : "+rf.getRefId());
		System.out.println("Same Session Referral product id is : "+rf.getProdId());
		refValue = String.valueOf(rf.getRefId());
		prodID = rf.getProdId();
		
	}
	
    public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    System.out.println("Current Login User is (getUserName) : "+ currentUserName);
		    return currentUserName;
		}else { 
			return "invalidUser";
		}
    }
    
    public String bodyMessage() {
    	String customMsg ="\nDear Sir/Madam,\n\nSomeomne used your Product Referral Link to register himself.\nYou will get commission when he will buy that product or any other products.\n\n";
    	String thankMsg = "\nThanks,\nSomodesk Customer Service";   	
    	String str = customMsg + thankMsg;
    	return str;
    }
    
    public boolean checkPassPolicy(String password)
    {
    	boolean result = false;
    	try
    	{
    		// Regex to check valid password. 
            String regex = "^(?=.*[a-z])"
            		+ "(?=.*[A-Z])"
            		+ "(?=.*[0-9])"
            		+ "(?=.*[!@#$%^&*_=+-]).{8,20}$"; 
      
            // Compile the ReGex 
            Pattern p = Pattern.compile(regex); 
            // Pattern class contains matcher() method to find matching between given password and regular expression. 
            Matcher m = p.matcher(password); 
            // Return if the password  matched the ReGex 
            result=  m.matches();
    	}
		catch(Exception ex){
			System.out.println("Error in checkPassPolicy " + ex.getMessage());
		}    
    	return result;
    }
	
 
    
    //Email checker
    @RequestMapping(value ="/checkingEmail",method=RequestMethod.POST)
    public  @ResponseBody String  getSearchEmail(@RequestParam String val) {
    	System.out.println("Checking email is real or not for :  "+ val);
    	boolean exists = new Email_Checker().isAddressValid(val);
    	System.out.println( val + " is valid? " + exists);
    	int emailCount = (userService.findByEmail(val) == null) ? 0 : 1; 
    	System.out.println("Email count for Already exists: "+ emailCount);
    	if(exists==true){
    		return  "{\"emailCount\":"+emailCount+",\"response\":\"true\"}";
    	}else {
    		return  "{\"emailCount\":"+emailCount+",\"response\":\"false\"}";
    		
    	}
    	
    }
    
    
    
    
    
    
}
