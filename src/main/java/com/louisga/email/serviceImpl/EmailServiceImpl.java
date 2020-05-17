package com.louisga.email.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.louisga.email.model.EmailPayload;
import com.louisga.email.service.EmailService;
import com.louisga.email.utils.AppConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author LouisGa
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	@Value("${spring.mail.sender}")
	private String MAIL_SENDER;
	
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;

	@Override
	public List<EmailPayload> getEmails() {
		// TODO Write Data Layer Logic to fetch all mails from database
		return null;
	}
	
	@Override
	public EmailPayload getEmail(String emailId) {
		// TODO Write Data Layer Logic to fetch a mail from database
		return null;
	}
	
	@Async
	@Override
	public void processEmail(EmailPayload payload) throws MessagingException {
	
		Map<String, Object> model = new HashMap<String, Object>();
		model.put(AppConstants.KEY_EMAIL_BODY, payload.getBody());
		model.put(AppConstants.KEY_EMAIL_DATETIME, payload.getDateTime());
	
		Context context = new Context(LocaleContextHolder.getLocale());
		context.setVariables(model);
	
		String mailBody = this.templateEngine.process(AppConstants.EMAI_TEMPLATE_FILE, context);
		sendEmail(payload, mailBody);		
	}
	
	private void sendEmail(EmailPayload payload, String mailBody) throws MessagingException {
	
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(MAIL_SENDER);
		helper.setReplyTo(MAIL_SENDER);
		helper.setSentDate(new Date());
		helper.setText(mailBody, true);
		helper.setSubject(payload.getSubject().toUpperCase());
	
		for(String recipientAddress : payload.getTo()) {
			helper.setTo(recipientAddress);
			try {
				javaMailSender.send(mimeMessage);
				log.info(AppConstants.EMAIL_SENT_SUCCESS_LOG, recipientAddress);
			} catch (MailException e) {
				log.info(AppConstants.EMAIL_SENT_FAILURE_LOG, recipientAddress);
				log.info(AppConstants.EMAIL_SENT_FAILURE_REASON_LOG, e.getMessage());
			}
		}
	}
	
	@Override
	public void deleteEmail(String emailId) {
		// TODO Write Data Layer Logic to delete a mail from database
	}

}
