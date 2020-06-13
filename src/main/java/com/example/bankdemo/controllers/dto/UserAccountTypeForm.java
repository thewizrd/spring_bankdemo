package com.example.bankdemo.controllers.dto;

import javax.validation.constraints.NotEmpty;

import com.example.bankdemo.utils.StringUtils;

public class UserAccountTypeForm extends UserForm {
	@NotEmpty
	private String accountType;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Override
	public boolean validateData() {
		return super.validateData() &&
			   !StringUtils.isNullOrWhitespace(accountType);
	}
}
