package com.example.bankdemo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model, HttpServletRequest request)
	{
		if (request.getRemoteUser() != null || request.getSession().getAttribute("user") != null) {
			return "redirect:/user/dashboard";
		}
		return "welcome";
	}
}
