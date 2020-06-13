package com.example.bankdemo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new BankUserDetailsService();
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new StandardPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	    authProvider.setHideUserNotFoundExceptions(false);
	    return authProvider;
	}

	@Autowired
	AuthSuccessHandler authSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement()
				.maximumSessions(-1)
				.expiredUrl("/user/login")
				.and()
				.invalidSessionUrl("/user/login")
			.and()
			.requiresChannel()
				.anyRequest().requiresSecure()
			.and()
			.authorizeRequests()
				.antMatchers("/", "/home", "/service/atm", "/register/*", "/css/*", "/js/*", "/account/*", "/user/perform_login", "/user/forgot*", "/user/reset*").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/user/login")
				.loginProcessingUrl("/user/login_process")
				.successForwardUrl("/user/perform_login")
				.defaultSuccessUrl("/user/dashboard")
				.failureUrl("/user/login")
				.successHandler(authSuccessHandler)
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/user/perform_logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
			.and()
			.rememberMe()
				.key("uniqueAndSecret")
				.tokenValiditySeconds(86400)
				.useSecureCookie(true);
	}
}