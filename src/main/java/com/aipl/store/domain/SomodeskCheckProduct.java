package com.aipl.store.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "somodeskCheckoutprod")
public class SomodeskCheckProduct {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long affiliateId;
	private String prodId;
	private BigDecimal productTotal;
	private String prodStatus;
	private String prodCommission;
	private String prodOrderdate;
	
	
	public SomodeskCheckProduct(Long userId, Long affiliateId, String prodId, BigDecimal productTotal,
			String prodStatus, String prodCommission, String prodOrderdate) {
		super();
		this.userId = userId;
		this.affiliateId = affiliateId;
		this.prodId = prodId;
		this.productTotal = productTotal;
		this.prodStatus = prodStatus;
		this.prodCommission = prodCommission;
		this.prodOrderdate = prodOrderdate;
	}


	public SomodeskCheckProduct() {
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


	public BigDecimal getProductTotal() {
		return productTotal;
	}


	public void setProductTotal(BigDecimal productTotal) {
		this.productTotal = productTotal;
	}


	public String getProdStatus() {
		return prodStatus;
	}


	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}


	public String getProdCommission() {
		return prodCommission;
	}


	public void setProdCommission(String prodCommission) {
		this.prodCommission = prodCommission;
	}


	public String getProdOrderdate() {
		return prodOrderdate;
	}


	public void setProdOrderdate(String prodOrderdate) {
		this.prodOrderdate = prodOrderdate;
	}
	
	
	
	
	

	

}
