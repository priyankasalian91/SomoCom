package com.aipl.store.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.aipl.store.config.HMAC;
import com.aipl.store.domain.AffiliateRegisterFees;
import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.dto.AffiliateCustomerToPay;
import com.aipl.store.dto.AffiliateUserRegistrationDto;
import com.aipl.store.dto.RazorPay;
import com.aipl.store.dto.Response;
import com.aipl.store.dto.Signature;
import com.aipl.store.repository.AffiliateUserRepository;
import com.aipl.store.service.RegistrationFeesService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


@Controller
public class RazorpayController {
	

	private RazorpayClient client;
	private static Gson gson = new Gson();

	private static final String SECRET_ID = "rzp_test_jPnRQ7eQ1QZRLX";
	private static final String SECRET_KEY = "ETkCJUflRHaiF0OKyUayTaZW";
	public  static String orderId = null;
	public static String phoneNumber = null;
	public static String email = null;
	public static String amount = null;
	
	@Autowired
	private RegistrationFeesService registrationFeesService;
    @Autowired
    private AffiliateUserRepository affiliateUserRepository;
	

	
	@RequestMapping(value="/logo",  method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response) throws IOException {
         ClassPathResource imgFile = new ClassPathResource("static/image/onlineShop.jpg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
	
	
	public RazorpayController() throws RazorpayException {
		this.client =  new RazorpayClient(SECRET_ID, SECRET_KEY); 
	}
	
	//Get method
	@RequestMapping("/register")
    public String affiRegistration(Model model) {
		//model.addAttribute("affiemail", email ); //attribute name, value
		//model.addAttribute("affiphone", phoneNumber ); //attribute name, value
        return "affi_registration";
    }
	
	@RequestMapping(value="/createPayment", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createOrder(@RequestBody AffiliateUserRegistrationDto customer) {
			
		try {
		
			Order order = createRazorPayOrder( customer.getAmount() );
			System.out.println("Order ID is : "+ order.get("id"));
			orderId = order.get("id");
			RazorPay razorPay = getRazorPay((String)order.get("id"), customer);
			if(registrationFeesService.findByEmail(customer.getEmail()) == null){
				email = customer.getEmail();
				phoneNumber = customer.getPhone();
				amount = customer.getAmount();
				return new ResponseEntity<String>(gson.toJson(getResponse(razorPay, 200)),
						HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(gson.toJson(getResponse(new RazorPay(), 500)),
						HttpStatus.EXPECTATION_FAILED);
			}

		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(gson.toJson(getResponse(new RazorPay(), 500)),
				HttpStatus.EXPECTATION_FAILED);
	}
	
	
	@RequestMapping(value="/verifyPayment", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> verifySignature(@RequestBody Signature signature) {
			
		String razorpaySignature = signature.getRazorpaySignature();
		String razorpayPaymentId = signature.getRazorpayPaymentId();
		Object generated_signature = null;
		AffiliateRegisterFees register = new AffiliateRegisterFees();
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    	Date date = new Date(System.currentTimeMillis());
		String registerDate = formatter.format(date);
		try {
			generated_signature = HMAC.calculateRFC2104HMAC(orderId + "|" + razorpayPaymentId, SECRET_KEY);
			System.out.println("Create signature is : "+generated_signature);
			System.out.println("Razpay signature is : "+ razorpaySignature);
			  if (generated_signature.equals(razorpaySignature)) {
				  System.out.println("payment is successful");
				  register.setRazorpayOrderId(signature.getRazorpayOrderId());
				  register.setRazorpayPaymentId(signature.getRazorpayPaymentId());
				  register.setRazorpaySignature(signature.getRazorpaySignature());
				  register.setPaymentsStatus("successful");
				  register.setPaymentDate(registerDate);
				  register.setEmail(email);
				  register.setAmount(amount);
				  register.setPhoneNumber(phoneNumber);
				  registrationFeesService.save(register);
				return new ResponseEntity<String>(gson.toJson(getResponseVerify(200)),HttpStatus.OK);
	
			  }
		} catch (SignatureException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<String>(gson.toJson(getResponseVerify(500)),HttpStatus.EXPECTATION_FAILED);

	}
	
	private Response getResponseVerify(int statusCode) {
		Response response = new Response();
		response.setStatusCode(statusCode);
		return response;
	}	
	
	private Response getResponse(RazorPay razorPay, int statusCode) {
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setRazorPay(razorPay);
		return response;
	}	
	
	private RazorPay getRazorPay(String orderId, AffiliateUserRegistrationDto customer) {
		RazorPay razorPay = new RazorPay();
		razorPay.setApplicationFee(convertRupeeToPaise(customer.getAmount()));
		razorPay.setCustomerName(customer.getUsername());
		razorPay.setCustomerEmail(customer.getEmail());
		razorPay.setCustomerContact(customer.getPhone());
		razorPay.setMerchantName("Somodesk");
		razorPay.setPurchaseDescription("Somodesk Product Purchase");
		razorPay.setRazorpayOrderId(orderId);
		razorPay.setSecretKey(SECRET_ID);
		razorPay.setImageURL("/logo");
		razorPay.setTheme("#F37254");
		razorPay.setNotes("notes"+orderId);
		
		return razorPay;
	}
	
	private Order createRazorPayOrder(String amount) throws RazorpayException {
		
		JSONObject options = new JSONObject();
		options.put("amount", convertRupeeToPaise(amount));
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture. 
		return client.Orders.create(options);
	}
	
	private String convertRupeeToPaise(String paise) {
		BigDecimal b = new BigDecimal(paise);
		BigDecimal value = b.multiply(new BigDecimal("100"));
		return value.setScale(0, RoundingMode.UP).toString();
		 
	}
	

}
