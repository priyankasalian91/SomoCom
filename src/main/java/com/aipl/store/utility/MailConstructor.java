package com.aipl.store.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailConstructor {

	@Autowired
	private Environment env;
	
	public SimpleMailMessage sendEmail(String email,String subjct, String msg) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subjct);
        simpleMailMessage.setText(msg);
        simpleMailMessage.setFrom(env.getProperty("support.email"));
        return simpleMailMessage;
		
	}
}
