package com.aipl.store.domain;


import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BulkPurchase  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int bulkquantity;
	private String email;
	
	@OneToOne
	@JoinColumn(name="article_id")
	private Article article;
	private String phone;
	private String fullname;
	private Timestamp requestedon;
	/* sent : true
	 * not  sent : false */
	private boolean mailsent;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBulkquantity() {
		return bulkquantity;
	}

	public void setBulkquantity(int bulkquantity) {
		this.bulkquantity = bulkquantity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	

	public boolean isMailsent() {
		return mailsent;
	}

	public void setMailsent(boolean mailsent) {
		this.mailsent = mailsent;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Timestamp getRequestedon() {
		return requestedon;
	}

	public void setRequestedon(Timestamp requestedon) {
		this.requestedon = requestedon;
	}

	@Override
	public String toString() {
		return "BulkPurchase [id=" + id + ", bulkquantity=" + bulkquantity + ", email=" + email + ", article=" + article
				+ ", phone=" + phone + ", fullname=" + fullname + ", requestedon=" + requestedon + ", mailsent="
				+ mailsent + "]";
	}



	
	
	
}



