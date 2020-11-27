package com.aipl.store.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aipl.store.common.ReadSettingsFile;
import com.aipl.store.domain.Article;
import com.aipl.store.domain.BulkPurchase;
import com.aipl.store.domain.Feedback;
import com.aipl.store.service.FeedbackService;
import com.aipl.store.utility.MailConstructor;

@Controller
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
    private MailConstructor mailConstructor;
	
    @Autowired
    private JavaMailSender mailSender;
	
	@RequestMapping("/contactus")
	public String contacttus(Model model) {	
		model.addAttribute("feedbackSaved", true);
		return "contactus";
	}
	
	
	@RequestMapping(value="/contactus", method=RequestMethod.POST)
	public String addFeedback(@ModelAttribute("feedback") Feedback feedback, Model model ,  RedirectAttributes attributes) {  
		Timestamp feedbackdt = new Timestamp(System.currentTimeMillis());
		feedback.setMailsent(true);
		feedback.setFeedbackdt(feedbackdt);
	   feedbackService.save(feedback);		
	   model.addAttribute("feedbackSaved", true);
	   
	   //send mail 
	    String sendTo = new ReadSettingsFile().getValueFromParam("feedbackto").toString();
		String subject = "Somodesk.com - Received a Feedback";
		String bodyMessage = bodyMessage(feedback);
		SimpleMailMessage newEmail = mailConstructor.sendEmail(sendTo, subject, bodyMessage);
	    mailSender.send(newEmail);
	    
	   return "redirect:/contactus";
	   //return "contactus";
	}

	public String bodyMessage(Feedback feedback) {
    	String customMsg ="\nDear Sir/Madam,\n\n" + "We have received a feedback on Somodesk.com from " + feedback.getName() + " (Email ID : " + feedback.getEmail() +") and the message / comment is as follows - \n " + 
    			          feedback.getComments() + ".\n\n";
    	String thankMsg = "\nThanks,\nSomodesk Customer Service";   	
    	String str = customMsg + thankMsg;
    	return str;
    }
	
}
