package com.aipl.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aipl.store.domain.AffiliateUser;
import com.aipl.store.domain.User;
import com.aipl.store.dto.AffiliateUserRegistrationDto;
import com.aipl.store.service.AffiliateSecurityService;
import com.aipl.store.service.AffiliateUserService;
import com.aipl.store.service.impl.UserSecurityService;

@Controller
@RequestMapping("/af/affi_registration")
public class AffiliateUserRegistrationController {
	
    @Autowired
    private AffiliateUserService affiliateUserService;
    
    @ModelAttribute("user")
    public AffiliateUserRegistrationDto userRegistrationDto() {
        return new AffiliateUserRegistrationDto();
    }
    
	@GetMapping
    public String affiRegistration(Model model,Authentication authentication) {
//		User user = (User) authentication.getPrincipal();
//		System.out.println("User is : "+ user);
//		model.addAttribute("email", user.getEmail());
//		model.addAttribute("username", user.getUsername());	
//		model.addAttribute("user", user);
        return "affi_registration";
    }
	
    @PostMapping
    public String registerUserAccount(@ModelAttribute("affiliateuser") @Valid AffiliateUserRegistrationDto userDto,
                                      BindingResult result){

        AffiliateUser existing = affiliateUserService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "affi_registration";
        }

//        if(userService.save1(userDto))
//            return "redirect:/registration?success";
        
        affiliateUserService.save(userDto);
        
        //affiliateSecurityService.autoLogin(userDto.getUsername(), userDto.getPassword());

         return "redirect:/af/affi_dashboard";
    }

}
