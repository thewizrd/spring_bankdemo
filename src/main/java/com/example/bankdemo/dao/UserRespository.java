package com.example.bankdemo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.bankdemo.data.User;

public interface UserRespository extends CrudRepository<User, Integer> {
	// Validate usern/pass
	User findFirstByUsernameAndPassword(String username, String password);
	boolean existsByUsernameAndPassword(String username, String password);
	// Check if uname exists
	boolean existsByUsername(String username);
	User findFirstByUsername(String username);
}
