package com.louisga.email.restcontroller;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.louisga.email.model.EmailPayload;
import com.louisga.email.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/emails")
@RequiredArgsConstructor
public class EmailRestController {

	/**
	 * @author LouisGa
	 */
	
	private final EmailService emailService;
	
	/**
	 * GET /api/v1/emails
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<EmailPayload>> fetchEmailRequests() {	
		return new ResponseEntity<>(emailService.getEmails(), HttpStatus.OK);
	}
	
	/**
	 * GET /api/v1/emails/:id
	 * @param id email id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<EmailPayload> fetchEmailRequestInfo(@PathVariable String id) {	
		return new ResponseEntity<>(emailService.getEmail(id), HttpStatus.OK);
	}
	
	/**
	 * POST /api/v1/emails/send
	 * @return
	 */
	@PostMapping("/send")
	public ResponseEntity<EmailPayload> receiveEmailRequest(@Valid @RequestBody EmailPayload payload) throws MessagingException {
		emailService.processEmail(payload);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	/**
	 * DELETE /api/v1/emails/:id
	 * @param id email id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmailRequest(@PathVariable String id) {	
		emailService.deleteEmail(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
}
