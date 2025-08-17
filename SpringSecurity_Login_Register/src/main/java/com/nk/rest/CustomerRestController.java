package com.nk.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nk.entity.Customer;
import com.nk.service.CustomerService;

@RestController
public class CustomerRestController {
	@Autowired
	private CustomerService service;
	@Autowired
	private AuthenticationManager authmanager;

	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer cust) {

		boolean status = service.savecustomer(cust);

		if (status) {
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginCustomer(@RequestBody Customer cust) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(cust.getEmail(),
				cust.getPwd());

		Authentication authenticate = authmanager.authenticate(token);
		boolean status = authenticate.isAuthenticated();

		if (status) {
			return new ResponseEntity<String>("Welcome", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}

	}
}
