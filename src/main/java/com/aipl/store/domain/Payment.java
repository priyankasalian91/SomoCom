package com.aipl.store.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String type;
	private String chequeNo;
	private Date chequeDate;
	private String draweeifsc;
	private String draweebank;
	private String draweename;
	private Date payCompletionDate;
	
	private String receiver;
	private String deladdtype; // 1 :home, 2 : office
	private String billname;
	private String billaddtype; // 1 :home, 2 : office
	private String billcheck;
	
	@OneToOne
	private Order order;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	public Date getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}
	
	public String getDraweeifsc() {
		return draweeifsc;
	}
	public void setDraweeifsc(String draweeifsc) {
		this.type = draweeifsc;
	}
	
	public String getDraweebank() {
		return draweebank;
	}
	public void setDraweebank(String draweebank) {
		this.draweebank = draweebank;
	}

	public String getDraweename() {
		return draweename;
	}
	public void setDraweename(String draweename) {
		this.draweename = draweename;
	}
	
	public Date getPayCompletionDate() {
		return payCompletionDate;
	}
	public void setPayCompletionDate(Date payCompletionDate) {
		this.payCompletionDate = payCompletionDate;
	}
	
	
	
	public String getDeladdtype() {
		return deladdtype;
	}
	public void setDeladdtype(String deladdtype) {
		this.deladdtype = deladdtype;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getBillname() {
		return billname;
	}
	public void setBillname(String billname) {
		this.billname = billname;
	}
	public String getBilladdtype() {
		return billaddtype;
	}
	public void setBilladdtype(String billaddtype) {
		this.billaddtype = billaddtype;
	}
	public String getBillcheck() {
		return billcheck;
	}
	public void setBillcheck(String billcheck) {
		this.billcheck = billcheck;
	}

	
	
	
	/*private String cardName;
	private String cardNumber;
	private int expiryMonth;
	private int expiryYear;
	private int cvc;
	private String holderName;
	
	@OneToOne
	private Order order;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getExpiryMonth() {
		return expiryMonth;
	}
	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	public int getExpiryYear() {
		return expiryYear;
	}
	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}
	public int getCvc() {
		return cvc;
	}
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	
	*/

}
