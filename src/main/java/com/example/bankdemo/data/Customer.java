package com.example.bankdemo.data;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.bankdemo.utils.StringUtils;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Integer id;
    @Column(name = "first_name", nullable = false)
	private String firstName;
    @Column(name = "middle_name", nullable = true)
	private String middleName;
    @Column(name = "last_name", nullable = false)
	private String lastName;
    @Column(name = "ssn_number", nullable = false, length = 11, unique = true)
	private String ssnNumber;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "email_address", nullable = false)
	private String emailAddress;
    @Column(name = "phone_number", nullable = false, length = 14)
	private String phoneNumber;
	
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Address address;
	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private User user;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Account> accounts;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	
	public boolean validateData() {
		return !StringUtils.isNullOrWhitespace(firstName) &&
			   !StringUtils.isNullOrWhitespace(lastName) &&
			   !StringUtils.isNullOrWhitespace(ssnNumber) &&
			   ssnNumber.length() == (11 /* 9 digit number + 2 delimeter */) &&
			   birthDate != null &&
			   !StringUtils.isNullOrWhitespace(emailAddress) &&
			   !StringUtils.isNullOrWhitespace(phoneNumber) &&
			   phoneNumber.length() == (14 /* 10 digit number + 2 parens + 1 space + 1 delimeter */);
	}
}