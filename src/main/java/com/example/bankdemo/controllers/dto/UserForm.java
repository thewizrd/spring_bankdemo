package com.example.bankdemo.controllers.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.bankdemo.utils.StringUtils;

public class UserForm implements Serializable {
	@NotEmpty
	protected String username;
	@NotEmpty
	protected String password;
	@NotEmpty
	protected String password_confirm;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword_confirm() {
		return password_confirm;
	}
	public void setPassword_confirm(String password_confirm) {
		this.password_confirm = password_confirm;
	}
	
	public boolean validateData() {
		return !StringUtils.isNullOrWhitespace(username) &&
			   !StringUtils.isNullOrWhitespace(password) &&
			   !StringUtils.isNullOrWhitespace(password_confirm) &&
			   Objects.equals(password, password_confirm);
	}
}
