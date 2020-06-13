package com.example.bankdemo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bankdemo.config.BankUserDetails;
import com.example.bankdemo.dao.CustomerRepository;
import com.example.bankdemo.dao.UserRespository;
import com.example.bankdemo.data.User;

@Controller
public class DashboardController {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private UserRespository userRepo;
	
	@GetMapping("/user/dashboard")
	public String dashboardPage(Model model, HttpServletRequest request, HttpSession session)
	{
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc.getAuthentication() == null || !sc.getAuthentication().isAuthenticated()) {
			return "redirect:/user/login";			
		}
		
		BankUserDetails bankUser = (BankUserDetails) sc.getAuthentication().getPrincipal();
		
		User user = userRepo.findFirstByUsername(bankUser.getUsername());
		model.addAttribute("accounts", user != null ? user.getCustomer().getAccounts() : null);
		
		return "account/dashboard";
	}
}
