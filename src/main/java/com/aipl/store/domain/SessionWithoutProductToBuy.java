package com.aipl.store.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sessionwithoutproduct")
public class SessionWithoutProductToBuy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, updatable=false)
	private Long id;
	private Long userId;
	private Long prodId;
	private Long affiliateId;
	private String productStatus;
	private String productCommission;
	private String productOrderDate;
	private String userRegisterDate;
	private BigDecimal productTotal;
	
	public SessionWithoutProductToBuy() {
	}

	public SessionWithoutProductToBuy(Long userId, Long prodId, Long affiliateId, String productStatus,
			String productCommission, String productOrderDate, String userRegisterDate, BigDecimal productTotal) {
		super();
		this.userId = userId;
		this.prodId = prodId;
		this.affiliateId = affiliateId;
		this.productStatus = productStatus;
		this.productCommission = productCommission;
		this.productOrderDate = productOrderDate;
		this.userRegisterDate = userRegisterDate;
		this.productTotal = productTotal;
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

	public Long getProductId() {
		return prodId;
	}

	public void setProductId(Long prodId) {
		this.prodId = prodId;
	}

	public Long getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(Long affiliateId) {
		this.affiliateId = affiliateId;
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
	
	
}