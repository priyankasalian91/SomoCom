package com.aipl.store.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="somodeskTest1")
public class SomodeskProductLinkDetails {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long affiliateId;
	private String prodId;
	private String date;
	private Long refAid;
	
	
	
	
	public SomodeskProductLinkDetails(Long userId, Long affiliateId, String prodId, String date, Long refAid) {
		super();
		this.userId = userId;
		this.affiliateId = affiliateId;
		this.prodId = prodId;
		this.date = date;
		this.refAid = refAid;
	}
	public SomodeskProductLinkDetails() {
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
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getRefAid() {
		return refAid;
	}
	public void setRefAid(Long refAid) {
		this.refAid = refAid;
	}
	
	
	
}
