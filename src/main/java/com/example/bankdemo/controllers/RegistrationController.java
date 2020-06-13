package com.example.bankdemo.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bankdemo.controllers.dto.UserAccountTypeForm;
import com.example.bankdemo.dao.CustomerRepository;
import com.example.bankdemo.dao.UserRespository;
import com.example.bankdemo.data.Account;
import com.example.bankdemo.data.AccountType;
import com.example.bankdemo.data.Address;
import com.example.bankdemo.data.Customer;
import com.example.bankdemo.data.User;

@Controller
@SessionAttributes({"customer"})
public class RegistrationController {
	
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private UserRespository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	// STEP 1 - Registration Page
	@GetMapping("/register/account")
	public String register(ModelMap model)
	{
		if (!model.containsAttribute("customer"))
			model.addAttribute("customer", new Customer());
		if (!model.containsAttribute("address"))
			model.addAttribute("address", new Address());
		model.addAttribute("dateNow", dateFormatter.format(Date.from(Instant.now())));
		return "registration/account_register";
	}

	// STEP 2 - Registration Page (Verification)
	@PostMapping("/register/verify_customer")
	public String verifyCustomer(ModelMap model, RedirectAttributes redirAttr,
			@ModelAttribute Customer customer, @ModelAttribute Address address) {
		if (address.validateData() && customer.validateData()) {
			address.setCustomer(customer);
			customer.setAddress(address);
			
			boolean ssnExists = customerRepo.existsByssnNumber(customer.getSsnNumber());

			if (!ssnExists) {
				// Redirect to new page
				return "redirect:/register/signup_account";
			} else {
				redirAttr.addFlashAttribute("errorMsg", "SSN number already exists! Do you have an account already?");
			}
		} else {
			redirAttr.addFlashAttribute("errorMsg", "Invalid entry!!");
		}
		return "redirect:/register/account";
	}

	// STEP 3 - Registration Page Pt. 2
	@GetMapping("/register/signup_account")
	public String signupAccount(ModelMap model)
	{
		if (!model.containsAttribute("userAccount"))
			model.addAttribute("userAccount", new UserAccountTypeForm());
		return "registration/account_register2";
	}

	// STEP 4 - Registration Page Pt. 2 (Verification)
	@PostMapping("/register/register_account")
	public String registerAccount(ModelMap model, RedirectAttributes redirAttr,
			@ModelAttribute Customer customer, @ModelAttribute UserAccountTypeForm userAccount,
			SessionStatus session) {
		if (userAccount.validateData()) {
			boolean userNameExists = userRepo.existsByUsername(userAccount.getUsername());

			if (userNameExists) {
				redirAttr.addFlashAttribute("errorMsg", "Username already exists!");
			} else {
				User user = new User();
				user.setUsername(userAccount.getUsername());
				user.setPassword(passwordEncoder.encode(userAccount.getPassword()));
				user.setCustomer(customer);
				customer.setUser(user);

				Account account = new Account();
				account.setAccountType(AccountType.valueOf(userAccount.getAccountType()));
				account.setDateOpened(new Date(Instant.now().toEpochMilli()));
				account.setCustomer(customer);

				if (customer.getAccounts() == null)
					customer.setAccounts(new HashSet<Account>());
				customer.getAccounts().add(account);
				
				customerRepo.save(customer);
				
				redirAttr.addFlashAttribute("userDTO", userAccount);
				
				session.setComplete();

				return "redirect:/user/login";
			}
		} else {
			redirAttr.addFlashAttribute("errorMsg", "Invalid entry!!");
		}
		return "redirect:/register/signup_account";
	}
}
