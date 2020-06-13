package com.example.bankdemo.controllers;

import java.util.Objects;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bankdemo.controllers.dto.UserForm;
import com.example.bankdemo.controllers.dto.VerifyCustomerEmailForm;
import com.example.bankdemo.dao.CustomerRepository;
import com.example.bankdemo.dao.UserRespository;
import com.example.bankdemo.data.Account;
import com.example.bankdemo.data.Customer;
import com.example.bankdemo.data.User;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

@Controller
@SessionAttributes({"customerForm", "userForm"})
public class ForgotCredentialsController {

	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private UserRespository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/user/forgot_credentials")
	public String forgotCreds(ModelMap model, SessionStatus session)
	{
		// Clear session
		session.setComplete();
		
		model.addAttribute("customerForm", new VerifyCustomerEmailForm());
		return "useraccount/forgot_credentials";
	}

	@PostMapping("/user/forgot_credentials")
	public String verifyUserInfo(RedirectAttributes redirectAttrs, ModelMap model,
			@ModelAttribute VerifyCustomerEmailForm customerForm, BindingResult binding,
			SessionStatus session)
	{
		if (!binding.hasErrors()) {
			boolean ssnExists = customerRepo.existsByssnNumber(customerForm.getSsnNumber());

			if (!ssnExists) {
				redirectAttrs.addFlashAttribute("errorMsg", "No account available for SSN number!");
			} else {
				Customer customer = customerRepo.findByssnNumber(customerForm.getSsnNumber());
				if (!Objects.equals(customer.getEmailAddress(), customerForm.getEmailAddress())) {
					redirectAttrs.addFlashAttribute("errorMsg", "Invalid email address!!");
				} else {
					Set<Account> accounts = customer.getAccounts();
					Account userAcc = Iterables.find(accounts, new Predicate<Account>() {
						@Override
						public boolean apply(@Nullable Account input) {
							return Objects.equals(input.getAccountIdFormatted(), customerForm.getAccountNumber());
						}
					});
					
					if (userAcc != null) {
						if ("username".equals(customerForm.getForgotOption())) {
							model.addAttribute("forgottenUsername", customer.getUser().getUsername());
							session.setComplete();
							return "useraccount/show_forgotten";
						} else if ("password".equals(customerForm.getForgotOption())) {
							UserForm userForm = new UserForm();
							userForm.setUsername(customer.getUser().getUsername());

							redirectAttrs.addFlashAttribute("customerForm", customerForm);
							redirectAttrs.addFlashAttribute("userForm", userForm);
							return "redirect:/user/resetpassword";
						}
					} else {
						redirectAttrs.addFlashAttribute("errorMsg", "Account not found!");
					}
				}
			}
		} else {
			redirectAttrs.addFlashAttribute("errorMsg", "Invalid entry!!");
		}
		return "redirect:/user/forgot_credentials";
	}
	
	@GetMapping("/user/resetpassword")
	public String resetPassword(ModelMap model) {
		if (model.containsAttribute("userForm")) {
			return "useraccount/reset_password";
		}
		return "redirect:/user/forgot_credentials";
	}

	@PostMapping("/user/resetpassword")
	public String resetPasswordPost(RedirectAttributes redirectAttrs,
			@ModelAttribute UserForm userForm, BindingResult binding,
			SessionStatus session)
	{
		if (!binding.hasErrors() && userForm.validateData()) {
			User user = userRepo.findFirstByUsername(userForm.getUsername());
			if (user.getUsername().equals(userForm.getUsername())) {
				user.setPassword(passwordEncoder.encode(userForm.getPassword()));
				userRepo.save(user);
				
				redirectAttrs.addFlashAttribute("alertMsg", "Password reset successfully");
				session.setComplete();
				return "redirect:/user/login";
			}
		} else {
			redirectAttrs.addFlashAttribute("errorMsg", "Invalid entry!!");
		}
		return "redirect:/user/forgot_credentials";
	}
}
