package com.aipl.store.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class AffiliateUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String inviteCode;
    private String pincode;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "affiliateusers_roles",
            joinColumns = @JoinColumn(
                    name = "affiliateuser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "affiliaterole_id", referencedColumnName = "id"))
    private Collection<AffiliateRole> roles;

    public AffiliateUser() {
    }




    


	public AffiliateUser(String username, String password, String firstName, String lastName, String inviteCode,
			String pincode, String email, String phone) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.inviteCode = inviteCode;
		this.pincode = pincode;
		this.email = email;
		this.phone = phone;
	}

	public AffiliateUser(String username, String password, String firstName, String lastName, String inviteCode,
			String pincode, String email, String phone, Collection<AffiliateRole> roles) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.inviteCode = inviteCode;
		this.pincode = pincode;
		this.email = email;
		this.phone = phone;
		this.roles = roles;
	}







	public Long getId() {
		return id;
	}







	public void setId(Long id) {
		this.id = id;
	}







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







	public String getInviteCode() {
		return inviteCode;
	}







	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}







	public String getPincode() {
		return pincode;
	}







	public void setPincode(String pincode) {
		this.pincode = pincode;
	}







	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public String getPhone() {
		return phone;
	}







	public void setPhone(String phone) {
		this.phone = phone;
	}







	public Collection<AffiliateRole> getRoles() {
		return roles;
	}







	public void setRoles(Collection<AffiliateRole> roles) {
		this.roles = roles;
	}







	@Override
	public String toString() {
		return "AffiliateUser [id=" + id + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", inviteCode=" + inviteCode + ", pincode=" + pincode
				+ ", email=" + email + ", phone=" + phone + ", roles=" + roles + "]";
	}

	
}
