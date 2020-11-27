package com.aipl.store.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aipl.store.domain.Address;
import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.Order;
import com.aipl.store.domain.Payment;
import com.aipl.store.domain.RefernceIdStore;
import com.aipl.store.domain.Shipping;
import com.aipl.store.domain.ShoppingCart;
import com.aipl.store.domain.SomodeskCheckProduct;
import com.aipl.store.domain.User;
import com.aipl.store.service.AffiliateUserService;
import com.aipl.store.service.OrderService;
import com.aipl.store.service.ShoppingCartService;
import com.aipl.store.service.SomodeskCheckProductService;
import com.aipl.store.service.VisitorService;
import com.aipl.store.utility.MailConstructor;


@Controller
public class CheckoutControler {
	
	//to handle one Session
	public static String affirefValue = null;
	public static String prodID = null;
	//To handle Message for Email
	public static String subject = "Somodesk - Purchased Through Your Referral Link";
	public static String subject1 = "Somodesk - Invite or Registered User Purchased Product";
	public static String bodyMsg ="\nDear Sir/Madam,\n\nSomeomne use your Referral Link to purchased the product.\nYou will get commission after the return policy finish.\n\nThanks,\nLuciApp";
	public static String bodyMessage1 ="\nDear Sir/Madam,\n\nWe like to infrom you that the invite User or User that is regisrted through your product link is purchased products.\nYou will get commission after the return policy finish.\n\nThanks,\nSomodesk.com";

	@Autowired
    VisitorService visitorService;
	
	@Autowired 
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;
	

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;
    
    @Autowired
    AffiliateUserService affiService;
    
    @Autowired
    private SomodeskCheckProductService somodeskCheckProductService;

	@RequestMapping("/checkout")
	public String checkout( @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
							Model model, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();	
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
		if(shoppingCart.isEmpty()) {
			model.addAttribute("emptyCart", true);
			return "redirect:/shopping-cart/cart";
		}						
		model.addAttribute("cartItemList", shoppingCart.getCartItems());
		model.addAttribute("shoppingCart", shoppingCart);
		if(missingRequiredField) {
			model.addAttribute("missingRequiredField", true);
		}		
		return "checkout";		
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String placeOrder(@ModelAttribute("shipping") Shipping shipping,
							@ModelAttribute("address") Address address,
							@ModelAttribute("payment") Payment payment,
							RedirectAttributes redirectAttributes, Authentication authentication) {		
		User user = (User) authentication.getPrincipal();		
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);	
		if (!shoppingCart.isEmpty()) {
			shipping.setAddress(address);
			Order order = orderService.createOrder(shoppingCart, shipping, payment, user);
			System.out.println("order"+ order.getId());
			redirectAttributes.addFlashAttribute("order", order);
			//Date Logic
        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        	Date date = new Date(System.currentTimeMillis());
			String purchaseDate = formatter.format(date);
			String username = getUsername();
			int userID = visitorService.newUserID(username);
			//long affiliateId =  visitorService.getAffiliateUserID(username);
			//Which typ of user for Default user
			int exist1 = visitorService.userExistST1(userID);
			int exist2 = visitorService.userExistST2(userID);
			int a = 0;
			int b = 0;
			int refAid = 0;
			if(exist1 != a) {
				refAid = visitorService.getReferralId(userID);
			}
			
			if(exist2 != b) {
				refAid = visitorService.getInviteId(userID);
			}
			
			if(affirefValue !=  null && prodID != null) {
				String affiliatecommisson = "10%";
				String productStatus = "y"; // y -> purchased
				BigDecimal orderAmount = visitorService.getOrderAmount(userID,order.getId() );

				SomodeskCheckProduct details = new SomodeskCheckProduct();
				details.setUserId((long) Integer.valueOf(userID));
				//details.setAffiliateId(affiliateId);
				details.setProdOrderdate(purchaseDate);
				details.setProdCommission(affiliatecommisson);
				details.setProdStatus(productStatus);
				details.setProdId(prodID);
				details.setProductTotal(orderAmount);
				somodeskCheckProductService.save(details);
				
				//Email functionality to affiliate User
				Optional<AffiliateUser> val = affiService.findById(Long.parseLong(affirefValue));
		        if (val.isPresent()) {
		        	AffiliateUser existingAffiUser = val.get();
		            String refAffiUserEmail = existingAffiUser.getEmail();
		            System.out.println("Referral Affilate User email is :" +refAffiUserEmail);
		            String prodName = visitorService.getProductName(Integer.valueOf(prodID));
		            String bodyMessage = bodyMessage(prodName, orderAmount,affiliatecommisson);
		            try {
						SimpleMailMessage newEmail = mailConstructor.sendEmail(refAffiUserEmail,subject1, bodyMessage);
		            	mailSender.send(newEmail);
				    	affirefValue = null;
				    	prodID = null;
				    } catch(Exception e) {
				    	System.out.println("Exception occured : "+ e.getMessage());
				    	throw e;
				    }
		        } else {
		            System.out.printf("No existingAffiUser found with id");
		        }
		        
		    //Normal user, invite user, without session user buying product
			}else if (refAid != 0){
				System.out.println("@@@@@@@@ You are already registered and now buying poduct. ####");
				String affiliatecommisson = "7%";
				String productStatus = "y"; // y -> purchased
				BigDecimal orderAmount = visitorService.getOrderAmount(userID,order.getId());
				
				//Multiple product
				String productID = "";
				List<Map<String, Object>> listArctle = visitorService.fetchListArticleId(userID, order.getId());
				System.out.println("Articles are : "+ listArctle.toString());
				if (listArctle.size() > 0) {
		            StringBuilder sb = new StringBuilder();
		            for (Map<String, Object> s : listArctle) { 
		            	sb.append(s.get("article_id")).append(","); 
		            }
		            productID = sb.deleteCharAt(sb.length() - 1).toString();
		            System.out.println(productID);

				}
				
				
				SomodeskCheckProduct details = new SomodeskCheckProduct();
				details.setUserId((long) Integer.valueOf(userID));
				details.setProdOrderdate(purchaseDate);
				details.setProdCommission(affiliatecommisson);
				details.setProdStatus(productStatus);
				details.setProdId(String.valueOf(productID)); //null
				details.setProductTotal(orderAmount);
				//details.setAffiliateId(affiliateId);
				somodeskCheckProductService.save(details);
				
				//Check for inviteuser register or referral user register
			
				//Email functionality to affiliate User
				Optional<AffiliateUser> val = affiService.findById((long)Integer.valueOf(refAid));
		        if (val.isPresent()) {
		        	AffiliateUser existingAffiUser = val.get();
		            String refAffiUserEmail = existingAffiUser.getEmail();
		            System.out.println("Referral Affilate User email is :" +refAffiUserEmail);
		            //String prodName = visitorService.getProductName(productID); //null
		            //String bodyMessage = bodyMessage(prodName, orderAmount,affiliatecommisson);
		            try {
						SimpleMailMessage newEmail = mailConstructor.sendEmail(refAffiUserEmail,subject1, bodyMessage1);
		            	mailSender.send(newEmail);
				    } catch(Exception e) {
				    	System.out.println("Exception occured : "+ e.getMessage());
				    	throw e;
				    }
		        } else {
		            System.out.printf("No existingAffiUser found with id");
		        }
			}else {
				System.out.println("@@@@@ You are ADMIN user####");
			}
			
		}	
		return "redirect:/order-submitted";
	}
	
	@RequestMapping(value = "/order-submitted", method = RequestMethod.GET)
	public String orderSubmitted(Model model) {
		Order order = (Order) model.asMap().get("order");
		if (order == null) {
			return "redirect:/";
		}
		model.addAttribute("order", order);
		return "orderSubmitted";	
	}
	
	//Logic for indirect 
	public void CheckVal(RefernceIdStore rf) {
		System.out.println("Checkout profuct through affiliate ID is : "+rf.getRefId());
		affirefValue = String.valueOf(rf.getRefId());
		prodID =String.valueOf( rf.getProdId());
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
    
    

    public String bodyMessage(String product, BigDecimal rate,String commision) {
    	String customMsg ="\nDear Sir/Madam,\n\nWe like to infrom you that someone used your Referral Link to purchase the product.\nYou will get commission as per the compilation of return policy.\n";
    	String thankMsg = "\nThanks,\nSomodesk Customer Service";

//    	String htmlMsg = "<body><h4 style='color:green;'> Dear <b style='color:red;'>" + affiUsername + "</b>,"
//    			+ "\n Product Rate and Commission Information, <br><table>"
//    			+ "<tr><td>Item Name     </td><td>  " + product + "</td></tr>" 
//    			+ "<tr><td>Rate   </td><td>  " + rate + "</td></tr>"
//    			+ "<tr><td>Commission   </td><td> " + commision + "</td></tr>"
//    			+ "</h4</body>";
    	String htmlMsg = "\nItem Name:"+product+"\nProdutc Amount:"+rate+"\nCommission Rate"+commision+"\n\n";
    	
    	String str = customMsg + htmlMsg+ thankMsg;
    	return str;
    }

}
