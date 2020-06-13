package com.example.bankdemo.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bankdemo.controllers.dto.UserForm;

@Controller
public class LoginController {

	@GetMapping("/user/login")
	public String loginPage(ModelMap model, HttpServletRequest request, HttpSession session)
	{
		if (request.getRemoteUser() != null || session.getAttribute("user") != null) {
			return "redirect:/user/dashboard";
		}

		if (model.containsAttribute("userForm") && request.getRemoteUser() == null) {
			UserForm user = (UserForm) model.getAttribute("userForm");
			try {
				request.login(user.getUsername(), user.getPassword());
				return "redirect:/user/dashboard";
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (!model.containsAttribute("userForm")) {
				model.addAttribute("userForm", new UserForm());
			}
		}

		return "useraccount/login";
	}

	@PostMapping("/user/perform_login")
	public String login(RedirectAttributes redirectAttrs,
			HttpServletRequest request, HttpSession session,
			@ModelAttribute UserForm userForm)
	{
		try {
			request.login(userForm.getUsername(), userForm.getPassword());
			return "redirect:/user/dashboard";
		} catch (ServletException e) {
			if (e.getCause() instanceof UsernameNotFoundException) {
				redirectAttrs.addFlashAttribute("errorMsg", "Username not found");
			} else if (e.getCause() instanceof BadCredentialsException) {
				redirectAttrs.addFlashAttribute("errorMsg", "Invalid password");
			}

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/user/login";
	}

	@GetMapping("/user/perform_logout")
	public String logout(RedirectAttributes redirectAttrs,
			HttpServletRequest request, HttpSession session)
	{
		try {
			session.invalidate();
			request.logout();
			redirectAttrs.addFlashAttribute("alertMsg", "Logged out successfully");
			return "redirect:/user/login";
		} catch (ServletException | IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/user/login";
	}
}
