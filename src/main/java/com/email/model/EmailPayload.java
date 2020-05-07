package com.email.model;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class EmailPayload {
	
	@NotBlank
	private String subject;
	
	@NotBlank
	private String body;
	private String dateTime;
	
	@NotEmpty
	private List<@NotEmpty @Email String> to;
}
