package com.example.bankdemo.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.bankdemo.utils.StringUtils;

@Entity
@Table(name = "addresses")
public class Address implements Serializable {
	@Id
	@Column(name = "customer_id")
	private int id;
	@Column(name = "street_address", nullable = false)
	private String streetAddress;
	@Column(name = "street_address2", nullable = true)
	private String streetAddress_2;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "state", nullable = false)
	private String state;
	@Column(name = "zipcode", nullable = false)
	private String zipCode;
	
	@OneToOne
	@MapsId
	private Customer customer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getStreetAddress_2() {
		return streetAddress_2;
	}

	public void setStreetAddress_2(String streetAddress_2) {
		this.streetAddress_2 = streetAddress_2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public boolean validateData() {
		return !StringUtils.isNullOrWhitespace(streetAddress) &&
			   !StringUtils.isNullOrWhitespace(city) &&
			   !StringUtils.isNullOrWhitespace(state) &&
			   !StringUtils.isNullOrWhitespace(zipCode) && zipCode.length() == 5;
	}
}
