package com.email.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.email.model.EmailPayload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	@Value("${spring.mail.sender}")
	private String MAIL_SENDER;
	
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	
	public void sendEmailRequest(@Valid EmailPayload payload) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("body", payload.getBody());
		
		Context context = new Context(LocaleContextHolder.getLocale());
		context.setVariables(model);
		
		String mailBody = this.templateEngine.process("fragments/email.html", context);
		helper.setFrom(MAIL_SENDER);
		helper.setReplyTo(MAIL_SENDER);
		helper.setSentDate(new Date());
		helper.setText(mailBody);
		helper.setSubject(payload.getSubject().toUpperCase());
		
		for(String recipientAddress : payload.getTo()) {
			helper.setTo(recipientAddress);
			javaMailSender.send(mimeMessage);
		}
	}

}
