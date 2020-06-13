package com.example.bankdemo.controllers.dto;

import java.io.Serializable;

import javax.validation.Validation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.bankdemo.utils.StringUtils;

public class VerifyCustomerEmailForm implements Serializable {
	@NotEmpty
	@Size(min = 11, max = 11)
	private String ssnNumber;
	@NotEmpty
	@Size(min = 9, max = 9)
	private String accountNumber;
	@NotEmpty
	@Email
	private String emailAddress;
	@NotEmpty
	private String forgotOption;

	public String getSsnNumber() {
		return ssnNumber;
	}
	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getForgotOption() {
		return forgotOption;
	}
	public void setForgotOption(String forgotOption) {
		this.forgotOption = forgotOption;
	}
	
	public boolean validateData() {
		return !StringUtils.isNullOrWhitespace(ssnNumber) &&
				ssnNumber.length() == 11 &&
				!StringUtils.isNullOrWhitespace(accountNumber) &&
				accountNumber.length() == 9 &&
				!StringUtils.isNullOrWhitespace(emailAddress) &&
				!StringUtils.isNullOrWhitespace(forgotOption);
	}
}
