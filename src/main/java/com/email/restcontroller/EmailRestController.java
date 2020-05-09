package com.email.restcontroller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.model.EmailPayload;
import com.email.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class EmailRestController {

	private final EmailService emailService;
	
	@PostMapping("/send")
	public ResponseEntity<EmailPayload> receiveEmailRequest(@Valid @RequestBody EmailPayload payload) throws MessagingException {
		
		emailService.processEmail(payload);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
