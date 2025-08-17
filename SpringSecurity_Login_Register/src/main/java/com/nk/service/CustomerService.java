package com.nk.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nk.entity.Customer;
import com.nk.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private CustomerRepository repo;

	public boolean savecustomer(Customer c) {
		String encode = encoder.encode(c.getPwd());
		c.setPwd(encode);

		Customer customer = repo.save(c);
		return customer.getCid() != null;

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Customer c = repo.findByEmail(email);
		
		return new User(c.getEmail(),c.getPwd(),Collections.emptyList());
	}
}
