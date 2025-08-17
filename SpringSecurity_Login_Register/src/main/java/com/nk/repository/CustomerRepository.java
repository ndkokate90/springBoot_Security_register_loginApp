package com.nk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nk.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	public Customer findByEmail(String email);

}
