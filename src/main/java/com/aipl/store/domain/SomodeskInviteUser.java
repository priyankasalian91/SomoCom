package com.aipl.store.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="somodeskTest2")
public class SomodeskInviteUser {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long affiliateId;
	private Long inviteAid;
	private String date;
	
	public SomodeskInviteUser(Long id, Long userId, Long affiliateId, Long inviteAid, String date) {
		super();
		this.id = id;
		this.userId = userId;
		this.affiliateId = affiliateId;
		this.inviteAid = inviteAid;
		this.date = date;
	}

	public SomodeskInviteUser() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(Long affiliateId) {
		this.affiliateId = affiliateId;
	}



	public Long getInviteAid() {
		return inviteAid;
	}

	public void setInviteAid(Long inviteAid) {
		this.inviteAid = inviteAid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
