package com.aipl.store.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "affiliateUserDetails")
public class AffiliateToPay {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, updatable=false)
	private Long id;
	private Long userId;
	private Long affiliateId;
	private String prodId;
	private Long refauid;
	private Long pomoauid;
	private String productStatus;
	private String productCommission;
	private String productOrderDate;
	private String userRegisterDate;
	private BigDecimal productTotal;
	private String buyPromoCode;
	
	public AffiliateToPay() {
	}



	public AffiliateToPay(Long userId, Long affiliateId, String prodId, Long refauid, Long pomoauid, String productStatus,
			String productCommission, String productOrderDate, String userRegisterDate, BigDecimal productTotal,
			String buyPromoCode) {
		super();
		this.userId = userId;
		this.affiliateId = affiliateId;
		this.prodId = prodId;
		this.refauid = refauid;
		this.pomoauid = pomoauid;
		this.productStatus = productStatus;
		this.productCommission = productCommission;
		this.productOrderDate = productOrderDate;
		this.userRegisterDate = userRegisterDate;
		this.productTotal = productTotal;
		this.buyPromoCode = buyPromoCode;
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



	public Long getRefauid() {
		return refauid;
	}



	public void setRefauid(Long refauid) {
		this.refauid = refauid;
	}



	public Long getPomoauid() {
		return pomoauid;
	}



	public void setPomoauid(Long pomoauid) {
		this.pomoauid = pomoauid;
	}



	public String getProductStatus() {
		return productStatus;
	}



	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}



	public String getProductCommission() {
		return productCommission;
	}



	public void setProductCommission(String productCommission) {
		this.productCommission = productCommission;
	}



	public String getProductOrderDate() {
		return productOrderDate;
	}



	public void setProductOrderDate(String productOrderDate) {
		this.productOrderDate = productOrderDate;
	}



	public String getUserRegisterDate() {
		return userRegisterDate;
	}



	public void setUserRegisterDate(String userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}



	public BigDecimal getProductTotal() {
		return productTotal;
	}



	public void setProductTotal(BigDecimal productTotal) {
		this.productTotal = productTotal;
	}



	public String getBuyPromoCode() {
		return buyPromoCode;
	}



	public void setBuyPromoCode(String buyPromoCode) {
		this.buyPromoCode = buyPromoCode;
	}


	
}