package com.aipl.store.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "invitedetails")
public class InviteDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long affiliateId;
	private Long inviteId;
	
	
	
	public InviteDetails( Long affiliateId, Long inviteId) {
		super();
		this.affiliateId = affiliateId;
		this.inviteId = inviteId;
	}
	public InviteDetails() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAffiliateId() {
		return affiliateId;
	}
	public void setAffiliateId(Long affiliateId) {
		this.affiliateId = affiliateId;
	}
	public Long getInviteId() {
		return inviteId;
	}
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}
	
	

}
