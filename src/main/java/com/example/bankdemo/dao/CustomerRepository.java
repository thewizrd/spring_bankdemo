package com.example.bankdemo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bankdemo.data.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	// Check SSN exists
	boolean existsByssnNumber(String ssnNumber);
	Customer findByssnNumber(String ssnNumber);
}
