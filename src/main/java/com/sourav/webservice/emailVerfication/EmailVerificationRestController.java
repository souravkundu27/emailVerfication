package com.sourav.webservice.emailVerfication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailVerificationRestController {

	@Autowired
	private EmailVerificationService service;
	
	@GetMapping(path = "/sourav/emailverification/{user}")
	String welcome(@PathVariable String user) {
		return "Welcome "+user+"  :)";
	}

	@PostMapping(path = "/sourav/emailverification/create")
	ResponseEntity createCode(@RequestBody EmailDetails emailDetails) {
		EmailDetails createdEmailDetails = service.createCode(emailDetails);
		if (createdEmailDetails != null) {
		    return new ResponseEntity(createdEmailDetails, HttpStatus.CREATED);
		} else {
			return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
		}
	}

	@PostMapping(path = "/sourav/emailverification/verify")
	ResponseEntity verifyOwnership(@RequestBody EmailDetails emailDetails) {
		boolean status = service.verify(emailDetails);
		if (status) {
		    return new ResponseEntity("Verified successfully", HttpStatus.FOUND);
		} else {
			return new ResponseEntity("email addr and code match not found within specified validity period and no of miss", HttpStatus.NOT_FOUND);
		}
	}
}
