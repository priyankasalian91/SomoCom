package com.aipl.store.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//delivery address
	private String streetAddress;
	private String city;
	private String country;
	private String zipCode;
	
	private String flatshop;
	private String landmark;
		
	private String billstreetAddress;
	private String billcity;
	private String billcountry;
	private String billzipCode;
	
	private String billflatshop;
	private String billlandmark;
	
	@Column(columnDefinition = "integer default 0")
	private int addresstype; // 0: default, 1 : profie , 2 : order
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getFlatshop() {
		return flatshop;
	}
	public void setFlatshop(String flatshop) {
		this.flatshop = flatshop;
	}
	
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	public String getBillstreetAddress() {
		return billstreetAddress;
	}
	public void setBillstreetAddress(String billstreetAddress) {
		this.billstreetAddress = billstreetAddress;
	}
	public String getBillcity() {
		return billcity;
	}
	public void setBillcity(String billcity) {
		this.billcity = billcity;
	}
	public String getBillcountry() {
		return billcountry;
	}
	public void setBillcountry(String billcountry) {
		this.billcountry = billcountry;
	}
	public String getBillzipCode() {
		return billzipCode;
	}
	public void setBillzipCode(String billzipCode) {
		this.billzipCode = billzipCode;
	}
	public String getBillflatshop() {
		return billflatshop;
	}
	public void setBillflatshop(String billflatshop) {
		this.billflatshop = billflatshop;
	}
	public String getBilllandmark() {
		return billlandmark;
	}
	public void setBilllandmark(String billlandmark) {
		this.billlandmark = billlandmark;
	}
	public int getAddresstype() {
		return addresstype;
	}
	public void setAddresstype(int addresstype) {
		this.addresstype = addresstype;
	}
	
	
}
