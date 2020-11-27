package com.aipl.store.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="affiliateregistertfees")
public class AffiliateRegisterFees {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long affiliateID;
	private Long userID;
	private String email;
	private String phoneNumber;
	private String amount;
	private String razorpayPaymentId;
	private String razorpaySignature;
	private String razorpayOrderId;
	private String paymentDate;
	private String paymentsStatus;
	
	public AffiliateRegisterFees() {
		
	}

	public AffiliateRegisterFees(Long affiliateID, Long userID, String email, String phoneNumber, String amount,
			String razorpayPaymentId, String razorpaySignature, String razorpayOrderId, String paymentDate,
			String paymentsStatus) {
		super();
		this.affiliateID = affiliateID;
		this.userID = userID;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.amount = amount;
		this.razorpayPaymentId = razorpayPaymentId;
		this.razorpaySignature = razorpaySignature;
		this.razorpayOrderId = razorpayOrderId;
		this.paymentDate = paymentDate;
		this.paymentsStatus = paymentsStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAffiliateID() {
		return affiliateID;
	}

	public void setAffiliateID(Long affiliateID) {
		this.affiliateID = affiliateID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getRazorpaySignature() {
		return razorpaySignature;
	}

	public void setRazorpaySignature(String razorpaySignature) {
		this.razorpaySignature = razorpaySignature;
	}

	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentsStatus() {
		return paymentsStatus;
	}

	public void setPaymentsStatus(String paymentsStatus) {
		this.paymentsStatus = paymentsStatus;
	}
	
	
	
	
	
}
