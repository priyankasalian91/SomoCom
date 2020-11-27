package com.aipl.store.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AffiliateUserRegistrationDto {

	    @NotEmpty
	    private String firstName;

	    @NotEmpty
	    private String lastName;

	    @NotEmpty
	    private String password;
	    
	    @NotEmpty
	    private String username;
	    
	    @NotEmpty
	    private String phone;

//	    @NotEmpty
//	    private String confirmPassword;

	    @Email
	    @NotEmpty
	    private String email;
	    
	    @NotEmpty
	    private String pincode;
	    
	    private String inviteCode;
	    
	    private String amount;

	    
	    @AssertTrue
	    private Boolean terms;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public @NotEmpty String getPincode() {
			return pincode;
		}

		public void setPincode(@NotEmpty String pincode) {
			this.pincode = pincode;
		}

		public String getInviteCode() {
			return inviteCode;
		}

		public void setInviteCode(String inviteCode) {
			this.inviteCode = inviteCode;
		}

		public Boolean getTerms() {
			return terms;
		}

		public void setTerms(Boolean terms) {
			this.terms = terms;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

	   
	    

	}

