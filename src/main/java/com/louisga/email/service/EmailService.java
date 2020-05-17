package com.louisga.email.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.louisga.email.model.EmailPayload;

public interface EmailService {

	List<EmailPayload> getEmails();

	EmailPayload getEmail(String emailId);

	void processEmail(EmailPayload payload) throws MessagingException;

	void deleteEmail(String emailId);
}
