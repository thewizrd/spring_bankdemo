package com.example.bankdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bankdemo.dao.UserRespository;
import com.example.bankdemo.data.User;

@Service
public class BankUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRespository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new BankUserDetails(user);
    }

}
