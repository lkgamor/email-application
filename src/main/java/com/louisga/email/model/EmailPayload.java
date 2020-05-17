package com.louisga.email.model;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 
 * @author LouisGa
 *
 */
@Data
public class EmailPayload {
	
	@NotBlank(message="Email subject cannot be empty")
	private String subject;
	
	@NotBlank
	private String body;
	private String dateTime;
	
	@NotEmpty(message="Please provide a destination email address")
	private List<@NotEmpty @Email String> to;
}
